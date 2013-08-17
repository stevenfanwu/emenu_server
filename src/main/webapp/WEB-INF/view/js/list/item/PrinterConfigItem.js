/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";


    var Tr = require('./Tr');
    var _ = require('../../lib/underscore');
    
    var PrinterConfigItem = Tr.extend({
        tmpl: require('./PrinterConfigItem.handlebars'),

        events: _.defaults({
            'click .btn-edit-printer-config': 'onEditPrinterConfig'
        }, Tr.prototype.events),

        
        /* -------------------- Event Listener ----------------------- */
        
        onEditPrinterConfig: function (evt) {
            evt.preventDefault();
            this.trigger('edit', this.model);
            evt.stopPropagation();
        }
    });
    
    return PrinterConfigItem;
    
});

