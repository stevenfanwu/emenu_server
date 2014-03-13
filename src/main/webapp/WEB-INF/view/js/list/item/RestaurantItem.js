/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Tr = require('./Tr');

    var RestaurantItem = Tr.extend({
        tmpl: require('./RestaurantItem.handlebars'),

        events: {
          'click .btn-edit-restaurant': 'onEditRestaurant',
          'click .btn-delete-restaurant': 'onDeleteRestaurant', 
        },

        getRenderData: function() {
          return Tr.prototype.getRenderData.apply(this, arguments);
        },

        /* -------------------- Event Listener ----------------------- */

        onEditRestaurant: function(evt) {
            evt.preventDefault();
            this.model.trigger('edit', this.model);
            evt.stopPropagation();
        },

        onDeleteRestaurant: function(evt) {
            evt.preventDefault();
            this.model.trigger('delete', this.model);
            evt.stopPropagation();
        },
    });

    return RestaurantItem;
});

