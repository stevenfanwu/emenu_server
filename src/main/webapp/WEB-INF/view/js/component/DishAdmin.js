/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseView = require('../BaseView');
    var DishModel = require('../model/DishModel');

    var DishAdmin = BaseView.extend({
        tmpl: require('./DishAdmin.handlebars'),

        events: {
            'click .btn-add-dish': 'onAddDish'
        },

        initialize: function () {
            BaseView.prototype.initialize.apply(this, arguments);
        },
        
        render: function () {
            BaseView.prototype.render.apply(this, arguments);
            if (!this.list) {
                var List = require('../list/DishList');
                this.list = new List();
                this.list.collection.on('edit', this.onEditDish.bind(this));
                this.list.collection.on('delete', this.onDeleteDish.bind(this));
            }
            this.list.render();
            this.$('.wrap-list').empty();
            this.$('.wrap-list').append(this.list.el);
        },
        
        /* -------------------- Event Listener ----------------------- */

        onDeleteDish: function (model) {
            if (window.confirm('确定删除菜品' + model.get('name') + '?')) {
                model.destroy({
                    success: function () {
                        this.list.refresh();
                    }.bind(this)
                });
            }
        },

        onEditDish: function (model) {
            var Dialog = require('../dialog/EditDishDialog');
            var dialog = new Dialog({
                model: model
            });
            dialog.model.on('saved', function () {
                this.list.refresh();
            }, this);
            dialog.show();
        },
        
        onAddDish: function (evt) {
            evt.preventDefault();
            var Dialog = require('../dialog/EditDishDialog');
            var dialog = new Dialog({
                model: new DishModel()
            });
            dialog.model.on('saved', function () {
                this.list.refresh();
            }, this);
            dialog.show();
            evt.stopPropagation();
        }
    });
    
    return DishAdmin;
    
});
