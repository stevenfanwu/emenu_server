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
                }).done(function () {
                }.bind(this));
            }, this);
            dialog.show();
            evt.stopPropagation();
        },

        onRemoveDish: function (evt) {
            evt.preventDefault();
            evt.stopPropagation();
        }
        
        
    });
    
    return DishThumbnail;
    
});
