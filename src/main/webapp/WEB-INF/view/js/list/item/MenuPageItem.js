/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var CarouselItem = require('./CarouselItem');
    var DishThumbnailList = require('../DishThumbnailList');
    
    var MenuPageItem = CarouselItem.extend({
        needRender: true,

        render: function () {
            if (this.isActive()) {
                if (!this.list) {
                    this.list = new DishThumbnailList({
                        parentId: this.model.get('id')
                    });
                    this.list.render();
                    this.$el.html(this.list.el);
                }
            }
        },

        onActiveChanged: function (isActive) {
            if (isActive && this.needRender) {
                this.render();
                this.needRender = false;
            }
        }
        
    });
    
    return MenuPageItem;
    
});
