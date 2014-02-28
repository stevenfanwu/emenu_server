/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseView = require('../BaseView');
    var MenuModel = require('../model/MenuModel');

    var MenuAdmin = BaseView.extend({

        initialize: function () {
            BaseView.prototype.initialize.apply(this, arguments);
        
            this.on('createMenu', this.onCreateMenu, this);
        },

        render: function () {
            BaseView.prototype.render.apply(this, arguments);
        
            if (!this.list) {
                var List = require('../list/MenuList');
                this.list = new List();
                this.list.on('fetched', function (collection) {
                    this.trigger('fetched', collection);
                }, this);
                this.list.collection.on('edit', this.onEditMenu, this);
                this.list.collection.on('delete', this.onDeleteMenu, this);
            }
            this.list.render();
            this.$el.append(this.list.el);
        },

        showEditMenuDialog: function (menuModel) {
            var Dialog = require('../dialog/EditMenuDialog');
            var dialog = new Dialog({
                model: menuModel
            });
            dialog.model.on('saved', function () {
                this.list.refresh();
            }, this);
            dialog.show();
        },
        
        /* -------------------- Event Listener ----------------------- */

        onDeleteMenu: function (menuModel) {
            if (window.confirm('Delete menu "' + menuModel.get('name') + '"?')) {
                menuModel.destroy({
                    success: function () {
                        this.list.refresh();
                    }.bind(this)
                });
            }
        },

        onEditMenu: function (menuModel) {
            this.showEditMenuDialog(menuModel);
        },
        
        onCreateMenu: function () {
            this.showEditMenuDialog(new MenuModel());
        }
        
    });
    
    return MenuAdmin;
    
});

