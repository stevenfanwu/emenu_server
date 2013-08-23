/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');

    var OrderModel = require('../model/OrderModel');
    var PrintDialog = require('../dialog/PrintDialog');

    var OrderList = BaseTable.extend({
        heads: ['订单号', '就餐人数', '桌名', '服务员', '收入', '结账时间', '操作'],
        
        ItemType: require('./item/OrderItem'),

        CollectionType: require('../collection/OrderCollection'),

        initItem: function (model, item) {
            BaseTable.prototype.initItem.apply(this, arguments);

            item.on('print', function () {
                var dialog = new PrintDialog({
                    model: model
                });
                dialog.show();
            }, this);
        }
    });
    
    return OrderList;
    
});
