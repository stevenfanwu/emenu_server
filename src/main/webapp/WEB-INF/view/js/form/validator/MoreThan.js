/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseValidator = require('./BaseValidator');

    var MoreThan = BaseValidator.extend({
        other: null,

        parseConfig: function (config) {
            BaseValidator.prototype.parseConfig.apply(this, arguments);
            this.other = config.other;
        },
        
        doValidate: function (item) {
            return item.getValue() > this.other;
        }
    });

    return MoreThan;
});

