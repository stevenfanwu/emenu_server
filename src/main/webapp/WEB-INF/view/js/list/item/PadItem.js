/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Tr = require('./Tr');
    var PadItem = Tr.extend({
        tmpl: require('./PadItem.handlebars'),

        events: {
            'click .btn-edit-pad': 'onEditPad',
            'click .btn-delete-pad': 'onDeletePad'
        },

        /* -------------------- Event Listener ----------------------- */
        
        onEditPad: function (evt) {
            evt.preventDefault();
            this.model.trigger('edit', this.model);
            evt.stopPropagation();
        },

        onDeletePad: function (evt) {
            evt.preventDefault();
            this.model.trigger('delete', this.model);
            evt.stopPropagation();
        }
        
    });
    
    return PadItem;
    
});

