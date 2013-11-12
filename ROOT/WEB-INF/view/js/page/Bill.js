/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BasePage = require('./BasePage');
    var OrderModel = require('../model/OrderModel');
    var BillModel = require('../model/BillModel');
    var PageDataUtils = require('../util/PageDataUtils');
    var BillForm = require('../form/BillForm');
    var $ = require('../lib/jquery');
    var _ = require('../lib/underscore');
    var Backbone = require('../lib/backbone');

    var DishItem = Backbone.View.extend({
        events: {
            'click input[type=checkbox]': 'onCheckedChange',
            'click .btn-cancel-dish': 'onCancelDish',
            'click .btn-free-dish': 'onFreeDish'
        },

        isChecked: function () {
            return this.$('input[type=checkbox]').is(':checked');
        },

        onCheckedChange: function (evt) {
            this.model.trigger('checkedChange');
        },

        onCancelDish: function (evt) {
            evt.preventDefault();

            var Dialog = require('../dialog/CancelDishDialog');
            var dialog = new Dialog({
                model: this.model
            });
            dialog.model.on('saved', function () {
                window.location.reload(true);
            }.bind(this));
            dialog.show();

            evt.stopPropagation();
        },

        onFreeDish: function (evt) {
            evt.preventDefault();

            var Dialog = require('../dialog/CancelDishDialog');
            var dialog = new Dialog({
                model: this.model,
                mode: 'free'
            });
            dialog.model.on('saved', function () {
                window.location.reload(true);
            }.bind(this));
            dialog.show();

            evt.stopPropagation();
        }
        
    });

    var Bill = BasePage.extend({

        events: _.defaults({
            'keyup input[name=discount]': 'onDiscountChange',
            'keyup input[name=tip]': 'onTipChange'
        }, BasePage.prototype.events),

        dishItems: [],

        initialize: function () {
            BasePage.prototype.initialize.apply(this, arguments);
        
            //step 1. get OrderModel from pageData
            this.orderModel = new OrderModel(PageDataUtils.getData('order'));

            //step 2. build BillModel
            var originPrice = this.orderModel.get('originPrice');
            var bill = PageDataUtils.getData('bill');
            if (!bill) {
                bill = {
                    tip : this.orderModel.tableModel.computeTip(originPrice),
                    discount: 10,
                    orderId: this.orderModel.get('id')
                };
            }
            this.model = new BillModel();
            this.model.set(bill);
            this.model.on('computeCost', this.onComputeCost, this);

            //step 3. build form
            this.form = new BillForm();
            this.form.page = this;
            this.form.model = this.model;
            this.form.orderModel = this.orderModel;
            this.form.init(this.$('.form-bill')[0]);

            //step 4 build DishItems
            this.dishItems = [];
            this.orderModel.dishCollection.forEach(function (dishModel) {
                var item = new DishItem();
                item.model = dishModel;
                var id = item.model.get('id');
                item.setElement(this.$('#dish-item-' + id));
                this.dishItems.push(item);

                item.model.on('checkedChange', this.onComputeCost, this);
            }, this);
            
            this.model.trigger('computeCost');
        },

        /* -------------------- Event Listener ----------------------- */
        
        onComputeCost: function () {
            var bill = this.model.toJSON();
            var order = bill.order || {};
            order.status = order.status || 0;
            if (order.status === 0) {
                bill.discount = parseFloat(bill.discount, 10);
                bill.tip = bill.tip === '' ? 0 : parseFloat(bill.tip, 10);

                var cost = 0;
                this.dishItems.forEach(function (item) {
                    if (item.isChecked()) {
                        cost = cost + (item.model.get('totalCost') * bill.discount / 10);
                    } else {
                        cost = cost + item.model.get('totalCost');
                    }
                }, this);
                cost = cost + bill.tip;
                cost = cost.toMoney();
                this.model.set('cost', cost);
            }
            this.form.findItemByName('cost').setValueFromModel(this.model);
        },

        onDiscountChange: function (evt) {
            var item =  this.form.findItemByName('discount');
            if (item.validate()) {
                var discount = item.getValue();
                this.model.set('discount', discount);
                this.model.trigger('computeCost');
            }
        },

        onTipChange: function (evt) {
            var item =  this.form.findItemByName('tip');
            if (item.validate()) {
                var tip = item.getValue();
                this.model.set('tip', tip);
                this.model.trigger('computeCost');
            }
        }
        
        
    });
    
    return Bill;
    
});

