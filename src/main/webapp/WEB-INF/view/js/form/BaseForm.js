/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Backbone = require('../lib/backbone');
    var BaseView = require('../BaseView');

    var BaseForm = BaseView.extend({
        events: {
            'click .btn-submit': 'onSubmit'
        },

        url: null,

        type: "post",

        items: [],

        itemConfig: [],

        initialize: function () {
            BaseView.prototype.initialize.apply(this, arguments);
            if (this.el) {
                this.init(this.el);
            }
        },
        
        init: function (el) {
            this.setElement(el);
            this.parseItemConfig();
            this.reset();

            this.$('input').keypress(function (evt) {
                if (evt.which === 13) {
                    this.onSubmit(evt);
                }
            }.bind(this));
        },

        parseItemConfig: function () {
            this.items = [];
            this.itemConfig.forEach(function (config) {
                var Item = config.type;
                var item = new Item();
                item.form = this;
                item.setElement(this.$(config.el)[0]);
                item.parseConfig(config);
                this.items.push(item);
            }, this);
        },

        reset: function () {
            this.resetItems();
        },

        resetItems: function () {
            this.items.forEach(function (item) {
                item.reset();
            }, this);
        },

        trySubmit: function () {
            this.reset();
            if (this.validateForm()) {
                this.doSubmit();
            }
        },

        validateForm: function () {
            var result = true;
            this.items.forEach(function (item) {
                if (!item.validate()) {
                    result = false;
                }
            }, this);
            return result;
        },

        doSubmit: function () {
            var options = this.createAjaxOptions();
            this.$el.ajaxSubmit(options);
        },

        createAjaxOptions: function () {
            var options = {
                url: this.url,
                type: this.type,
                resetForm: true,
                success: this.onSuccess
            };
            return options;
        },

        findItemByName: function (name) {
            var ret = null;
            this.items.some(function (item) {
                if (item.name == name) {
                    ret = item;
                    return true;
                }
                return false;
            }, this);

            return ret;
        },

        /* ---------- abstract ---------- */

        onSuccess: function () {},

        /* ---------- Event Listener ---------- */

        onSubmit: function (evt) {
            evt.preventDefault();
            this.trySubmit();
        }
    });

    return BaseForm;
});

