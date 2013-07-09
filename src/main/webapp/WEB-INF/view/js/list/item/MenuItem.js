/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";
    
    var BaseItem = require('./BaseItem');
    var $ = require('../../lib/jquery');

    var MenuItem = BaseItem.extend({
        events: {
            'mouseenter .hover-tip': 'onMouseEnter'
        },

        tmpl: require('./MenuItem.handlebars'),

        
        /* -------------------- Event Listener ----------------------- */
        
        onMouseEnter: function (evt) {
            evt.preventDefault();
            $(evt.currentTarget).tooltip('show');
            evt.stopPropagation();
        }
    });
    
    return MenuItem;
    
});

