/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseValidator = require('./BaseValidator');

    var Integer = BaseValidator.extend({
        doValidate: function (item) {
            var value = item.getValue();
            if (value === '') {
                return true;
            }
            return String(parseInt(value, 10)) === String(value);
        }
    });

    return Integer;
    
});

