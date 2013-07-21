/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseCollection = require('./BaseCollection');

    var BaseCollectionWithParentId = BaseCollection.extend({
        parentId: null,

        parentKey: null,

        baseUrl: null,

        url: function () {
            if (this.parentId) {
                return this.baseUrl + "?" + this.parentKey + "=" + this.parentId;
            }
            return this.baseUrl;
        }
        
    });
    
    return BaseCollectionWithParentId;
    
});

