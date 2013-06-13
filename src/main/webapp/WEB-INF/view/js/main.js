/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Backbone = require('./lib/backbone');
    var $ = require('./lib/jquery');

    var page = $('#pageName').val();
    var mod = './page/' + page;
    require.async(mod, function (Page) {
        new Page();
    });
});
