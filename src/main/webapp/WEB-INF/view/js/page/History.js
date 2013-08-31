/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var TabPage = require('./TabPage');

    var OrderList = require('../list/OrderList');
    var StatList = require('../list/StatList');
    var DishStatList = require('../list/DishStatList');
    var MenuStatList = require('../list/MenuStatList');
    var DatePicker = require('../component/DatePicker');

    var History = TabPage.extend({
        RouterType: require('../router/HistoryRouter'),

        tabEl: ['.tab-order', '.tab-stat', '.tab-dish-stat', '.tab-menu-stat'],

        initEvents: function () {
            TabPage.prototype.initEvents.apply(this, arguments);
            this.on('showOrder', function () {
                this.emptyPullRightTab();
                this.activeTab('.tab-order');
                var list = new OrderList();
                list.render();
                this.$('.bottom-content').empty();
                this.$('.bottom-content').append(list.el);
            }, this);
            this.on('showStat', function () {
                this.emptyPullRightTab();
                this.activeTab('.tab-stat');

                this.picker = new DatePicker({
                    timeMode: 'time'
                });
                this.list = new StatList();
                this.$('.bottom-content').empty();
                this.$('.bottom-content').append(this.picker.el);
                this.$('.bottom-content').append(this.list.el);

                this.picker.on('dateChanged', function (data) {
                    this.list.collection.startTime = data.start.getTime();
                    this.list.collection.endTime = data.end.getTime();
                    this.list.refresh();
                }, this);
                this.picker.render();
            }, this);
            this.on('showDishStat', function () {
                this.emptyPullRightTab();
                this.activeTab('.tab-dish-stat');

                this.picker = new DatePicker({
                    timeMode: 'time'
                });
                this.list = new DishStatList();
                this.$('.bottom-content').empty();
                this.$('.bottom-content').append(this.picker.el);
                this.$('.bottom-content').append(this.list.el);

                this.picker.on('dateChanged', function (data) {
                    this.list.collection.startTime = data.start.getTime();
                    this.list.collection.endTime = data.end.getTime();
                    this.list.refresh();
                }, this);
                this.picker.render();
            }, this);
            this.on('showMenuStat', function () {
                this.emptyPullRightTab();
                this.activeTab('.tab-menu-stat');

                this.picker = new DatePicker({
                    timeMode: 'time'
                });
                this.list = new MenuStatList();
                this.$('.bottom-content').empty();
                this.$('.bottom-content').append(this.picker.el);
                this.$('.bottom-content').append(this.list.el);

                this.picker.on('dateChanged', function (data) {
                    this.list.collection.startTime = data.start.getTime();
                    this.list.collection.endTime = data.end.getTime();
                    this.list.refresh();
                }, this);
                this.picker.render();
            }, this);
        }
    });
    
    return History;
    
});

