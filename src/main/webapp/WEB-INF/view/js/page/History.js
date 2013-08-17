/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var TabPage = require('./TabPage');

    var OrderList = require('../list/OrderList');

    var History = TabPage.extend({
        RouterType: require('../router/HistoryRouter'),

        tabEl: ['.tab-order'],

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
        }
    });
    
    return History;
    
});

