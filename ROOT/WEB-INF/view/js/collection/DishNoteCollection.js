/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseCollection = require('./BaseCollection');
    var DishNoteModel = require('../model/DishNoteModel');

    var DishNoteCollection = BaseCollection.extend({
        url: '/api/dish/notes',

        model: DishNoteModel
    });
    
    return DishNoteCollection;

});

