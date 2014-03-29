/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    var DishNoteModel = require('../model/DishNoteModel');

    var DishNoteList = BaseTable.extend({
        heads: ['Dish Note', 'Action'],

        CollectionType: require('../collection/DishNoteCollection'),
        
        ItemType: require('./item/DishNoteItem'),

        initialize: function () {
            BaseTable.prototype.initialize.apply(this, arguments);
        
            this.on('createDishNote', this.onCreateDishNote, this);
            this.collection.on('edit', this.onEditDishNote, this);
            this.collection.on('delete', function (noteModel) {
                if (window.confirm('Delete "' + noteModel.get('name') + '"?')) {
                    noteModel.destroy({
                        success: function () {
                            this.refresh();
                        }.bind(this)
                    });
                }
            }, this);
        },

        showEditDishNoteDialog: function (menuModel) {
            var Dialog = require('../dialog/EditDishNoteDialog');
            var dialog = new Dialog({
                model: menuModel
            });
            dialog.model.on('saved', function () {
                this.refresh();
            }, this);
            dialog.show();
        },
        
        /* -------------------- Event Listener ----------------------- */
        
        onCreateDishNote: function () {
            this.showEditDishNoteDialog(new DishNoteModel());
        },

        onEditDishNote: function (noteModel) {
            this.showEditDishNoteDialog(noteModel);
        }

    });
    
    return DishNoteList;
    
});

