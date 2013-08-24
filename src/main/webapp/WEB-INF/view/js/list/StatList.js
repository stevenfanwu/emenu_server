/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    var StatModel = require('../model/StatModel');
    
    var StatList = BaseTable.extend({
        heads: ['时间', '总单数', '翻桌率', '客流量', '订单均价', '人均消费', '开发票数',
            '发票总额', '优惠数额', '服务费总额', '总收入'],
        
        ItemType: require('./item/StatItem'),

        CollectionType: require('../collection/StatCollection')
    });
    
    return StatList;
    
});

