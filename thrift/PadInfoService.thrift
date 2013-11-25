include "Base.thrift"
/**
 * 平板信息
 */
namespace java cn.com.cloudstone.menu.server.thrift.api

struct PadInfo{
	// 登陆session，指明登陆用户
	1: string sessionId,
	// 设备唯一标示
	2: string IMEI,
	// 电量
	3: i32 batteryLevel,
}

service IPadInfoService{
	/**
	 * 提交Pad信息
	 */
	bool submitPadInfo(1:PadInfo info),
}