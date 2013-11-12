/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseValidator = require('./BaseValidator');

    var MoreThan = BaseValidator.extend({
        other: null,

        including: false,

        parseConfig: function (config) {
            BaseValidator.prototype.parseConfig.apply(this, arguments);
            this.other = config.other;
            if (config.including !== undefined) {
                this.including = config.including;
            }
        },
        
        doValidate: function (item) {
            var value = item.getValue();
            if (value === '') {
                return true;
            }
            value = parseFloat(value, 10);
            if (this.including) {
                return value >= this.other;
            }
            return value > this.other;
        }
    });

    return MoreThan;
});

