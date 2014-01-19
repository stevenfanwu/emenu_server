include "Base.thrift"
include "ProfileService.thrift"
/**
 * 服务员服务
 */
namespace java cn.com.cloudstone.menu.server.thrift.api

// 权限不足。请求被拒绝。
exception PermissionDenyExcpetion {}

// 餐桌已经被占用
exception TableOccupiedException {}

// 餐桌未使用
exception TableEmptyException {}

// 餐桌已经点菜
exception TableCancelException {}

/**
 * 餐桌状态。已预订，已占用，空置。
 */
enum TableStatus{
	Ordered, Occupied, Empty
}

/**
 * 餐桌信息
 */
struct TableInfo{
	1: string id,
	// 顾客人数
	2: i32 customNumber,
	3: TableStatus status,
	// 此桌当前点餐的orderId
	4: i32 orderId,
}

enum ServiceType{
	CallForWaiter, // 呼叫服务员
	RenewTableware, // 更新餐具
	PromptDishes, // 催促订单
	CheckOut, // 结账
}

service IWaiterService{
	
	/*
	 * 开台，将一个桌从empty变为occupied
	 */
	bool occupyTable(1:string sessionId, 2:string tableId, 3:i32 customNumber) 
	throws (1:Base.UserNotLoginException ue, 2:PermissionDenyExcpetion pe, 3:TableOccupiedException te),

	/**
	 * 查询所有餐桌状态
	 */
	list<TableInfo> queryTableInfos(1:string sessionId) 
	throws (1:Base.UserNotLoginException ue, 2:PermissionDenyExcpetion pe),
	
	/*
	 * 更换餐桌。
	 */
	bool changeTable(1:string sessionId, 2:string oldTableId, 3:string newTableId) 
	throws (1:Base.UserNotLoginException ue, 2:PermissionDenyExcpetion pe, 3:TableOccupiedException te),
	
	/*
	 * 将几桌并为一桌。
	 */
	bool mergeTable(1:string sessionId, 2:list<string> oldTableIds, 3:string newTableId) 
	throws (1:Base.UserNotLoginException ue, 2:PermissionDenyExcpetion pe, 3:TableOccupiedException te),
	
	/*
	 * 清台,取消一桌的点餐状态。
	 */
	bool emptyTable(1:string sessionId, 2:string tableId) 
	throws (1:Base.UserNotLoginException ue, 2:PermissionDenyExcpetion pe),
	
	
	/**
	 * 呼叫服务
	 */
	bool callService(1:string sessionId, 2:string tableId, 3: ServiceType type) throws (1: Base.UserNotLoginException ue, 2: TableEmptyException te ),
	
	
}