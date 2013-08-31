/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Backbone = require('../lib/backbone');
    var BaseView = require('../BaseView');
    var $ = require('../lib/jquery');
    var BroadcastManager = require('../misc/BroadcastManager');

    var BasePage = BaseView.extend({
        el: 'body',

        RouterType: null,

        router: null,

        broadcast: [1],

        initialize: function () {
            BaseView.prototype.initialize.apply(this, arguments);
        
            if (this.RouterType) {
                var Router = this.RouterType;
                var options = this.createRouterOptions();
                this.router = new Router(options);
            }

            this.initEvents();

            this.broadcastManager = new BroadcastManager();
            this.onRegisterBroadcast();
            this.broadcastManager.start();
        },

        onRegisterBroadcast: function () {
            this.broadcast.forEach(function (type) {
                this.broadcastManager.register(type, function (data) {
                    this.onBroadcast(type, data);
                }.bind(this));
            }, this);
        },

        onBroadcast: function (type, data) {
            if (type === 1) {
                var order = data;
                window.noty({
                    text: '有新的订单',
                    type: 'success',
                    buttons: [{
                        addClass: 'btn btn-primary btn-small',
                        text: '查看',
                        onClick: function ($noty) {
                            window.open('/bill?tableId=' + order.tableId, '_blank');
                            $noty.close();
                        }
                    }, {
                        addClass: 'btn btn-danger btn-small',
                        text: '关闭',
                        onClick: function ($noty) {
                            $noty.close();
                        }
                    }]
                });
            }
        },

        initEvents: function () {
        },

        createRouterOptions: function () {
            return {
                page: this
            };
        }
        
    });

    return BasePage;
    
});

