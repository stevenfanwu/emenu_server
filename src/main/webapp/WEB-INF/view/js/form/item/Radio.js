/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');

    var Radio = BaseItem.extend({
        getValue: function () {
            return this.$('input:checked').val();
        }
        
    });

    return Radio;
});

