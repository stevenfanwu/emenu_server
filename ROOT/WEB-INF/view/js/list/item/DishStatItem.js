/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";


    var Tr = require('./Tr');
    var _ = require('../../lib/underscore');
    var StringUtils = require('../../util/StringUtils');
    
    var DishStatItem = Tr.extend({
          
        tmpl: require('./DishStatItem.handlebars'),

        getRenderData: function () {
            var data = Tr.prototype.getRenderData.apply(this, arguments);
            data.income = data.income ? data.income.toMoney() : 0;
            data.price = data.price ? data.price.toMoney() : 0;
            data.discount = data.discount ? data.discount.toMoney() : 0;
            return data;
        }
        
    });
    
    return DishStatItem;
    
});
