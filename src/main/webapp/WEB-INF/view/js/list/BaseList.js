/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseView = require('../BaseView');

    var BaseList = BaseView.extend({

        tmpl: require('./BaseList.handlebars'),

        heads: null,

        collection: null,

        CollectionType: null,

        ItemType: null,

        fetched: false,

        initialize: function () {
            BaseView.prototype.initialize.apply(this, arguments);

            var Collection = this.CollectionType;
            this.collection = new Collection();
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

        initItem: function (model, item) {
        },

        appendItem: function (item) {
            this.$('tbody').append(item.el);
        },

        doRender: function () {
            this.el.innerHTML = this.template();
            this.heads.forEach(function (head) {
                this.$('.head-row').append('<th>' + head + '</th>');
            }, this);
            this.collection.forEach(function (model) {
                if (this.filterModel(model)) {
                    this.appendModel(model);
                }
            }, this);
        },

        filterModel: function (model) {
            return true;
        },

        resetContent: function () {
            BaseView.prototype.resetContent.apply(this, arguments);

            this.collection.reset();
            this.render();
        },

        refresh: function () {
            this.fetched = false;
            this.resetContent();
        }
        
    });
    
    return BaseList;
    
});

