using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace CSharp.Client.UnitTest
{
    /// <summary>
    /// Summary description for RpcCallsSpeedUnitTest
    /// </summary>
    [TestClass]
    public class RpcDataUnitTest
    {
        [TestMethod]
        public void BoolTestMethod()
        {
            var toBeSent = true;
            var received = ClientHelper.Client.boolMethod(toBeSent);
            Assert.AreEqual(toBeSent, received);

            toBeSent = false;
            received = ClientHelper.Client.boolMethod(toBeSent);
            Assert.AreEqual(toBeSent, received);
        }

        [TestMethod]
        public void BufferTestMethod()
        {           
            var toBeSent = new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
            var received = ClientHelper.Client.bufferMethod(toBeSent);            

            if(toBeSent.Except(received).Count() != 0)
                Assert.Fail();
        }

        [TestMethod]
        public void DateTimeTestMethod()
        {
            var toBeSent = DateTime.Now;
            var received = ClientHelper.Client.dateMethod(toBeSent);
            Assert.AreEqual(toBeSent, received);
        }

        [TestMethod]
        public void Int16TestMethod()
        {
            Assert.AreEqual(short.MaxValue, ClientHelper.Client.int16Method(short.MaxValue));
        }

        [TestMethod]
        public void Int32TestMethod()
        {
            Assert.AreEqual(Int32.MaxValue, ClientHelper.Client.int32Method(Int32.MaxValue));
        }
        [TestMethod]
        public void Int64TestMethod()
        {
            Assert.AreEqual(Int64.MaxValue, ClientHelper.Client.int64Method(Int64.MaxValue));
        }

        [TestMethod]
        public void ListTestMethod()
        {
            var toBeSent = new List<int> { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
            var received = ClientHelper.Client.listMethod(toBeSent);

            if (toBeSent.Except(received).Count() != 0)
                Assert.Fail();
        }

        [TestMethod]
        public void DictTestMethod()
        {
            var toBeSent = new Dictionary<int, int> { { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 }};
            var received = ClientHelper.Client.mapMethod(toBeSent);

            if (toBeSent.Except(received).Count() != 0)
                Assert.Fail();
        }

        [TestMethod]
        public void StringTestMethod()
        {
            var toBeSent = "Hello World";
            Assert.AreEqual(toBeSent, ClientHelper.Client.stringMethod(toBeSent));
        }
    }
}
