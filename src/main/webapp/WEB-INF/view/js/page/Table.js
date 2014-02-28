/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var TabList = require('./TabList');
    var TableModel = require('../model/TableModel');

    var Table = TabList.extend({
        RouterType: require('../router/TableRouter'),

        ListType: require('../list/TableList'),

        tabEl: ['.tab-all', '.tab-room', '.tab-hall', '.tab-booth'],

        events: {
            'click .btn-create-table': 'onCreateTable'
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
            this.list.collection.on('edit', this.onEditTable, this);
            this.list.collection.on('delete', this.onDeleteTable, this);
        },

        /* -------------------- Event Listener ----------------------- */
        
        onCreateTable: function (evt) {
            evt.preventDefault();
            var Dialog = require('../dialog/EditTableDialog');
            var dialog = new Dialog({
                model: new TableModel({
                    minCharge: 0,
                    tipMode: 0
                })
            });
            dialog.model.on('saved', function () {
                this.list.refresh();
            }, this);
            dialog.show();
            evt.stopPropagation();
        },

        onDeleteTable: function (model) {
            if (window.confirm('Delete table' + model.get('name') + '?')) {
                model.destroy({
                    success: function () {
                        this.list.refresh();
                    }.bind(this)
                });
            }
        },
        
        onEditTable: function (model) {
            var Dialog = require('../dialog/EditTableDialog');
            var dialog = new Dialog({
                model: model
            });
            dialog.model.on('saved', function () {
                this.list.refresh();
            }, this);
            dialog.show();
        }
        
    });
    
    return Table;
    
});

