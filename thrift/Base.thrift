/**
 * Base service. 
 */
namespace java cn.com.cloudstone.menu.server.thrift.api

//  User not login.
exception UserNotLoginException {}

// Error, client will show this error directly. 
exception AException{
	1: i32 code,
	2: string msg,
}
