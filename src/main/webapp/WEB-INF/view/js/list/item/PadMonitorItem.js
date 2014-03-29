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
                        data.timeText =  "Just connected";
                    } else {
                        data.timeText = "Connected " + data.time + " minutes ago"; }
                } else if (data.time >= 60) {
                    data.timeStatus = "error";
                    data.timeText = "Last connected > 1 hour ago";
                } else {
                    data.timeStatus = "warning";
                    data.timeText = "Lost connection for " + data.time + " minutes";
                }
            } else {
                data.timeStatus = "error";
                data.timeText = "Last connected > 1 hour ago";
            }

            return data;
        }
        
    });
    
    return PadMonitorItem;
});

