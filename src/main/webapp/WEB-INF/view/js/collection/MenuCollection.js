/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";


    var BaseCollection = require('./BaseCollection');

    var MenuModel = require('../model/MenuModel');

    var MenuCollection = BaseCollection.extend({
        url: '/api/menus',

        model: MenuModel
    });
    
    return MenuCollection;
    
});

