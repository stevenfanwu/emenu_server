include "Base.thrift"
/**
 * 订单服务
 */
namespace java cn.com.cloudstone.menu.server.thrift.api

// 餐桌未使用
exception TableEmptyException {}

// 订单含有不合法的菜，例如此菜已被沽清
exception HasInvalidGoodsException {}

// 低于最低消费
exception UnderMinChargeException {
	// 该桌最低消费
	1: i32 minCharge,
}


/**
 * 商品状态。写这个的那个人乱写的名字。
 * 对应的状态按顺序分别是
 * "已点","上过","外带","等叫","已退"
 */
enum GoodState{
	Ordered,
	Servered,
	Takeout,
	Waiting,
	Canceled,
}

/**
 * 单个商品订单
 */
struct GoodsOrder{
	// 商品id
	1: i32 id,
	// 商品数量
	2: double number,
	// 单价
	3: double price,
	// 备注列表
	4: list<string> remarks,
	// 商品名
	5: string name,
	// 商品助记码
	6: string shortName,
	// 菜系，分类
	7: string category,
	// 特价
	8: bool onSales,
	// 商品订单所属的order id
	9: i32 orderid,
	// 商品状态
	10:GoodState goodstate,
}


/**
 * 订单
 */
struct Order{
	// 商品
	1: list<GoodsOrder> goods,
	// 原价。字段无用。
	2: double originalPrice,
	// 折扣价
	3: double price,
	// 备注，订单目前没有填写备注的地方。字段无用。
	4: string remarks,
	// 桌号
	5: string tableId,
}


service IOrderService{
	/**
	 * 提交订单
	 */
	bool submitOrder(1:string sessionId, 2:Order order) 
	throws (1: Base.UserNotLoginException ue, 2: TableEmptyException te, 3: HasInvalidGoodsException he, 4: UnderMinChargeException ume ),

	/**
	 * 查询某桌当前订单
	 */
	list<Order> queryOrder(1:string sessionId, 2:string tableId) 
	throws (1: Base.UserNotLoginException ue, 2: TableEmptyException te ),

	/**
	 * 退货
	 */
	bool cancelGoods(1:string sessionId, 2:i32 orderId, 3:i32 goodsId)
	throws (1: Base.UserNotLoginException ue, 2: Base.AException te ),
}