/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";


    var Carousel = require('./Carousel');

    var MenuPageList = Carousel.extend({

        initialize: function () {
            Carousel.prototype.initialize.apply(this, arguments);
            this.collection.parentId = this.options.parentId;
        },

        CollectionType: require('../collection/MenuPageCollection'),

        getCarouselId: function () {
            return Carousel.prototype.getCarouselId.apply(this, arguments)
                 + "-" + this.collection.parentId;
        },
        
        ItemType: require('./item/MenuPageItem')
        
    });
    
    return MenuPageList;
});

