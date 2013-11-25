/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseView = require('../BaseView');
    var MenuPageCollection = require('../collection/MenuPageCollection');
    var MenuPageModel = require('../model/MenuPageModel');
    var $ = require('../lib/jquery');
    var Pager = require('./widget/Pager');

    var MenuPage = BaseView.extend({
        tmpl: require('./MenuPage.handlebars'),

        events: {
            'click .btn-edit-page': 'onEditPage',
            'click .btn-delete-page': 'onDeletePage'
        },

        collection: null,

        pager: null,

        getCurrentPageModel: function () {
            return this.collection.at(this.pager.currentIndex - 1);
        },

        render: function () {
            if (!this.collection) {
                this.collection = new MenuPageCollection();
            }
            this.collection.reset();
            this.collection.parentId = this.options.parentId;
            this.collection.fetch({
                success: function () {
                    this.doRender();
                }.bind(this)
            });
        },

        doRender: function () {
            BaseView.prototype.render.apply(this, arguments);
            if (!this.pager) {
                this.pager = new Pager();
                this.pager.on('pager', function (index) {
                    this.showPage(index);
                }, this);
            }
            this.pager.collection = this.collection;
            if (this.options.menuPageId) {
                this.collection.some(function (model, index) {
                    if (model.get('id') === this.options.menuPageId) {
                        this.pager.currentIndex = index + 1;
                        return true;
                    }
                }, this);
                delete this.options.menuPageId;
            }
            this.pager.render();
            this.$el.prepend(this.pager.el);
            this.showPage();
        },

        showPage: function (index) {
            if (index) {
                this.pager.currentIndex = index;
            }
            this.pager.render();
            if (this.collection.length >= this.pager.currentIndex) {
                var List = require('../list/DishThumbnailList');
                this.list = new List({
                    parentId: this.collection.at(this.pager.currentIndex - 1).get('id')
                });
                this.list.render();
                this.$('.page-content').html(this.list.el);
            } else {
                this.$('.page-content').empty();
            }
        },

        /* -------------------- Event Listener ----------------------- */

        onEditPage: function (evt) {
            evt.preventDefault();
            this.trigger('editPage', this.getCurrentPageModel());
            evt.stopPropagation();
        },

        onDeletePage: function (evt) {
            evt.preventDefault();
            this.trigger('deletePage', this.getCurrentPageModel());
            evt.stopPropagation();
        }
        
    });
    
    return MenuPage;
    
});

