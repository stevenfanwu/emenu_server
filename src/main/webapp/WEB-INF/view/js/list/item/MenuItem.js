/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";
    
    var BaseItem = require('./BaseItem');
    var $ = require('../../lib/jquery');

    var MenuItem = BaseItem.extend({
        events: {
            'mouseenter .hover-tip': 'onMouseEnter',
            'click .btn-edit-menu': 'onEditMenu',
            'click .btn-delete-menu': 'onDeleteMenu'
        },

        tmpl: require('./MenuItem.handlebars'),

        
        /* -------------------- Event Listener ----------------------- */
        
        onMouseEnter: function (evt) {
            evt.preventDefault();
            $(evt.currentTarget).tooltip('show');
            evt.stopPropagation();
        },

        onDeleteMenu: function (evt) {
            evt.preventDefault();
            this.model.trigger('delete', this.model);
            evt.stopPropagation();
        },

        onEditMenu: function (evt) {
            evt.preventDefault();
            this.model.trigger('edit', this.model);
            evt.stopPropagation();
        }
    });
    
    return MenuItem;
    
});

