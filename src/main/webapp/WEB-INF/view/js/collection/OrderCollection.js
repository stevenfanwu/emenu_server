/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseCollection = require('./BaseCollection');

    var OrderModel = require('../model/OrderModel');

    var OrderCollection = BaseCollection.extend({
        baseUrl: '/api/orders',

        model: OrderModel,

        startTime: null,

        endTime: null,

        url : function () {
            if (this.startTime) {
                return this.baseUrl + '?start=' + this.startTime
                    + '&end=' + this.endTime;
            }
            return this.baseUrl;
        }
    });
    
    return OrderCollection;
    
});

