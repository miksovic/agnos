package agnos;

import java.io.*;
import java.util.*;

public class Packers {
	public interface IPacker {
		public void pack(Object obj, OutputStream stream) throws IOException;

		public Object unpack(InputStream stream) throws IOException;
	}

	protected static void _write(OutputStream stream, byte[] buffer)
			throws IOException {
		stream.write(buffer, 0, buffer.length);
	}

	protected static void _read(InputStream stream, byte[] buffer)
			throws IOException {
		int total_got = 0;
		int got;

		while (total_got < buffer.length) {
			got = stream.read(buffer, total_got, buffer.length - total_got);
			total_got += got;
			if (got <= 0 && total_got < buffer.length) {
				throw new IOException("end of stream detected");
			}
		}
	}

	public static class _Int8 implements IPacker {
		private byte[] buffer = new byte[1];

		public void pack(Object obj, OutputStream stream) throws IOException
		{
			buffer[0] = (byte)(((Byte)obj) & 0xFF);
			_write(stream, buffer);
		}

		public Object unpack(InputStream stream) throws IOException {
			_read(stream, buffer);
			return new Byte(buffer[0]);
		}
	}

	public static _Int8 Int8 = new _Int8();

	public static class _Bool implements IPacker {
		public void pack(Object obj, OutputStream stream) throws IOException {
			Int8.pack(new Byte(((Boolean) obj) ? (byte) 1 : (byte) 0), stream);
		}

		public Object unpack(InputStream stream) throws IOException {
			return new Boolean((Byte) Int8.unpack(stream) != 0);
		}
	}

	public static _Bool Bool = new _Bool();

	public static class _Int16 implements IPacker {
		private byte[] buffer = new byte[2];

		public void pack(Object obj, OutputStream stream) throws IOException {
			short val = (Short) obj;
			buffer[0] = (byte) ((val >> 8) & 0xff);
			buffer[1] = (byte) ((val) & 0xFF);
			_write(stream, buffer);
		}

		public Object unpack(InputStream stream) throws IOException {
			_read(stream, buffer);
			return new Short((short) (buffer[0] << 8 | buffer[1]));
		}
	}

	public static _Int16 Int16 = new _Int16();

	public static class _Int32 implements IPacker {
		private byte[] buffer = new byte[4];

		public void pack(Object obj, OutputStream stream) throws IOException {
			int val = (Integer) obj;
			buffer[0] = (byte) ((val >> 24) & 0xff);
			buffer[1] = (byte) ((val >> 16) & 0xff);
			buffer[2] = (byte) ((val >> 8) & 0xff);
			buffer[3] = (byte) ((val) & 0xFF);
			_write(stream, buffer);
		}

		public Object unpack(InputStream stream) throws IOException {
			_read(stream, buffer);
			return new Integer((buffer[0] << 24 | buffer[1] << 16
					| buffer[2] << 8 | buffer[3]));
		}
	}

	public static _Int32 Int32 = new _Int32();

	public static class _Int64 implements IPacker {
		private byte[] buffer = new byte[8];

		public void pack(Object obj, OutputStream stream) throws IOException {
			int val = (Integer) obj;
			buffer[0] = (byte) ((val >> 56) & 0xff);
			buffer[1] = (byte) ((val >> 48) & 0xff);
			buffer[2] = (byte) ((val >> 40) & 0xff);
			buffer[3] = (byte) ((val >> 32) & 0xff);
			buffer[4] = (byte) ((val >> 24) & 0xff);
			buffer[5] = (byte) ((val >> 16) & 0xff);
			buffer[6] = (byte) ((val >> 8) & 0xff);
			buffer[7] = (byte) ((val) & 0xFF);
			_write(stream, buffer);
		}

		public Object unpack(InputStream stream) throws IOException {
			_read(stream, buffer);
			return new Long((long) (buffer[0] << 56 | buffer[1] << 48
					| buffer[2] << 40 | buffer[3] << 32 | buffer[4] << 24
					| buffer[5] << 16 | buffer[6] << 8 | buffer[7]));
		}
	}

	public static _Int64 Int64 = new _Int64();
	public static _Int64 ObjRef = Int64;

	public static class _Float implements IPacker {
		public void pack(Object obj, OutputStream stream) throws IOException {
			Int64.pack(Double.doubleToLongBits((Double) obj), stream);
		}

		public Object unpack(InputStream stream) throws IOException {
			return new Double(Double.longBitsToDouble((Long) (Int64
					.unpack(stream))));
		}
	}

	public static _Float Float = new _Float();

	public static class _Buffer implements IPacker {
		public void pack(Object obj, OutputStream stream) throws IOException {
			byte[] val = (byte[]) obj;
			Int32.pack(new Integer(val.length), stream);
			_write(stream, val);
		}

		public Object unpack(InputStream stream) throws IOException {
			int length = (Integer) Int32.unpack(stream);
			byte[] buf = new byte[length];
			_read(stream, buf);
			return buf;
		}
	}

	public static _Buffer Buffer = new _Buffer();

	public static class _Date implements IPacker {
		public void pack(Object obj, OutputStream stream) throws IOException {
			Int64.pack(new Long(((Date) obj).getTime()), stream);
		}

		public Object unpack(InputStream stream) throws IOException {
			return new Date((Long)Int64.unpack(stream));
		}
	}

	public static _Date Date = new _Date();

	public static class _Str implements IPacker {
		public void pack(Object obj, OutputStream stream) throws IOException {
			Buffer.pack(((String) obj).getBytes("UTF-8"), stream);
		}

		public Object unpack(InputStream stream) throws IOException {
			byte[] buf = (byte[])Buffer.unpack(stream);
			return new String(buf, "UTF-8");
		}
	}

	public static _Str Str = new _Str();

	public static class ListOf implements IPacker {
		private IPacker type;

		public ListOf(IPacker type) {
			this.type = type;
		}

		public void pack(Object obj, OutputStream stream) throws IOException {
			List val = (List) obj;
			Int32.pack(new Integer(val.size()), stream);

			for (Object obj2 : val) {
				type.pack(obj2, stream);
			}
		}

		public Object unpack(InputStream stream) throws IOException {
			int length = (Integer) Int32.unpack(stream);
			ArrayList<Object> arr = new ArrayList<Object>(length);
			for (int i = 0; i < length; i++) {
				arr.add(type.unpack(stream));
			}
			return arr;
		}
	}

	public static class MapOf implements IPacker {
		private IPacker keytype;
		private IPacker valtype;

		public MapOf(IPacker keytype, IPacker valtype) {
			this.keytype = keytype;
			this.valtype = valtype;
		}

		public void pack(Object obj, OutputStream stream) throws IOException {
			Map val = (Map) obj;
			Int32.pack(new Integer(val.size()), stream);

			for (Map.Entry e : (Set<Map.Entry>)val.entrySet()) {
				keytype.pack(e.getKey(), stream);
				valtype.pack(e.getValue(), stream);
			}
		}

		public Object unpack(InputStream stream) throws IOException {
			int length = (Integer) Int32.unpack(stream);
			Map<Object, Object> map = new HashMap<Object, Object>(length);
			for (int i = 0; i < length; i++) {
				Object k = keytype.unpack(stream);
				Object v = valtype.unpack(stream);
				map.put(k, v);
			}
			return map;
		}
	}

}
