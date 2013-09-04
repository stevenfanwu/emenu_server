/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var TabList = require('./TabList');
    var TableModel = require('../model/TableModel');
    var _ = require('../lib/underscore');

    var Status = TabList.extend({
        RouterType: require('../router/StatusRouter'),

        tabEl: ['.tab-all', '.tab-room', '.tab-hall', '.tab-booth'],

        ListType: require('../list/TableStatusList'),

        broadcast: _.union(TabList.prototype.broadcast, [2, 3]),

        onBroadcast: function (type, data) {
            if (type === 1 || type === 2 || type === 3) {
                window.location.reload(true);
            } else {
                TabList.prototype.onBroadcast.apply(this, arguments);
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

