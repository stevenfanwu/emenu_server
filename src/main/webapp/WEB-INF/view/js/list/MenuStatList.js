/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    var MenuStatModel = require('../model/MenuStatModel');
    var MenuStatSum = require('../component/MenuStatSum');
    
    var MenuStatList = BaseTable.extend({
        heads: ['Category', 'Qty', 'Discount', 'Total Sales'],
        
        ItemType: require('./item/MenuStatItem'),

        CollectionType: require('../collection/MenuStatCollection'),

        doRender: function () {
            BaseTable.prototype.doRender.apply(this, arguments);
            this.renderSum();
        },

        renderSum: function () {
            var data = {
                count: 0,
                discount: 0,
                income: 0
            };
            data = this.collection.reduce(function (data, model) {
                var stat = model.toJSON();
                data.count = data.count + stat.count;
                data.discount = data.discount + stat.discount;
                data.income = data.income + stat.income;
                return data;
            }, data, this);
            var sumView = new MenuStatSum({
                data: data
            });
            sumView.render();
            this.$('tbody').append(sumView.el);
        }
    });
    
    return MenuStatList;

});

