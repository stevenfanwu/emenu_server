/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    var DishStatModel = require('../model/DishStatModel');
    
    var DishStatList = BaseTable.extend({
        heads: ['菜品名称', '菜品分类', '总份数', '单价', '退菜数量', '优惠数额', '总收入'],
        
        ItemType: require('./item/DishStatItem'),

        CollectionType: require('../collection/DishStatCollection')
    });
    
    return DishStatList;

});

