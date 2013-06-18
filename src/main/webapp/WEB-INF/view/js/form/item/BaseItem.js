/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Backbone = require('../../lib/backbone');
    var BaseView = require('../../BaseView');
    var $ = require('../../lib/jquery');
    
    var BaseItem = BaseView.extend({
        validators: [],

        reset: function () {
            this.$el.removeClass('success');
            this.$el.removeClass('error');
            this.clearError();
        },

        parseConfig: function (config) {
            //config validators
            this.validators = [];
            config.validators.forEach(function (validatorConfig) {
                var Validator = validatorConfig.type;
                var validator = new Validator();
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

        clearError: function() {
            this.$('.help-inline').text('');
            this.$('.help-inline').hide();
        }
    });

    return BaseItem;
});

