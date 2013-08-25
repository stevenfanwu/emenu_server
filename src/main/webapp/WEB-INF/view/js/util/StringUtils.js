/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseUtils = require('./BaseUtils');
    var DPGlobal = window.jQuery.fn.datetimepicker.DPGlobal;

    var StringUtils = BaseUtils.extend({
        formatDate: function (timestamp, format) {
            if (!format) {
                format = 'yyyy-mm-dd hh:ii:ss';
            }
            format = this.parseFormat(format);
            return DPGlobal.formatDate(new Date(timestamp + 8 * 3600 * 1000),
                format, 'zh-CN', 'standard');
        },

        parseFormat: function (format) {
            return DPGlobal.parseFormat(format, 'standard');
        },

        formatDateDay: function (timestamp) {
            return this.formatDate(timestamp, 'yyyy-mm-dd');
        },

        formatPercentage: function (data) {
            return Math.round(data * 100) / 100 + '%';
        },

        parseDate: function (dateStr, format) {
            format = this.parseFormat(format);
            var  time = DPGlobal.parseDate(dateStr, format, 'zh-CN', 'standard').getTime();
            return new Date(time - 8 * 3600 * 1000);
        }
    });
    
    return new StringUtils();
    
});

