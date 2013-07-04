/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Backbone = require('../../lib/backbone');
    var BaseView = require('../../BaseView');
    var $ = require('../../lib/jquery');
    
    var BaseItem = BaseView.extend({
        validators: [],

        form: null,

        reset: function () {
            this.clearError();
        },

        parseConfig: function (config) {
            //config validators
            this.name = config.name;
            this.validators = [];
            config.validators = config.validators || [];
            config.validators.forEach(function (validatorConfig) {
                var Validator = validatorConfig.type;
                var validator = new Validator({
                    item: this,
                    form: this.form
                });
                validator.parseConfig(validatorConfig);
                this.validators.push(validator);
            }, this);
        },

        validate: function () {
            return this.validators.every(function (validator) {
                return validator.validate(this);
            }, this);
        },

        getValue: function () {
            return this.$('input').val();
        },

        setValue: function (value) {
            return this.$('input').val(value);
        },

        isEmpty: function () {
            if (this.getValue()) {
                return false;
            }
            return true;
        },

        showError: function (msg) {
            this.$el.addClass('error');
            this.$('.help-inline').show();
            this.$('.help-inline').text(msg);
        },

        showSuccess: function () {
            this.$el.addClass('success');
        },

        clearError: function () {
            this.$el.removeClass('success');
            this.$el.removeClass('error');
            this.$('.help-inline').text('');
            this.$('.help-inline').hide();
        },

        init: function () {
            this.setValueFromModel(this.form.model);
        },

        setValueFromModel: function (model) {
            var value = model.get(this.name);
            if (value !== undefined) {
                this.setValue(value);
            }
        },

        saveValueToModel: function (model) {
            model.set(this.name, this.getValue());
        }
    });

    return BaseItem;
});

