include "Base.thrift"
/**
 * 平板信息
 */
namespace java cn.com.cloudstone.menu.server.thrift.api

struct PadInfo{
	// session id. 
	1: string sessionId,
	// device id.
	2: string IMEI,
	// battery. 
	3: i32 batteryLevel,
        // The restaurent id.
        4: i32 restaurentId,
}

service IPadInfoService{
	bool submitPadInfo(1:PadInfo info),
}
