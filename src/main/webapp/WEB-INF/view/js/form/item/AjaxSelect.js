/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Select = require('./Select');
    var Required = require('../validator/Required');

    var AjaxSelect = Select.extend({
        optionList: [],

        fetched: false,

        collection: null,

        CollectionType: null,

        wrapId: false,

        tmpl: require('./Select.handlebars'),

        parseConfig: function (config) {
            Select.prototype.parseConfig.apply(this, arguments);
            this.CollectionType = config.CollectionType;
            this.wrapId = config.wrapId === true;
        },

        getValue: function () {
            if (!this.hasValidator(Required) && this.$('select')[0].selectedIndex === 0) {
                return null;
            }
            if (this.wrapId) {
                var name = this.getLabel();
                var id = null;
                this.collection.toJSON().some(function (item) {
                    if (item.name === name) {
                        id = item.id;
                        return true;
                    }
                    return false;
                }, this);
                return id;
            }
            return this.getLabel();
        },

        getLabel: function () {
            return Select.prototype.getValue.apply(this, arguments);
        },

        setValue: function (value) {
            if (value === null) {
                return;
            }
            if (this.wrapId) {
                var id = value;
                var name = null;
                this.collection.toJSON().some(function (item) {
                    if (item.id === id) {
                        name = item.name;
                        return true;
                    }
                    return false;
                }, this);
                value = name;
            }
            Select.prototype.setValue.call(this, value);
        },

        getOptions: function () {
            var data = this.collection.toJSON();
            if (!this.hasValidator(Required)) {
                if (this.wrapId) {
                    data.unshift({
                        name: '无',
                        id: 0
                    });
                } else {
                    data.unshift('无');
                }
            }
            return data;
        },
        
        init: function () {
            if (!this.fetched) {
                if (!this.collection) {
                    var Collection = this.CollectionType;
                    this.collection = new Collection();
                }
                this.collection.fetch({
                    success: function () {
                        this.fetched = true;
                        this.$(this.valueEl).html(this.template(this.getOptions()));
                        Select.prototype.init.apply(this, arguments);
                    }.bind(this)
                });
            }
        }
        
        
    });
    
    return AjaxSelect;
    
});

