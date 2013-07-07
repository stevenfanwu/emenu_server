/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');
    var Const = require('../../misc/Const');
    
    var DishItem = BaseItem.extend({
        tmpl: require('./DishItem.handlebars'),

        getRenderData: function () {
            var data = BaseItem.prototype.getRenderData.apply(this, arguments);
            data.typeLabel = Const.DishType.getLabel(data.type);
            data.unitLabel = Const.DishUnit.getLabel(data.unit);
            data.spicyLabel = Const.Spicy.getLabel(data.spicy);
            data.nonIntLabel = Const.Boolean.getLabel(data.nonInt);
            data.specialPriceLabel = Const.Boolean.getLabel(data.specialPrice);
            data.inMenu = Const.Boolean.getLabel(data.menuIds.length !== 0);
            return data;
        }
        
        
    });
    
    return DishItem;
    
});

