/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseValidator = function () {};

    BaseValidator.prototype.errorMessage = null;

    BaseValidator.prototype.validate = function (item) {
        if (!this.doValidate(item)) {
            item.showError(this.errorMessage);
            return false;
        }
        return true;
    };

    BaseValidator.prototype.doValidate = function (item) {
        return true;
    };

    BaseValidator.prototype.parseConfig = function (config) {
        this.errorMessage = config.errorMessage;
    };

    exports.extend = function (options) {
        var instance = new BaseValidator();

        options = options || {};
        Object.keys(options).forEach(function (key) {
            instance[key] = options[key];
        });

        var SubType = function () {};
        SubType.prototype = instance;
        return SubType;
    };
});

