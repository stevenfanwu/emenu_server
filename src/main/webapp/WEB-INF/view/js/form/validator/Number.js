/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseValidator = require('./BaseValidator');

    var Number = BaseValidator.extend({
        
        doValidate: function (item) {
            var value = item.getValue();
            if (value === '') {
                return true;
            }
            return !isNaN(parseFloat(value)) && isFinite(value);
        }
    });
    
    return Number;
    
});

