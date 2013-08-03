/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseView = require('../BaseView');
    var MenuPageCollection = require('../collection/MenuPageCollection');
    var $ = require('../lib/jquery');

    var MenuPage = BaseView.extend({
        tmpl: require('./MenuPage.handlebars'),

        events: {
            'click .page-item': 'onPageClick'
        },

        collection: new MenuPageCollection(),

        currentIndex: 1,

        render: function () {
            this.collection.parentId = this.options.parentId;
            this.collection.fetch({
                success: function () {
                    this.doRender();
                }.bind(this)
            });
        },

        doRender: function () {
            if (this.options.menuPageId) {
                this.collection.some(function (model, index) {
                    if (model.get('id') === this.options.menuPageId) {
                        this.currentIndex = index + 1;
                        return true;
                    }
                }, this);
                delete this.options.menuPageId;
            }
            this.showPage();
        },

        showPage: function (index) {
            if (index) {
                this.currentIndex = index;
            }
            BaseView.prototype.render.apply(this, arguments);
            var List = require('../list/DishThumbnailList');
            this.list = new List({
                parentId: this.collection.at(this.currentIndex - 1).get('id')
            });
            this.list.render();
            this.$('.page-content').html(this.list.el);
        },

        getRenderData: function () {
            var data = BaseView.prototype.getRenderData.apply(this, arguments);
            data.pages = this.collection.toJSON();
            data.pages.forEach(function (page, index) {
                page.index = index + 1;
                page.active = page.index === this.currentIndex;
            }, this);
            data.maxIndex = data.pages.length;
            data.lastIndex = this.currentIndex - 1;
            data.nextIndex = this.currentIndex + 1;
            data.hasLast = data.lastIndex > 0;
            data.hasNext = data.nextIndex <= data.maxIndex;
            return data;
        },

        
        /* -------------------- Event Listener ----------------------- */
        
        onPageClick: function (evt) {
            evt.preventDefault();
            var index = parseInt($(evt.target).attr('value'), 10);
            if (index !== this.currentIndex && index >= 1 && index <= this.collection.length) {
                this.showPage(index);
            }
            evt.stopPropagation();
        }
        
    });
    
    return MenuPage;
    
});

