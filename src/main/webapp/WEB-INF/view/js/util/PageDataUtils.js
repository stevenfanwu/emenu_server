/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseUtils = require('./BaseUtils');
    var $ = require('../lib/jquery');
    
    var PageDataUtils = BaseUtils.extend({
        get: function (key) {
            return $('span.page-data').data(key);
        },

        set: function (key, value) {
            $('span.page-data').data(key, value);
        }
    });

    return new PageDataUtils();
});

