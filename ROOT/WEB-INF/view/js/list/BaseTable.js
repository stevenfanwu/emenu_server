/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseList = require('./BaseList');

    var BaseTable = BaseList.extend({

        tmpl: require('./BaseTable.handlebars'),

        heads: null,

        appendItem: function (item) {
            this.$('tbody').append(item.el);
        },

        doRender: function () {
            BaseList.prototype.doRender.apply(this, arguments);
            this.heads.forEach(function (head) {
                this.$('.head-row').append('<th>' + head + '</th>');
            }, this);
        }
        
    });
    
    return BaseTable;
    
});

