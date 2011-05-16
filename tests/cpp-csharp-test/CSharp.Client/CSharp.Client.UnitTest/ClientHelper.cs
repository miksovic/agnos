using System;

namespace CSharp.Client.UnitTest
{
    public class ClientHelper
    {
        public static string Address { get { throw new NotImplementedException("Specify server address."); } }
        public static int Port { get { throw new NotImplementedException("Specify server port."); } }

        public static TestServiceBindings.ClientBindings.TestService.Client Client = TestServiceBindings.ClientBindings.TestService.Client.ConnectSock(Address, Port);
    }
}
