/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var CollectionWithParentId = require('./CollectionWithParentId');

    var DishModel = require('../model/DishModel');

    var DishCollection = CollectionWithParentId.extend({
        baseUrl: '/api/dishes',

        parentKey: 'menuPageId',

        model: DishModel

    });
    
    return DishCollection;
    
});

