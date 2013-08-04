/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');

    var PadMonitorItem = BaseItem.extend({
        tmpl: require('./PadMonitorItem.handlebars'),

        tagName: 'li',

        className: 'span2 thumbnail pad-monitor-item-wrap',

        getRenderData: function () {
            var data = BaseItem.prototype.getRenderData.apply(this, arguments);
            if (data.batteryLevel >= 80) {
                data.batteryStatus = 'success';
            } else if (data.batteryLevel >= 30) {
                data.batteryStatus = 'warning';
            } else {
                data.batteryStatus = 'danger';
            }

            if (data.session) {
                var time = new Date().getTime() - data.session.activateTime;
                time = new Date(time).getMinutes();
                data.time = time;
                if (data.time < 5) {
                    data.timeStatus = "success";
                    if (data.time < 1) {
                        data.timeText =  "刚刚连接";
                    } else {
                        data.timeText = data.time + "分钟前连接";
                    }
                } else if (data.time >= 60) {
                    data.timeStatus = "error";
                    data.timeText = "超过一个小时未连接";
                } else {
                    data.timeStatus = "warning";
                    data.timeText = data.time + "分钟未连接";
                }
            } else {
                data.timeStatus = "error";
                data.timeText = "超过一个小时未连接";
            }

            return data;
        }
        
    });
    
    return PadMonitorItem;
});

