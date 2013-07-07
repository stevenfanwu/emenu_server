/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseValidator = function (options) {
        options = options || {};
        Object.keys(options).forEach(function (key) {
            this[key] = options[key];
        }, this);
    };

    BaseValidator.prototype.errorMessage = null;

    BaseValidator.prototype.validate = function (item) {
        if (!this.doValidate(item)) {
            return {
                success: false,
                error: this.errorMessage
            };
        }
        return {
            success: true
        };
    };

    BaseValidator.prototype.doValidate = function (item) {
        return true;
    };

    BaseValidator.prototype.parseConfig = function (config) {
        this.errorMessage = config.errorMessage || this.errorMessage;
    };

    BaseValidator.extend = function (options) {
        var instance = new BaseValidator();

        options = options || {};
        Object.keys(options).forEach(function (key) {
            instance[key] = options[key];
        });

        var SubType = function () {
            BaseValidator.apply(this, arguments);
        };
        SubType.prototype = instance;
        return SubType;
    };

    return BaseValidator;
});

