/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseList = require('./BaseList');

    var Carousel = BaseList.extend({
        tmpl: require('./Carousel.handlebars'),

        events: {
            'slide .carousel': 'onSlideStart',
            'slid .carousel': 'onSlideFinish'
        },

        appendItem: function (item) {
            this.$('.carousel-inner').append(item.el);
            if (this.$('.carousel-inner .item').length === 1) {
                item.activate();
            }
            this.items.push(item);
        },

        getRenderData: function () {
            var data = BaseList.prototype.getRenderData.apply(this, arguments);
            data.carouselId = this.getCarouselId();
            return data;
        },

        getCarouselId: function () {
            return "myCarousel";
        },
        
        /* -------------------- Event Listener ----------------------- */
        
        onSlideStart: function () {
        },
        
        onSlideFinish: function () {
            this.items.forEach(function (item) {
                item.checkActive();
            }, this);
        }
    });
    
    return Carousel;
    
});

