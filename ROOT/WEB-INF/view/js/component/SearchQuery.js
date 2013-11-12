/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseView = require('../BaseView');

    var SearchQuery = BaseView.extend({
        events: {
            'keyup .search-query': 'onQuery'
        },

        initialize: function () {
            BaseView.prototype.initialize.apply(this, arguments);
        
        },
        
        /* -------------------- Event Listener ----------------------- */
        
        onQuery: function (evt) {
            evt.preventDefault();
            var query = this.$('.search-query').val();
            this.trigger('query', query);
            evt.stopPropagation();
        }
    });
    
    return SearchQuery;
    
});

