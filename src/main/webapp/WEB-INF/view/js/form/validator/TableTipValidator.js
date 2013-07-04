/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseValidator = require('./BaseValidator');

    var TableTipValidator = BaseValidator.extend({
        errorMessage: '请输入大于0的数字',

        doValidate: function (item) {
            var tipMode = item.getValue().tipMode;
            var tip = item.getValue().tip;
            if (tipMode !== 0) {
                if (isNaN(tip)) {
                    return false;
                }
                return tip >= 0;
            }
            return true;
        }
    });
    
    return TableTipValidator;
    
});

