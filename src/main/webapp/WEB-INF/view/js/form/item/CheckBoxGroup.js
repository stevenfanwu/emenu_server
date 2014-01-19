/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');
    var $ = require('../../lib/jquery');
    
    var CheckBoxGroup = BaseItem.extend({
        
        valueEl: '.checkbox-group',

        getValue: function () {
            var value = [];
            this.$('input:checked').each(function (index, el) {
                value.push($(el).val());
            });
            return value;
        },

        setValue: function (value) {
            this.value = value;
        }
        
        
    });
    
    return CheckBoxGroup;
    
});
