/**
 * 基础服务
 */
namespace java cn.com.cloudstone.menu.server.thrift.api

// 用户未登录
exception UserNotLoginException {}

// 自定义错误，前端会直接显示这个错误信息
exception AException{
	1: i32 code,
	2: string msg,
}