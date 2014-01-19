/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseList = require('./BaseList');

    var MenuList = BaseList.extend({
        className: 'accordion color-list',

        CollectionType: require('../collection/MenuCollection'),

        ItemType: require('./item/MenuItem')
        
    });
    
    return MenuList;
    
});

