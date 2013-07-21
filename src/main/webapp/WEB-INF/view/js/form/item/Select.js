/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');

    var Select = BaseItem.extend({

        setValue: function (value) {
            this.$('input').prop('selected', false);
            this.$('input[value=' + value + ']').prop('selected', true);
        },
        
        getValue: function () {
            return this.$('option:selected').val();
        }
        
    });

    return Select;
});

