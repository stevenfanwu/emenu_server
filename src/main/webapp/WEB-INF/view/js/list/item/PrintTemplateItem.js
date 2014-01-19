/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";


    var Tr = require('./Tr');
    var _ = require('../../lib/underscore');
    
    var PrintTemplateItem = Tr.extend({
        tmpl: require('./PrintTemplateItem.handlebars'),

        events: _.defaults({
            'click .btn-edit-print-template': 'onEditPrintTemplate',
            'click .btn-delete-print-template': 'onDeletePrintTemplate'
        }, Tr.prototype.events),

        
        /* -------------------- Event Listener ----------------------- */
        
        onEditPrintTemplate: function (evt) {
            evt.preventDefault();
            this.trigger('edit', this.model);
            evt.stopPropagation();
        },

        onDeletePrintTemplate: function (evt) {
            evt.preventDefault();
            this.trigger('delete', this.model);
            evt.stopPropagation();
        }
        
        
    });
    
    return PrintTemplateItem;
    
});

