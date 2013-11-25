/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Tr = require('../list/item/Tr');
    
    var DishStatSum = Tr.extend({
        tmpl: require('./DishStatSum.handlebars'),
        
        getRenderData: function () {
            return this.options.data;
        }
        
    });
    
    return DishStatSum;
    
});

