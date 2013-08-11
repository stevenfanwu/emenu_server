/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BasePage = require('./BasePage');
    var SoldoutList = require('../list/SoldoutList');
    var SearchQuery = require('../component/SearchQuery');
    var MenuUtils = require('../util/MenuUtils');

    var Soldout = BasePage.extend({
        RouterType: require('../router/SoldoutRouter'),

        events: {
            'click .btn-unsoldout-all': 'onUnsoldoutAll'
        },
        
        render: function () {
            this.list = new SoldoutList();
            this.list.render();
            this.$('.dish-list-wrap').html(this.list.el);
            if (!this.searchQuery) {
                this.searchQuery = new SearchQuery({
                    el: this.$('.wrap-search-query')
                });
                this.searchQuery.on('query', this.list.onQuery, this.list);
            }
        },

        
        /* -------------------- Event Listener ----------------------- */
        
        onUnsoldoutAll: function (evt) {
            evt.preventDefault();
            MenuUtils.unsoldoutAllDish({
                success: function () {
                    this.list.trigger('clearSoldout');
                }.bind(this)
            });
            evt.stopPropagation();
        }
        
    });
    
    return Soldout;
    
});

