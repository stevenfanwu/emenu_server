/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Tr = require('../list/item/Tr');
    
    
    var MenuStatSum = Tr.extend({
        tmpl: require('./MenuStatSum.handlebars'),
        
        getRenderData: function () {
            return this.options.data;
        }
        
    });
    
    return MenuStatSum;
});

