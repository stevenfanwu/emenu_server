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

        //TODO move to parent
        hasQuery: false,

        url: function () {
            var  url = this.baseUrl;
            if (this.parentId) {
                url = url + (this.hasQuery ? "&" : "?") + this.parentKey + "=" + this.parentId;
                this.hasQuery = true;
            }
            //TODO move to parent
            if (this.page) {
                url = url + (this.hasQuery ? "&" : "?") + "page=" + this.page;
            }
            return url;
        }
        
    });
    
    return BaseCollectionWithParentId;
    
});

