/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseCollection = require('./BaseCollection');

    var BaseCollectionWithParentId = BaseCollection.extend({
        parentId: null,

        parentKey: null,

        baseUrl: null,

        page: null,

        url: function () {
            var hasQuery = false;
            var  url = this.baseUrl;
            if (this.parentId) {
                url = url + (hasQuery ? "&" : "?") + this.parentKey + "=" + this.parentId;
                hasQuery = true;
            }
            //TODO move to parent
            if (this.page) {
                url = url + (hasQuery ? "&" : "?") + "page=" + this.page;
                hasQuery = true;
            }
            return url;
        }
        
    });
    
    return BaseCollectionWithParentId;
    
});

