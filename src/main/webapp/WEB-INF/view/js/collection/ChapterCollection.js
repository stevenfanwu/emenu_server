/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";


    var CollectionWithParentId = require('./CollectionWithParentId');

    var ChapterModel = require('../model/ChapterModel');

    var ChapterCollection = CollectionWithParentId.extend({
        baseUrl: '/api/chapters',

        parentKey: 'menuId',

        model: ChapterModel
    });
    
    return ChapterCollection;
    
});

