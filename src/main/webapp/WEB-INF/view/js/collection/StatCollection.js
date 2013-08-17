/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseCollection = require('./BaseCollection');
    var StatModel = require('../model/StatModel');

    var StatCollection = BaseCollection.extend({
        url: '/api/stats',

        model: StatModel
        
    });
    
    return StatCollection;
    
});
