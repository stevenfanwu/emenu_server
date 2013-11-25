/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var TabPage = require('./TabPage');

    var TabList = TabPage.extend({

        ListType: null,

        initialize: function () {
            var List = this.ListType;
            this.list = new List({
                el: this.$('.wrap-list')
            });
            TabPage.prototype.initialize.apply(this, arguments);
        },

        render: function () {
            this.list.render();
        }
    });
    
    return TabList;
    
});

