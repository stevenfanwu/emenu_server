/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseList = require('./BaseList');

    var ListWithParentId = BaseList.extend({
        initialize: function () {
            BaseList.prototype.initialize.apply(this, arguments);
            this.collection.parentId = this.options.parentId;
        }
        
    });

    return ListWithParentId;

});

