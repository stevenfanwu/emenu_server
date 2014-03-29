/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    var DishStatModel = require('../model/DishStatModel');
    var DishStatSum = require('../component/DishStatSum');
    
    var DishStatList = BaseTable.extend({
        heads: ['Dish Name', 'Category', 'Qty', 'Price', 'Order canceled', 'Discount', 'Total Sales'],
        
        ItemType: require('./item/DishStatItem'),

        CollectionType: require('../collection/DishStatCollection'),

        doRender: function () {
            BaseTable.prototype.doRender.apply(this, arguments);
            this.renderSum();
        },

        renderSum: function () {
            var data = {
                count: 0,
                backCount: 0,
                discount: 0,
                income: 0
            };
            data = this.collection.reduce(function (data, model) {
                var stat = model.toJSON();
                data.count = data.count + stat.count;
                data.backCount = data.backCount + stat.backCount;
                data.discount = data.discount + stat.discount;
                data.income = data.income + stat.income;
                return data;
            }, data, this);
            var sumView = new DishStatSum({
                data: data
            });
            sumView.render();
            this.$('tbody').append(sumView.el);
        }
        
    });
    
    return DishStatList;

});

