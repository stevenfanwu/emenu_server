/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');

    var Carousel = BaseItem.extend({
        className: 'item',

        activate: function () {
            this.$el.addClass('active');
            this.onActiveChanged(true);
        },

        isActive: function () {
            return this.$el.hasClass('active');
        },

        checkActive: function () {
            this.onActiveChanged(this.isActive());
        },
        
        /* -------------------- Event Listener ----------------------- */
        
        onActiveChanged: function (isActive) {
        }
        
    });
    
    return Carousel;
    
});

