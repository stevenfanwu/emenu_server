/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseValidator = require('./BaseValidator');

    var IsSame = BaseValidator.extend({
        otherItem: null,

        doValidate: function (item) {
            return item.getValue() === this.otherItem.getValue();
        },

        parseConfig: function (config) {
            BaseValidator.prototype.parseConfig.apply(this, arguments);
            this.otherItem = this.form.findItemByName(config.otherItem);
        }
        
    });
    
    return IsSame;
    
});

