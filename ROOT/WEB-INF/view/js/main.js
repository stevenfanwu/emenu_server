/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Backbone = require('./lib/backbone');
    var $ = require('./lib/jquery');
    var PageDataUtils = require('./util/PageDataUtils');

    var page = PageDataUtils.getData("pageConfig").name;

    window.Number.prototype.toMoney = function () {
        return Math.round(this * 100) / 100;
    };

    var mod = './page/' + page;
    require.async(mod, function (Page) {
        var page = new Page();
        if (page.router) {
            Backbone.history.start();
        }
    });
});
