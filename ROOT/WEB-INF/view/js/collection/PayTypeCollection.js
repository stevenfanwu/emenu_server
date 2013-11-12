/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseCollection = require('./BaseCollection');
    var PayTypeModel = require('../model/PayTypeModel');

    var PayTypeCollection = BaseCollection.extend({
        url: '/api/pay-types',

        model: PayTypeModel
    });
    
    return PayTypeCollection;
});

