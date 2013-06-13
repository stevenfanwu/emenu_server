/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Backbone = require('../lib/backbone');
    var BaseView = require('../BaseView');

    var BaseForm = BaseView.extend({
        url: null,

        type: "post",

        trySubmit: function () {
            if (this.validateForm()) {
                this.doSubmit();
            }
        },

        validateForm: function () {
            return true;
        },

        doSubmit: function () {
            var options = {
                url: this.url,
                type: this.type,
                resetForm: true,
                success: this.onSuccess
            };
            this.$el.ajaxSubmit(options);
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

