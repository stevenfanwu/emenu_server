/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Backbone = require('../lib/Backbone');

    var BaseCollection  = Backbone.Collection.extend({
    });
    
    return BaseCollection;
});

