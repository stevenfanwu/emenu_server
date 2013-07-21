/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var ListWithParentId = require('./ListWithParentId');

    var DishThumbnailList = ListWithParentId.extend({
        tagName: 'ul',

        className: 'thumbnails',

        CollectionType : require('../collection/DishCollection'),

        ItemType: require('./item/DishThumbnail')
    });
    
    return DishThumbnailList;
    
});

