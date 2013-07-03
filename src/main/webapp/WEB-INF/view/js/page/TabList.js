/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BasePage = require('./BasePage');

    var TabList = BasePage.extend({
        tabEl: [],

        ListType: null,

        activeTab: function (tab) {
            this.tabEl.forEach(function (t) {
                if (t === tab) {
                    this.$(t).addClass('active');
                } else {
                    this.$(t).removeClass('active');
                }
            }, this);
        },

        initialize: function () {
            var List = this.ListType;
            this.list = new List({
                el: this.$('.wrap-list')
            });
            BasePage.prototype.initialize.apply(this, arguments);
        },

        render: function () {
            this.list.render();
        }
    });
    
    return TabList;
    
});

