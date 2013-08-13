/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Tr = require('./Tr');
    
    var PrintComponentItem = Tr.extend({
        tmpl: require('./PrintComponentItem.handlebars')
        
    });
    
    return PrintComponentItem;
    
});

