/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Tr = require('./Tr');
    var _ = require('../../lib/underscore');

    var DishTagItem = Tr.extend({
        tmpl: require('./DishTagItem.handlebars'),

        events: _.defaults({
            'click .btn-edit-dish-tag': 'onEditDishTag',
            'click .btn-delete-dish-tag': 'onDeleteDishTag'
        }, Tr.prototype.events),

        
        /* -------------------- Event Listener ----------------------- */
        
        onEditDishTag: function (evt) {
            evt.preventDefault();
            this.model.trigger('edit', this.model);
            evt.stopPropagation();
        },

        onDeleteDishTag: function (evt) {
            evt.preventDefault();
            this.model.trigger('delete', this.model);
            evt.stopPropagation();
        }
        
    });
    
    return DishTagItem;
    
});
