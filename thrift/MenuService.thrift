include "Base.thrift"
/**
 * 菜单服务
 */
namespace java cn.com.cloudstone.menu.server.thrift.api

/**
 * 图片对象
 */
struct Img{
	// 预览图url
	1: string previewImgUrl,
	// 原图url
	2: string imgUrl,
}

/**
 * 商品，菜品
 */
struct Goods{
	// 商品id，unique
	1: i32 id,
	// 商品名
	2: string name,
	// 商品助记码
	3: string shortName,
	// 商品价格
	4: double price,
	// 简介，配料
	5: string introduction,
	// 原料
	6: list<string> material,
	// 配料
	7: list<string> condiments,
	// 菜品图，可能一道菜存在多幅图
	8: list<Img> imgs,
	// 菜系，分类
	9: string category,
	// 是否是特价菜
	10: bool onSales,
	//0~5 辣度，默认为0
	11: i32 spicy,
	// 是否售完，true则不可点选
	12: bool soldout,
	// 是否允许点餐数量为小数, 默认为false，若为true则允许为小数。
	13:bool numberDecimalPermited,
}

/**
 * 页面布局类型
 * 每一页的布局方式，后面的数字表示此页的菜的数量。
 * 比如 Triangle4，页面三角形布局，此页4个菜。
 */
enum PageLayoutType{
	// 水平
	Horizontal1, 
	Horizontal2, 
	Horizontal3, 
	Triangle4, 
	Grid6
}

/**
 * 菜品页
 */
struct MenuPage{
	// 页面id
	1: i32 id,
	// 页面布局方式
	2: PageLayoutType layoutType,
	// 该页菜列表，数量应小于等于该页布局容纳上限。
	3: list<Goods> goodsList,
}

/**
 * 菜单
 */
struct Menu{
	// 菜单id
	1: i32 id,
	// 菜单名
	2: string name,
	// 菜单页面列表
	3: list<MenuPage> pages,
	// 左上角显示的menu logo。
	4: string menuLogo,
}

service IMenuService{
	/**
	 * 获取当前菜单
	 */
	Menu getCurrentMenu(1:string sessionId)
        throws (1: Base.UserNotLoginException ue), 
	
	/**
	 * 获取所有备注，顾客点菜时有可能会为各个菜添加备注。
	 * 例如：微辣、不放辣、清淡、少糖、不放葱、不放蒜
	 */ 
	list<string> getAllNotes(1:string sessionId)
        throws (1: Base.UserNotLoginException ue), 
}
