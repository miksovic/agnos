using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace CSharp.Client.UnitTest
{    
    /// <summary>    
    /// Summary description for RpcCallsSpeedUnitTest
    /// </summary>
    [TestClass]
    public class RpcSpeedUnitTest
    {
        [TestMethod]
        public void TestMethod()
        {
            const int callCount = 1000;
            RpcTest(ClientHelper.Client.boolMethod, true, callCount);
            RpcTest(ClientHelper.Client.bufferMethod, new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 }, callCount);
            RpcTest(ClientHelper.Client.dateMethod, DateTime.Now, callCount);
            RpcTest(ClientHelper.Client.int16Method, short.MaxValue, callCount);
            RpcTest(ClientHelper.Client.int32Method, int.MaxValue, callCount);
            RpcTest(ClientHelper.Client.int64Method, long.MaxValue, callCount);
            RpcTest(ClientHelper.Client.listMethod, (IList<int>)new List<int> { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 }, callCount);
            RpcTest(ClientHelper.Client.mapMethod, (IDictionary<int, int>)new Dictionary<int, int> { { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 } }, callCount);
            RpcTest(ClientHelper.Client.stringMethod, "Hello World", callCount);
        }

        delegate T SameInOutTypeDelegate<T>(T arg);

        private static void RpcTest<TParam>(SameInOutTypeDelegate<TParam> method, TParam param, int callCount)
        {
            var testStartTime = DateTime.Now;
            Console.Write(string.Format("{0}X call of method {1}", callCount, method.Method.Name));                        
            for (var i = 0; i < callCount; i++)
                method.Invoke(param);
            Console.Write(string.Format(" : processing time {0}", DateTime.Now - testStartTime) + Environment.NewLine);
        }
    }
}