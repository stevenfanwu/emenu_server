/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseCollection = require('./BaseCollection');

    var OrderModel = require('../model/OrderModel');

    var OrderCollection = BaseCollection.extend({
        url: '/api/orders',

        model: OrderModel
    });
    
    return OrderCollection;
    
});

