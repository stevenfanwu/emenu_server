/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseCollection = require('./BaseCollection');

    var PrintComponentModel = require('../model/PrintComponentModel');

    var PrintComponentCollection = BaseCollection.extend({
        url: '/api/printers/components',

        model: PrintComponentModel
    });
    
    return PrintComponentCollection;
    
});

