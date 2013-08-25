/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";
    
    //http://www.malot.fr/bootstrap-datetimepicker/

    var BaseView = require('../BaseView');
    var StringUtils = require('../util/StringUtils');

    var DatePicker = BaseView.extend({
        tmpl: require('./DatePicker.handlebars'),

        className: 'date-picker',

        initialize: function () {
            BaseView.prototype.initialize.apply(this, arguments);

            this.options = this.options || {};
            this.options.mode = this.options.mode || 'pair';
            this.options.timeMode = this.options.timeMode || 'date';
        },
        

        render: function () {
            BaseView.prototype.render.apply(this, arguments);
            this.init();
        },

        init: function () {
            var startOptions = this.getInitOptions(true);
            this.$('.date-start').datetimepicker(startOptions);
            var time = startOptions.initialDate.getTime();
            time = StringUtils.formatDate(time, startOptions.format);
            this.$('.date-start input').val(time);

            var endOptions = this.getInitOptions(false);
            this.$('.date-end').datetimepicker(endOptions);
            time = endOptions.initialDate.getTime();
            time = StringUtils.formatDate(time, endOptions.format);
            this.$('.date-end input').val(time);

            this.$('.date').datetimepicker().on('changeDate', function () {
                this.triggerChange();
            }.bind(this));

            this.triggerChange();
        },

        getInitOptions: function (isStart) {
            var options = {
                autoclose: true,
                language: 'zh-CN',
                format: this.getFormat(),
                minView: this.getMinView(),
                maxView: this.getMaxView(),
                startView: this.getMaxView(),
                todayBtn: this.options.timeMode !== 'time',
                todayHighlight: this.options.timeMode !== 'time'
            };
            options.initialDate = this.getInitDate(isStart);

            return options;
        },

        getInitDate: function (isStart) {
            var HOUR = 3600 * 1000;
            var DAY = 24 * HOUR;
            var time = new Date().getTime();
            time = Math.floor((time + 8 * HOUR) / DAY) * DAY - 8 * HOUR;

            if (this.options.timeMode === 'time') {
                if (!isStart) {
                    time = time + DAY - 1;
                }
            } else {
                if (isStart) {
                    time = time - 6 * DAY;
                } else {
                    time = time + DAY - 1;
                }
            }
            return new Date(time);
        },

        getFormat: function () {
            if (this.options.timeMode === 'time') {
                return 'yyyy-mm-dd hh:ii';
            }
            return 'yyyy-mm-dd';
        },

        getMinView: function () {
            if (this.options.timeMode === 'time') {
                return 1;
            }
            return 2;
        },

        getMaxView: function () {
            if (this.options.timeMode === 'time') {
                return 1;
            }
            return 2;
        },

        triggerChange: function () {
            var data = {};
            var dateStr = this.$('.date-start input').val();
            var format = this.getFormat();
            data.start = StringUtils.parseDate(dateStr, format);

            dateStr = this.$('.date-end input').val();
            data.end = StringUtils.parseDate(dateStr, format);
            if (this.options.timeMode === 'date') {
                data.end = new Date(data.end.getTime() + 24 * 3600 * 1000 - 1);
            }

            this.trigger('dateChanged', data);
        }
        
    });
    
    return DatePicker;
    
});

