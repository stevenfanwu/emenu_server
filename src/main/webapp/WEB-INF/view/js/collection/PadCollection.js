/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseCollection = require('./BaseCollection');

    var PadModel = require('../model/PadModel');

    var PadCollection = BaseCollection.extend({
        url: '/api/pads',

        model: PadModel
    });
    
    return PadCollection;
});

