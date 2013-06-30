/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Backbone = require('../lib/backbone');
    var BaseView = require('../BaseView');
    var $ = require('../lib/jquery');

    var BasePage = BaseView.extend({
        el: 'body',

        RouterType: null,

        router: null,

        initialize: function () {
            BaseView.prototype.initialize.apply(this, arguments);
        
            if (this.RouterType) {
                var Router = this.RouterType;
                var options = this.createRouterOptions();
                this.router = new Router(options);
            }

            this.initEvents();
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

