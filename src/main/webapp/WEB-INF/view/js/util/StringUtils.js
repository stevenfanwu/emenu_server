/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseUtils = require('./BaseUtils');

    var StringUtils = BaseUtils.extend({
        formatDate: function (timestamp) {
            var d = new Date(timestamp);
            return d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' ' +
                d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();
        },

        formatDateDay: function (timestamp) {
            var d = new Date(timestamp);
            return d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate();
        },

        formatPercentage: function (data) {
            return Math.round(data * 100) / 100 + '%';
        }
    });
    
    return new StringUtils();
    
});

