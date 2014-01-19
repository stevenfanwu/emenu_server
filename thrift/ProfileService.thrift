include "Base.thrift"
/**
 * 账户服务
 */
namespace java cn.com.cloudstone.menu.server.thrift.api

// 用户已存在
exception DuplicateUsernameException {}

// 用户名格式错误
exception MalformedUsernameException {}

// 密码格式错误
exception MalformedPasswordException {}

// 用户、密码错误
exception WrongUsernameOrPasswordException {}

// 不被接受的IMEI
exception IMEINotAllowedException {}

enum UserType{
	Waiter, Admin, Customer,
}

struct Login{
	1: string sessionId, 
	2: string username,
	3: UserType userType,
}

service IProfileService{

	/*
	 * 登陆，返回sessionId。
	 */
	Login loginUser(1:string username, 2:string pwd, 3:string imei) 
	throws (1:WrongUsernameOrPasswordException e, 2:IMEINotAllowedException ie),
	
	/*
	 * 注销
	 */
	bool logout(1:string sessionId),
	
	/*
	 * 修改密码
	 * 不需要实现
	 */
	bool changePassword(1:string sessionId, 2:string oldPwd, 3:string newPwd) 
	throws (1:Base.UserNotLoginException ue, 2:WrongUsernameOrPasswordException we),
	
	/**
	 * 获取所有可登陆的用户名。
	 */
	list<string> getAllUsers(),
}
