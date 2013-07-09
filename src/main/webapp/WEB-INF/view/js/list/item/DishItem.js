/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Tr = require('./Tr');
    var Const = require('../../misc/Const');
    
    var DishItem = Tr.extend({
        tmpl: require('./DishItem.handlebars'),

        events: {
            'click .btn-edit-dish': 'onEditDish',
            'click .btn-delete-dish': 'onDeleteDish'
        },

        getRenderData: function () {
            var data = Tr.prototype.getRenderData.apply(this, arguments);
            data.typeLabel = Const.DishType.getLabel(data.type);
            data.unitLabel = Const.DishUnit.getLabel(data.unit);
            data.spicyLabel = Const.Spicy.getLabel(data.spicy);
            data.nonIntLabel = Const.Boolean.getLabel(data.nonInt);
            data.specialPriceLabel = Const.Boolean.getLabel(data.specialPrice);
            data.inMenu = Const.Boolean.getLabel(data.menuIds.length !== 0);
            return data;
        },

        
        /* -------------------- Event Listener ----------------------- */
        
        onDeleteDish: function (evt) {
            evt.preventDefault();
            this.model.trigger('delete', this.model);
            evt.stopPropagation();
        },
        
        onEditDish: function (evt) {
            evt.preventDefault();
            this.model.trigger('edit', this.model);
            evt.stopPropagation();
        }
        
        
    });
    
    return DishItem;
    
});

