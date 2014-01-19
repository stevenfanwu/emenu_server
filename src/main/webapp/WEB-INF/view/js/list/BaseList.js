/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseView = require('../BaseView');

    var BaseList = BaseView.extend({
        CollectionType: null,

        collection: null,

        ItemType: null,

        fetched: false,

        items: [],

        initialize: function () {
            BaseView.prototype.initialize.apply(this, arguments);

            if (!this.collection) {
                var Collection = this.CollectionType;
                this.collection = new Collection();
            }
        },

        appendModel: function (model) {
            var Item = this.ItemType;
            var item = new Item({
                model: model
            });

            item.index = this.collection.indexOf(model);
            item.isFirst = (item.index === 0);
            item.isLast = (item.index === this.collection.length - 1);

            item.render();
            this.initItem(model, item);
            this.appendItem(item);
            this.items.push(item);
        },

        filterModel: function (model) {
            return true;
        },

        initItem: function (model, item) {
            item.on('refreshList', function () {
                this.refresh();
            }, this);
        },

        destroy: function () {
            BaseView.prototype.destroy.apply(this, arguments);
            this.resetContent();
        },

        resetContent: function () {
            this.collection.forEach(function (model) {
                model.off();
            }, this);
            this.collection.reset();
            this.fetched = false;
            this.items.forEach(function (item) {
                item.destroy();
            }, this);
            BaseView.prototype.resetContent.apply(this, arguments);
        },

        refresh: function () {
            this.resetContent();
        },

        appendItem: function (item) {
            this.$el.append(item.el);
        },

        doRender: function () {
            BaseView.prototype.render.apply(this, arguments);
            this.items = [];
            this.preProcessCollection(this.collection);
            this.collection.forEach(function (model) {
                if (this.filterModel(model)) {
                    this.appendModel(model);
                }
            }, this);
        },
        
        render: function () {
            if (!this.fetched) {
                this.collection.fetch({
                    success: function () {
                        this.trigger('fetched', this.collection);
                        this.doRender();
                    }.bind(this)
                });
                this.fetched = true;
                return;
            }
            this.doRender();
        },

        preProcessCollection: function (collection) {
        }
        
    });
    
    return BaseList;
    
});

