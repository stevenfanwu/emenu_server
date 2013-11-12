/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseCollection = require('./BaseCollection');

    var PrintTemplateModel = require('../model/PrintTemplateModel');

    var PrintTemplateCollection = BaseCollection.extend({
        url: '/api/printers/templates',

        model: PrintTemplateModel
    });
    
    return PrintTemplateCollection;
    
});

