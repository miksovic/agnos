//////////////////////////////////////////////////////////////////////////////
// Part of the Agnos RPC Framework
//    http://agnos.sourceforge.net
//
// Copyright 2010, International Business Machines Corp.
//                 Author: Tomer Filiba (tomerf@il.ibm.com)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//////////////////////////////////////////////////////////////////////////////

using System;
using System.Threading;

namespace Agnos.Utils
{
    public sealed class ReentrantLock
    {
        private volatile Thread owner;
        private int count;

        public ReentrantLock()
        {
            owner = null;
            count = 0;
        }

        public void Acquire()
        {
            Monitor.Enter(this);
            owner = Thread.CurrentThread;
            count += 1;
        }

        public void Release()
        {
        		count -= 1;
        		if (count == 0) {
            		owner = null;
            	}
            	if (count < 0) {
				count = 0;
            		throw new InvalidOperationException("released too many times!");
            	}
            Monitor.Exit(this);
        }

        public bool IsHeldByCurrentThread()
        {
            return owner == Thread.CurrentThread;
        }
    }


}


