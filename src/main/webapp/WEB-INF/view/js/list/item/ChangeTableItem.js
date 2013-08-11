/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');

    var ChangeTableItem = BaseItem.extend({
        events: {
            'click .btn-select': 'onSelect'
        },

        tagName: 'li',

        className: 'thumbnail table-thumbnail span2',

        tmpl: require('./ChangeTableItem.handlebars'),

        getRenderData: function () {
            var data = BaseItem.prototype.getRenderData.apply(this, arguments);
            data.empty = !this.model.get('status');
            return data;
        },

        
        /* -------------------- Event Listener ----------------------- */
        
        onSelect: function (evt) {
            evt.preventDefault();
            if (this.model.get('status') === 0) {
                this.trigger('select', this.model);
            }
            evt.stopPropagation();
        }
        
    });
    
    return ChangeTableItem;
    
});

