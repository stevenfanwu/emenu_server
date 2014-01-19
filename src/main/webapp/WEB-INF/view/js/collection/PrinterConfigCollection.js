/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    
    var BaseCollection = require('./BaseCollection');

    var PrinterConfigModel = require('../model/PrinterConfigModel');

    var PrinterConfigCollection = BaseCollection.extend({
        url: '/api/printers/configs',

        model: PrinterConfigModel
    });
    
    return PrinterConfigCollection;
    
});

