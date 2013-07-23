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
            'dishTag': 'showDishTag'
        },

        showDish: function () {
            this.page.trigger('showDish');
        },

        showMenu: function () {
            this.page.trigger('showMenu');
        },

        showDishTag: function () {
            this.page.trigger('showDishTag');
        }
    });

    return MenuRouter;

});

