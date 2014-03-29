/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    var PadModel = require('../model/PadModel');

    var PadList = BaseTable.extend({
        heads: ['Name', 'IMEI', 'Note', 'Action'],

        CollectionType: require('../collection/PadCollection'),
        
        ItemType: require('./item/PadItem'),

        initialize: function () {
            BaseTable.prototype.initialize.apply(this, arguments);
        
            this.on('createPad', this.onCreatePad, this);
        },

        initItem: function (model, item) {
            BaseTable.prototype.initItem.apply(this, arguments);
            model.on('edit', this.onEditPad, this);
            model.on('delete', this.onDeletePad, this);
        },

        showEditPadDialog: function (padModel) {
            var Dialog = require('../dialog/EditPadDialog');
            var dialog = new Dialog({
                model: padModel
            });
            dialog.model.on('saved', function () {
                this.refresh();
            }, this);
            dialog.show();
        },
        
        /* -------------------- Event Listener ----------------------- */
        
        onCreatePad: function () {
            var padModel = new PadModel();
            this.showEditPadDialog(padModel);
        },
        
        onEditPad: function (padModel) {
            this.showEditPadDialog(padModel);
        },

        onDeletePad: function (padModel) {
            if (window.confirm('确定删除平板' + padModel.get('name'))) {
                padModel.destroy({
                    success: function () {
                        this.refresh();
                    }.bind(this)
                });
            }
        }
    });
    
    return PadList;
    
});
