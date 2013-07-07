/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseCollection = require('./BaseCollection');

    var DishModel = require('../model/DishModel');

    var DishCollection = BaseCollection.extend({
        url: '/api/dishes',

        model: DishModel
    });
    
    return DishCollection;
    
});

