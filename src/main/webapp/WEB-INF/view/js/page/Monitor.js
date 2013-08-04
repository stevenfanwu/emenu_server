/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var TabPage = require('./TabPage');
    var PadList = require('../list/PadMonitorList');

    var Monitor = TabPage.extend({
        RouterType: require('../router/MonitorRouter'),

        tabEl: ['.tab-pad'],
        
        initEvents: function () {
            TabPage.prototype.initEvents.apply(this, arguments);
            this.on('showPad', function () {
                this.activeTab('.tab-pad');
                this.list = new PadList();
                this.list.render();
                this.$('.monitor-wrap').html(this.list.el);
            }, this);
        }
    });
    
    return Monitor;
    
});

