/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Select = require('./Select');

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
        },

        initValue: null,

        getValue: function () {
            if (this.wrapId) {
                var name = Select.prototype.getValue.apply(this, arguments);
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
            return Select.prototype.getValue.apply(this, arguments);
        },

        setValue: function (value) {
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
                this.initValue = name;
            } else {
                this.initValue = value;
            }
        },
        
        init: function () {
            Select.prototype.init.apply(this, arguments);
            if (!this.fetched) {
                if (!this.collection) {
                    var Collection = this.CollectionType;
                    this.collection = new Collection();
                }
                this.collection.fetch({
                    success: function () {
                        this.fetched = true;
                        this.$(this.valueEl).html(this.template(this.collection.toJSON()));
                        if (this.initValue) {
                            Select.prototype.setValue.call(this, this.initValue);
                        }
                    }.bind(this)
                });
            }
        }
        
        
    });
    
    return AjaxSelect;
    
});

