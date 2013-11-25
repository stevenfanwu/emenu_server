/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";
    
    var BaseUtils = function () {};

    exports.extend = function (options) {
        var instance = new BaseUtils();

        options = options || {};
        Object.keys(options).forEach(function (key) {
            instance[key] = options[key];
        });

        var SubType = function () {};
        SubType.prototype = instance;
        return SubType;
    };
});

