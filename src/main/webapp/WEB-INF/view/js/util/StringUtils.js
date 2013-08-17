/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseUtils = require('./BaseUtils');

    var StringUtils = BaseUtils.extend({
        formatDate: function (timestamp) {
            var d = new Date(timestamp);
            return d.getFullYear() + '-' + d.getMonth() + '-' + d.getDate() + ' ' +
                d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();
        }
    });
    
    return new StringUtils();
    
});

