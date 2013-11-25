/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Tr = require('./Tr');
    var _ = require('../../lib/underscore');

    var DishNoteItem = Tr.extend({
        tmpl: require('./DishNoteItem.handlebars'),

        events: _.defaults({
            'click .btn-edit-dish-note': 'onEditDishNote',
            'click .btn-delete-dish-note': 'onDeleteDishNote'
        }, Tr.prototype.events),

        
        /* -------------------- Event Listener ----------------------- */
        
        onEditDishNote: function (evt) {
            evt.preventDefault();
            this.model.trigger('edit', this.model);
            evt.stopPropagation();
        },

        onDeleteDishNote: function (evt) {
            evt.preventDefault();
            this.model.trigger('delete', this.model);
            evt.stopPropagation();
        }
        
    });
    
    return DishNoteItem;

});

