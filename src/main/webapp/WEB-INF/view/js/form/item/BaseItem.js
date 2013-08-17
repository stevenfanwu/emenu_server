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

        valueEl: 'input',

        value: null,

        reset: function () {
            this.clearTip();
        },

        focus: function () {
            this.$(this.valueEl).focus();
        },

        hasValidator: function (validatorType) {
            return this.findValidator(validatorType) !== null;
        },

        findValidator: function (validatorType) {
            var r = null;
            this.validators.some(function (validator) {
                if (validator instanceof validatorType) {
                    r = validator;
                    return true;
                }
                return false;
            });
            return r;
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
            var result = {
                success: true
            };
            this.validators.every(function (validator) {
                result = validator.validate(this);
                return result.success;
            }, this);
            this.clearTip();
            if (result.success) {
                this.showSuccess();
            } else {
                this.showError(result.error);
            }
            return result.success;
        },

        getValue: function () {
            return this.$(this.valueEl).val();
        },

        setValue: function (value) {
            return this.$(this.valueEl).val(value);
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

        clearTip: function () {
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

