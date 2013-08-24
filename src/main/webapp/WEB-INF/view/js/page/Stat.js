/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var TabPage = require('./TabPage');
    var StatList = require('../list/StatList');
    var OrderList = require('../list/OrderList');

    var Stat = TabPage.extend({
        RouterType: require('../router/StatRouter'),
        
        tabEl: ['.tab-order', '.tab-stat'],

        initEvents: function () {
            TabPage.prototype.initEvents.apply(this, arguments);
            this.on('showOrder', function () {
                this.emptyPullRightTab();
                this.activeTab('.tab-order');

                var DatePicker = require('../component/DatePicker');
                this.picker = new DatePicker();
                this.list = new OrderList();
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
            this.on('showStat', function () {
                this.emptyPullRightTab();
                this.activeTab('.tab-stat');
                var list = new StatList();
                list.render();
                this.$('.bottom-content').empty();
                this.$('.bottom-content').append(list.el);
            }, this);
        }
    });
    
    return Stat;
    
});

