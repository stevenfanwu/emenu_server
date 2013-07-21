/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var ListWithParentId = require('./ListWithParentId');

    var DishThumbnailList = ListWithParentId.extend({
        tagName: 'ul',

        className: 'thumbnails',

        CollectionType : require('../collection/DishCollection'),

        ItemType: require('./item/DishThumbnail'),

        initItem: function (model, item) {
            ListWithParentId.prototype.initItem.apply(this, arguments);
            item.menuPageId = this.collection.parentId;
        }
        
    });
    
    return DishThumbnailList;
    
});

