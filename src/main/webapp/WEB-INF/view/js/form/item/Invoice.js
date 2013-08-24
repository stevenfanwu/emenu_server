/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');

    var Invoice = BaseItem.extend({
        
        init: function () {
            BaseItem.prototype.init.apply(this, arguments);
            this.$('input[type=checkbox]').click(function () {
                this.setValue(this.getValue());
            }.bind(this));
        },
        
        setValueFromModel: function (model) {
            var value = {
                invoice: false,
                invoicePrice: 0
            };
            if (model.get('invoice')) {
                value.invoice = true;
                value.invoicePrice = model.get('invoicePrice');
                if (value.invoicePrice === null || value.invoicePrice === undefined) {
                    value.invoicePrice = 0;
                }
            }
            this.setValue(value);
        },

        saveValueToModel: function (model) {
            var value = this.getValue();
            model.set('invoice', value.invoice);
            model.set('invoicePrice', parseFloat(value.invoicePrice, 10));
        },

        setValue: function (value) {
            this.$('input[type=checkbox]').prop('checked', value.invoice);
            if (value.invoice) {
                this.$('.input-invoicePrice').show();
                this.$('input[type=text]').val(value.invoicePrice);
            } else {
                this.$('.input-invoicePrice').hide();
            }
        },

        showError: function (msg) {
            this.$('.input-invoicePrice').addClass('error');
            this.$('.input-invoicePrice .help-inline').show();
            this.$('.input-invoicePrice .help-inline').text(msg);
        },

        showSuccess: function () {
            this.$('.input-invoicePrice').addClass('success');
        },

        clearTip: function () {
            this.$('.input-invoicePrice').removeClass('success');
            this.$('.input-invoicePrice').removeClass('error');
            this.$('.input-invoicePrice .help-inline').text('');
            this.$('.input-invoicePrice .help-inline').hide();
        },

        getValue: function () {
            var value = {
                invoice: false,
                invoicePrice: 0
            };
            value.invoice = this.$('input[type=checkbox]').prop('checked');
            if (value.invoice) {
                value.invoicePrice = this.$('input[type=text]').val();
            }
            return value;
        }
    });
    
    return Invoice;
    
});

