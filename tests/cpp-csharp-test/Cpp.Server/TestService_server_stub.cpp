//////////////////////////////////////////////////////////////////////////////
// this is an auto-generated server skeleton
//////////////////////////////////////////////////////////////////////////////
#include "TestService_server_bindings.hpp"

using namespace agnos;
using namespace TestService::ServerBindings;


//
// classes
//
//
// handler
//
class Handler : public IHandler
{
	public:

	shared_ptr< map< int32_t, int32_t > > mapMethod(shared_ptr< map< int32_t, int32_t > > value)
	{
        return(value);
	}
	
	bool boolMethod(bool value)
	{        
        return(value);
	}
	
	datetime dateMethod(const datetime& value)
	{
        return(value);
	}
	
	shared_ptr<string> bufferMethod(const string& value)
	{        
        shared_ptr<string> myString(new string(value));    
        return(myString);
	}
	
	shared_ptr<string> stringMethod(const string& value)
	{        
        shared_ptr<string> myString(new string(value));
        return(myString);
	}
	
	int32_t int32Method(int32_t value)
	{        
        return(value);
	}
	
	int64_t int64Method(int64_t value)
	{        
        return(value);
	}
	
	shared_ptr< set< int32_t > > setMethod(shared_ptr< set< int32_t > > value)
	{        
        return(value);
	}
	
	int8_t int8Method(int8_t value)
	{
        return(value);
	}
	
	shared_ptr< vector< int32_t > > listMethod(shared_ptr< vector< int32_t > > value)
	{
        return(value);
	}
	
	int16_t int16Method(int16_t value)
	{
        return(value);
	}
	
};

//
// main
//
int main(int argc, const char * argv[])
{	
	ProcessorFactory processor_factory(shared_ptr<IHandler>(new Handler()));
	
	agnos::servers::CmdlineServer server(processor_factory);
	return server.main(argc, argv);
}
