/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseList = require('./BaseList');

    var Carousel = BaseList.extend({
        className: 'carousel-wrap',

        tmpl: require('./Carousel.handlebars'),

        events: {
            'slide .carousel': 'onSlideStart',
            'slid .carousel': 'onSlideFinish',
            'click .btn-previous': 'onPreviousClick',
            'click .btn-next': 'onNextClick'
        },

        doRender: function () {
            BaseList.prototype.doRender.apply(this, arguments);
            this.$('.carousel').carousel({
                interval: false
            });
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
        },

        onPreviousClick: function (evt) {
            evt.preventDefault();
            this.$('.carousel').carousel('prev');
            evt.stopPropagation();
        },

        onNextClick: function (evt) {
            evt.preventDefault();
            this.$('.carousel').carousel('next');
            evt.stopPropagation();
        }
        
    });
    
    return Carousel;
    
});

