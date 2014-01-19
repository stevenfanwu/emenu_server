/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var ListWithParentId = require('./ListWithParentId');

    var ChapterList = ListWithParentId.extend({
        className: 'accordion color-list',

        CollectionType: require('../collection/ChapterCollection'),

        ItemType: require('./item/ChapterItem')
        
    });
    
    return ChapterList;
});

