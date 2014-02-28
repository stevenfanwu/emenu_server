/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var TabPage = require('./TabPage');
    var PadList = require('../list/PadList');

    var Device = TabPage.extend({
        RouterType: require('../router/DeviceRouter'),

        tabEl: ['.tab-pad'],
        
        initEvents: function () {
            TabPage.prototype.initEvents.apply(this, arguments);
            this.on('showPad', function () {
                this.activeTab('.tab-pad');
                this.list = new PadList();
                this.list.render();
                this.$('.device-wrap').html(this.list.el);
                var tab = this.appendPullRight('Add device');
                tab.on('click', function () {
                    this.list.trigger('createPad');
                }, this);
            }, this);
        }
    });
    
    return Device;
    
});

