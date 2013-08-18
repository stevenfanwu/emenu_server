/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    var StatModel = require('../model/StatModel');
    
    var StatList = BaseTable.extend({
        heads: ['时间', '收入', '桌次', '客流量', '翻桌率'],
        
        ItemType: require('./item/StatItem'),

        CollectionType: require('../collection/StatCollection')
    });
    
    return StatList;
    
});

