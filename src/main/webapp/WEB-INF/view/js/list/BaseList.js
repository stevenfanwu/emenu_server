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
            item.render();
            this.initItem(model, item);
            this.appendItem(item);
        },

        filterModel: function (model) {
            return true;
        },

        initItem: function (model, item) {
        },

        resetContent: function () {
            this.collection.reset();
            BaseView.prototype.resetContent.apply(this, arguments);
        },

        refresh: function () {
            this.fetched = false;
            this.resetContent();
        },

        appendItem: function (item) {
            this.$el.append(item.el);
        },

        doRender: function () {
            BaseView.prototype.render.apply(this, arguments);
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
                        this.doRender();
                    }.bind(this)
                });
                this.fetched = true;
                return;
            }
            this.doRender();
        }
        
    });
    
    return BaseList;
    
});

