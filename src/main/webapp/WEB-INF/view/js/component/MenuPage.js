/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseView = require('../BaseView');
    var MenuPageCollection = require('../collection/MenuPageCollection');
    var MenuPageModel = require('../model/MenuPageModel');
    var $ = require('../lib/jquery');

    var MenuPage = BaseView.extend({
        tmpl: require('./MenuPage.handlebars'),

        events: {
            'click .page-item': 'onPageClick',
            'click .btn-go': 'onPageGo',
            'click .btn-edit-page': 'onEditPage',
            'click .btn-delete-page': 'onDeletePage'
        },

        collection: null,

        currentIndex: 1,

        getCurrentPageModel: function () {
            return this.collection.at(this.currentIndex - 1);
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
            data.maxIndex = this.collection.length;
            data.lastIndex = this.currentIndex - 1;
            data.nextIndex = this.currentIndex + 1;
            data.hasLast = data.lastIndex > 0;
            data.hasNext = data.nextIndex <= data.maxIndex;
            data.currentIndex = this.currentIndex;

            var pagers = [];
            var i = 1;
            var walk = function (end) {
                while (i <= data.maxIndex && i <= end) {
                    pagers.push({
                        isActive: i === this.currentIndex,
                        isNormal: i !== this.currentIndex,
                        index: i
                    });
                    i = i + 1;
                }
            }.bind(this);
            walk(3);
            if (this.currentIndex - 1 > i) {
                pagers.push({
                    isDot: true
                });
                i = this.currentIndex - 1;
            }
            walk(this.currentIndex + 1);
            if (i < data.maxIndex - 2) {
                pagers.push({
                    isDot: true
                });
                i = data.maxIndex - 2;
            }
            walk(data.maxIndex);

            data.pagers = pagers;
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
        },

        onPageGo: function (evt) {
            evt.preventDefault();
            var value = this.$('.input-page').val();
            var index = parseInt(value, 10);
            if (String(index) === String(value)) {
                this.showPage(index);
            }
            evt.stopPropagation();
        },

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

