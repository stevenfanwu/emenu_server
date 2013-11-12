/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var $ = require('../lib/jquery');
    var _ = require('../lib/underscore');
    var Backbone = require('../lib/backbone');
    var BaseView = require('../BaseView');

    var BaseForm = BaseView.extend({
        events: {
            'click .btn-submit': 'onSubmit'
        },

        items: [],

        hiddenItems: [],

        model: null,

        itemConfig: [],

        originData: {},

        init: function (el) {
            this.originData = this.model.toJSON();
            this.setElement(el);
            this.parseItemConfig();
            this.reset();

            this.$('input').keypress(function (evt) {
                if (evt.which === 13) {
                    this.onSubmit(evt);
                }
            }.bind(this));

            this.on('cancel', this.onCancel, this);
        },

        parseItemConfig: function () {
            this.items = [];
            this.itemConfig.forEach(function (config) {
                if (!config.el) {
                    config.el = '.input-' + config.name;
                }
                if (this.hiddenItems.indexOf(config.name) !== -1) {
                    //hide item
                    this.$(config.el).hide();
                    return;
                }
                var Item = config.type;
                var item = new Item();
                item.form = this;
                item.setElement(this.$(config.el)[0]);
                item.parseConfig(config);
                item.init();
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
            var firstInvalidItem = null;
            this.items.forEach(function (item) {
                if (!item.validate()) {
                    if (!firstInvalidItem) {
                        firstInvalidItem = item;
                    }
                    result = false;
                } else {
                    item.saveValueToModel(this.model);
                }
            }, this);
            if (firstInvalidItem) {
                firstInvalidItem.focus();
            }
            return result;
        },

        doSubmit: function () {
            this.model.set(this.getFormData());
            this.model.save({}, {
                success: function (model, response, options) {
                    this.onSuccess(response);
                }.bind(this),

                error: function (model, xhr, options) {
                    this.onFailed(xhr);
                }.bind(this)
            });
        },

        ajaxSubmit: function (options) {
            options = _.extend({
                type: 'POST',
                data: this.getFormData(),
                success: this.onSuccess.bind(this),
                error: this.onFailed.bind(this),
                url: this.model ? this.model.url : ''
            }, options);
            $.ajax(options.url, options);
        },

        findItemByName: function (name) {
            var ret = null;
            this.items.some(function (item) {
                if (item.name === name) {
                    ret = item;
                    return true;
                }
                return false;
            }, this);

            return ret;
        },

        getFormData: function () {
            return this.model.toJSON();
        },

        /* ---------- abstract ---------- */

        onSuccess: function (response) {
            this.model.trigger('saved');
        },

        onFailed: function (xhr) {},

        /* ---------- Event Listener ---------- */

        onSubmit: function (evt) {
            evt.preventDefault();
            this.trySubmit();
        },

        onCancel: function (evt) {
            this.model.set(this.originData);
        }
    });

    return BaseForm;
});

