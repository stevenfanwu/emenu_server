/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseValidator = require('./BaseValidator');

    var Required = BaseValidator.extend({
        doValidate: function (item) {
            return !item.isEmpty();
        }
    });

    return Required;
});

