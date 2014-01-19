/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');

    var TableTip = BaseItem.extend({
        init: function () {
            BaseItem.prototype.init.apply(this, arguments);
        
            this.$('input:radio').click(function () {
                var mode =  parseInt(this.$('input:checked').val(), 10);
                this.onModeChanged(mode);
            }.bind(this));
        },

        setValueFromModel: function (model) {
            var value = {
                tipMode: model.get('tipMode')
            };
            if (value.tipMode !== 0) {
                value.tip = model.get('tip');
            }
            this.setValue(value);
        },

        setValue: function (value) {
            this.onModeChanged(value.tipMode);
            this.$('input[name=tipMode]').prop('checked', false);
            this.$('input[name=tipMode]input[value=' + value.tipMode + ']').prop('checked', true);
            this.$('input[name=tip]').val(value.tip);
        },

        getValue: function () {
            var tipMode =  parseInt(this.$('input:checked').val(), 10);
            var tip = parseFloat(this.$('input[name=tip]').val(), 10);
            return {
                tipMode: tipMode,
                tip: tip
            };
        },

        showError: function (msg) {
            this.$('.wrap-tip').addClass('error');
            this.$('.help-inline').show();
            this.$('.help-inline').text(msg);
        },

        showSuccess: function () {
            this.$('.wrap-tip').addClass('success');
        },

        clearTip: function () {
            this.$('.wrap-tip').removeClass('success');
            this.$('.wrap-tip').removeClass('error');
            this.$('.help-inline').text('');
            this.$('.help-inline').hide();
        },

        saveValueToModel: function (model) {
            var value = this.getValue();
            model.set('tipMode', value.tipMode);
            if (value.tipMode !== 0) {
                model.set('tip', value.tip);
            }
        },
        

        /* -------------------- Event Listener ----------------------- */
        
        onModeChanged: function (mode) {
            this.reset();
            if (mode === 0) {
                this.$('.wrap-tip').hide();
                this.$('.add-on').hide();
            } else if (mode === 1) {
                this.$('.wrap-tip').show();
                this.$('.add-on').hide();
            } else {
                this.$('.wrap-tip').show();
                this.$('.add-on').show();
            }
        }
        
    });

    return TableTip;

});

