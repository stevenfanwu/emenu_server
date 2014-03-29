/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    var DishTagModel = require('../model/DishTagModel');

    var DishTagList = BaseTable.extend({
        heads: ['Cooking Method', 'Action'],

        CollectionType: require('../collection/DishTagCollection'),
        
        ItemType: require('./item/DishTagItem'),

        initialize: function () {
            BaseTable.prototype.initialize.apply(this, arguments);
        
            this.on('createDishTag', this.onCreateDishTag, this);
            this.collection.on('edit', this.onEditDishTag, this);
            this.collection.on('delete', function (tagModel) {
                if (window.confirm('Delete "' + tagModel.get('name') + '"?')) {
                    tagModel.destroy({
                        success: function () {
                            this.refresh();
                        }.bind(this)
                    });
                }
            }, this);
        },

        showEditDishTagDialog: function (menuModel) {
            var Dialog = require('../dialog/EditDishTagDialog');
            var dialog = new Dialog({
                model: menuModel
            });
            dialog.model.on('saved', function () {
                this.refresh();
            }, this);
            dialog.show();
        },
        
        /* -------------------- Event Listener ----------------------- */
        
        onCreateDishTag: function () {
            this.showEditDishTagDialog(new DishTagModel());
        },

        onEditDishTag: function (tagModel) {
            this.showEditDishTagDialog(tagModel);
        }

    });
    
    return DishTagList;
    
});

