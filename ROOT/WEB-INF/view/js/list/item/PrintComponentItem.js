/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Tr = require('./Tr');
    var _ = require('../../lib/underscore');
    
    var PrintComponentItem = Tr.extend({
        tmpl: require('./PrintComponentItem.handlebars'),

        events: _.defaults({
            'click .btn-edit-print-component': 'onEditPrintComponent',
            'click .btn-delete-print-component': 'onDeletePrintComponent'
        }, Tr.prototype.events),

        
        /* -------------------- Event Listener ----------------------- */
        
        onEditPrintComponent: function (evt) {
            evt.preventDefault();
            this.trigger('edit', this.model);
            evt.stopPropagation();
        },

        onDeletePrintComponent: function (evt) {
            evt.preventDefault();
            this.trigger('delete', this.model);
            evt.stopPropagation();
        }
        
        
    });
    
    return PrintComponentItem;
    
});

