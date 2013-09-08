/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Tr = require('./Tr');
    var _ = require('../../lib/underscore');
    var StringUtils = require('../../util/StringUtils');
    
    var StatItem = Tr.extend({
          
        tmpl: require('./StatItem.handlebars'),

        getRenderData: function () {
            var data = Tr.prototype.getRenderData.apply(this, arguments);
            data.time = StringUtils.formatDateDay((data.day * 24 + 8) * 3600 * 1000);
            data.tableRate = StringUtils.formatPercentage(data.tableRate);
            data.aveOrder = data.aveOrder.toMoney();
            data.avePerson = data.avePerson.toMoney();
            data.discount = data.discount.toMoney();
            return data;
        }
        
    });
    
    return StatItem;
    
});

