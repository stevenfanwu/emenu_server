/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseCollection = require('./BaseCollection');
    var RestaurantModel = require('../model/RestaurantModel');

    var RestaurantCollection = BaseCollection.extend({
        url: '/api/restaurants',

        model: RestaurantModel 
    });
    
    return  RestaurantCollection;
});

