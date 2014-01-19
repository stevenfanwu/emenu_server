/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var CheckboxGroup = require('./CheckBoxGroup');

    var AjaxCheckboxGroup = CheckboxGroup.extend({

        fetched: false,

        collection: null,

        CollectionType: null,

        tmpl: require('./AjaxCheckboxGroup.handlebars'),

        parseConfig: function (config) {
            CheckboxGroup.prototype.parseConfig.apply(this, arguments);
            this.CollectionType = config.CollectionType;
        },
        
        init: function () {
            CheckboxGroup.prototype.init.apply(this, arguments);
            if (!this.fetched) {
                this.fetch({
                    success: function () {
                        this.render();
                        this.fetched = true;
                    }.bind(this)
                });
                return;
            }
            this.render();
        },

        render: function () {
            this.$(this.valueEl).html(this.template(this.getRenderData()));
        },

        fetch: function (options) {
            if (!this.collection) {
                var Collection = this.CollectionType;
                this.collection = new Collection();
            }
            this.collection.fetch({
                success: function () {
                    options.success.call(this);
                }.bind(this)
            });
        },

        getRenderData: function () {
            var data = this.collection.toJSON();
            return {
                options: this.toOptionArray(data)
            };
        },

        toOptionArray: function (data) {
            return data.map(function (item) {
                return this.toOption(item);
            }, this);
        },

        toOption: function (data) {
            return {
                value: this.getValueFromData(data),
                label: this.getLabelFromData(data),
                checked: this.isChecked(data)
            };
        },

        isChecked: function (data) {
            if (!this.value) {
                return false;
            }
            return this.value.some(function (v) {
                return String(v) === String(this.getValueFromData(data));
            }, this);
        },

        getValueFromData: function (data) {
            return data.id;
        },

        getLabelFromData: function (data) {
            return data.name;
        }
        
    });
    
    return AjaxCheckboxGroup;
    
});

