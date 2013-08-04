/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseList = require('./BaseList');

    var PadMonitorList = BaseList.extend({

        tagName: 'ul',

        className: 'thumbnails',
        
        CollectionType: require('../collection/PadCollection'),

        ItemType: require('./item/PadMonitorItem')
    });
    
    return PadMonitorList;
    
});

