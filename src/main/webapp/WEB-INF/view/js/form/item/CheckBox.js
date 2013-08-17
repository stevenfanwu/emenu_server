/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');

    var CheckBox = BaseItem.extend({

        setValue: function (value) {
            this.$('input').prop('checked', value);
        },
        
        getValue: function () {
            return this.$('input').is(':checked');
        }
        
    });

    return CheckBox;
    
});

