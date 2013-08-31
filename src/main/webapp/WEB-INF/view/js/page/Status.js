/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var TabList = require('./TabList');
    var TableModel = require('../model/TableModel');

    var Status = TabList.extend({
        RouterType: require('../router/StatusRouter'),

        tabEl: ['.tab-all', '.tab-room', '.tab-hall', '.tab-booth'],

        ListType: require('../list/TableStatusList'),

        onBroadcast: function (type, data) {
            TabList.prototype.onBroadcast.apply(this, arguments);

            if (type === 1) {
                window.location.reload(true);
            }
        },
        
        initEvents: function () {
            TabList.prototype.initEvents.apply(this, arguments);
            this.on('showAll', function () {
                this.list.showAll();
                this.activeTab('.tab-all');
            }, this);
            this.on('showRoom', function () {
                this.list.showRoom();
                this.activeTab('.tab-room');
            }, this);
            this.on('showHall', function () {
                this.list.showHall();
                this.activeTab('.tab-hall');
            }, this);
            this.on('showBooth', function () {
                this.list.showBooth();
                this.activeTab('.tab-booth');
            }, this);
        }
    });
    
    return Status;
    
});

