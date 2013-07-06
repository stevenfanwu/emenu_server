/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseView = require('../BaseView');

    var DishAdmin = BaseView.extend({
        tmpl: require('./DishAdmin.handlebars')
        
    });
    
    return DishAdmin;
    
});
