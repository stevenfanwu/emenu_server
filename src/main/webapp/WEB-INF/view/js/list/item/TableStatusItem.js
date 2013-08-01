/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');

    var TableStatusItem = BaseItem.extend({
        tmpl: require('./TableStatusItem.handlebars'),

        tagName: 'li',

        className: 'span2 thumbnail table-status-item-wrap',

        events: {
            'click .btn-bill': 'onBill'
        },

        getRenderData: function () {
            var data = BaseItem.prototype.getRenderData.apply(this, arguments);
            data.empty = data.status === 0;
            return data;
        },

        
        /* -------------------- Event Listener ----------------------- */
        
        onBill: function (evt) {
            if (this.model.get('status') === 0) {
                evt.preventDefault();
            }
        }
        
    });
    
    return TableStatusItem;
    
});

