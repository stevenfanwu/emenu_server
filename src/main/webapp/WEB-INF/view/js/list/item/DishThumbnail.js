/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');
    var SelectDishDialog = require('../../dialog/SelectDishDialog');
    var $ = require('../../lib/jquery');
    var DishModel = require('../../model/DishModel');
    var EditDishDialog = require('../../dialog/EditDishDialog');
    var MenuUtils = require('../../util/MenuUtils');

    var DishThumbnail = BaseItem.extend({
        tmpl: require('./DishThumbnail.handlebars'),

        events: {
            'click .btn-img': 'onImgClick',
            'click .btn-edit-dish': 'onEditDish',
            'click .btn-select-dish': 'onSelectDish',
            'click .btn-remove-dish': 'onRemoveDish'
        },

        menuPageId: null,

        getRenderData: function () {
            var data = BaseItem.prototype.getRenderData.apply(this, arguments);

            data.hasValue = data.id > 0;

            return data;
        },

        editDish: function () {
            var dialog = new EditDishDialog({
                model: this.model
            });
            dialog.model.on('saved', function () {
                this.resetContent();
            }, this);
            dialog.show();
        },
        
        addDish: function () {
            var dialog = new EditDishDialog({
                model: new DishModel()
            });
            dialog.model.on('saved', function () {
                this.model = dialog.model;
                this.resetContent();
                this.bindDish(this.model.get('id'));
            }, this);
            dialog.show();
        },

        bindDish: function (dishId) {
            var options = {};
            options.data = {
                dishId: dishId,
                menuPageId: this.menuPageId,
                pos: this.index
            };
            options.success = function (dish) {
                this.model.set(dish);
                this.resetContent();
            }.bind(this);
            MenuUtils.bindDish(options);
        },

        /* -------------------- Event Listener ----------------------- */
        
        onImgClick: function (evt) {
            evt.preventDefault();
            if (this.model.get('id') > 0) {
                this.editDish();
            } else {
                this.addDish();
            }
            evt.stopPropagation();
        },

        onEditDish: function (evt) {
            evt.preventDefault();
            this.editDish();
            evt.stopPropagation();
        },

        onSelectDish: function (evt) {
            evt.preventDefault();
            var dialog = new SelectDishDialog();
            dialog.on('submit', function (idName) {
                this.bindDish(idName.id);
            }, this);
            dialog.show();
            evt.stopPropagation();
        },

        onRemoveDish: function (evt) {
            evt.preventDefault();
            if (window.confirm('确定移除菜品"' + this.model.get('name') + '"?')) {
                var options = {};
                options.data = {
                    dishId: this.model.get('id'),
                    menuPageId: this.menuPageId,
                    pos: this.index
                };
                options.success = function (dish) {
                    this.model.set(dish);
                    this.resetContent();
                }.bind(this);
                MenuUtils.unbindDish(options);
            }
            evt.stopPropagation();
        }
        
        
    });
    
    return DishThumbnail;
    
});
