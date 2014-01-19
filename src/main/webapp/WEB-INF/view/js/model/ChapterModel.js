/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";


    var BaseModel = require('./BaseModel');

    var ChapterModel = BaseModel.extend({
        urlRoot: '/api/chapters'
    });
    
    return ChapterModel;
    
});
