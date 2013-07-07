/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseList = require('./BaseList');
    var Const = require('../misc/Const');

    var DishList = BaseList.extend({
        heads: ['名称', '类型', '价格', '会员价', '单位', '辣度', '允许小数份', '特价菜', '菜品描述', '已加入菜单', '操作'],

        ItemType: require('./item/DishItem'),

        CollectionType: require('../collection/DishCollection')

    });
    
    return DishList;
});

