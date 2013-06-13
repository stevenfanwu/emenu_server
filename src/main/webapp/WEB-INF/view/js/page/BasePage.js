/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Backbone = require('../lib/backbone');
    var BaseView = require('../BaseView');

    var BasePage = BaseView.extend({
        el: 'body'
    });

    return BasePage;
    
});

