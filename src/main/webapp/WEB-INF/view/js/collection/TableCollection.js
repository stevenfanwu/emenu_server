/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseCollection = require('./BaseCollection');

    var TableModel = require('../model/TableModel');

    var TableCollection = BaseCollection.extend({
        url: '/api/tables',

        model: TableModel
    });
    
    return TableCollection;
    
});

