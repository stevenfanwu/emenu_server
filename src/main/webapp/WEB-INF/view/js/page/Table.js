/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var TabList = require('./TabList');

    var Table = TabList.extend({
        RouterType: require('../router/TableRouter'),

        ListType: require('../list/TableList'),

        tabEl: ['.tab-all', '.tab-room', '.tab-hall', '.tab-booth'],
        
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
    
    return Table;
    
});

