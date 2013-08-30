/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    var MenuStatModel = require('../model/MenuStatModel');
    
    var MenuStatList = BaseTable.extend({
        heads: ['菜品分类', '总份数', '优惠数额', '总收入'],
        
        ItemType: require('./item/MenuStatItem'),

        CollectionType: require('../collection/MenuStatCollection')
    });
    
    return MenuStatList;

});

