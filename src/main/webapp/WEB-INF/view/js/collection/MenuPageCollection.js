/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var CollectionWithParentId = require('./CollectionWithParentId');

    var MenuPageModel = require('../model/MenuPageModel');

    var MenuPageCollection = CollectionWithParentId.extend({
        baseUrl: '/api/pages',

        parentKey: 'chapterId',

        model: MenuPageModel
    });
    
    return MenuPageCollection;

});

