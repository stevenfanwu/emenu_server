/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseCollection = require('./BaseCollection');
    var DishTagModel = require('../model/DishTagModel');

    var DishTagCollection = BaseCollection.extend({
        url: '/api/dish/tags',

        model: DishTagModel
    });
    
    return DishTagCollection;
});

