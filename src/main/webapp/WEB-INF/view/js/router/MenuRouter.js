/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseRouter = require('./BaseRouter');

    var MenuRouter = BaseRouter.extend({
        routes: {
            '': 'showDish',
            'dish': 'showDish',
            'menu': 'showMenu',
            'taste': 'showTaste'
        },

        showDish: function () {
            this.page.trigger('showDish');
        },

        showMenu: function () {
            this.page.trigger('showMenu');
        },

        showTaste: function () {
            this.page.trigger('showTaste');
        }
    });

    return MenuRouter;

});

