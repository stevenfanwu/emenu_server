/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');
    var SelectDishDialog = require('../../dialog/SelectDishDialog');
    var $ = require('../../lib/jquery');

    var DishThumbnail = BaseItem.extend({
        tmpl: require('./DishThumbnail.handlebars'),

        events: {
            'click .btn-img': 'onImgClick',
            'click .btn-select-dish': 'onSelectDish',
            'click .btn-remove-dish': 'onRemoveDish'
        },

        menuPageId: null,

        getRenderData: function () {
            var data = BaseItem.prototype.getRenderData.apply(this, arguments);

            data.hasValue = data.id > 0;

            return data;
        },
        

        /* -------------------- Event Listener ----------------------- */
        
        onImgClick: function (evt) {
            evt.preventDefault();
            evt.stopPropagation();
        },

        onSelectDish: function (evt) {
            evt.preventDefault();
            var dialog = new SelectDishDialog();
            dialog.on('submit', function (idName) {
                var data = {
                    dishId: idName.id,
                    menuPageId: this.menuPageId,
                    pos: this.index
                };
                $.ajax({
                    url: '/api/menus/bind',
                    type: 'PUT',
                    data: data
                }).done(function (dish) {
                    this.model.set(dish);
                    this.resetContent();
                }.bind(this));
            }, this);
            dialog.show();
            evt.stopPropagation();
        },

        onRemoveDish: function (evt) {
            evt.preventDefault();
            if (window.confirm('确定移除菜品"' + this.model.get('name') + '"?')) {
                var data = {
                    dishId: this.model.get('id'),
                    menuPageId: this.menuPageId,
                    pos: this.index
                };
                $.ajax({
                    url: '/api/menus/unbind',
                    type: 'PUT',
                    data: data
                }).done(function (dish) {
                    this.model.set(dish);
                    this.resetContent();
                }.bind(this));
            }
            evt.stopPropagation();
        }
        
        
    });
    
    return DishThumbnail;
    
});
