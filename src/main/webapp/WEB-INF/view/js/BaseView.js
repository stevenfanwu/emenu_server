/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Backbone = require('./lib/backbone');
    var PageDataUtils = require('./util/PageDataUtils');
    var $ = require('./lib/jquery');
    var handlebars = require('./lib/handlebars');

    var BaseView = Backbone.View.extend({

        tmpl: null,

        events: {
            'mouseenter .hover-tip': 'onMouseEnter',
            'click .btn-back': 'onHistoryBack'
        },

        template : function (data, tmpl) {
            tmpl = tmpl || this.tmpl;
            var template = handlebars.compile(tmpl.template);
            return template(data);
        },

        resetContent: function () {
            this.render();
        },

        empty: function () {
            this.$el.empty();
        },

        render: function () {
            Backbone.View.prototype.render.apply(this, arguments);
            if (this.tmpl) {
                this.el.innerHTML = this.template(this.getRenderData());
            } else {
                this.empty();
            }
        },

        getRenderData: function () {
            if (!this.model) {
                return {};
            }
            return this.model.toJSON();
        },

        getCurrentUser: function () {
            return PageDataUtils.getData('loginUser');
        },

        destroy: function () {
            if (this.$el) {
                this.undelegateEvents();
            }
            this.off();
        },

        /* -------------------- Event Listener ----------------------- */
        
        onMouseEnter: function (evt) {
            evt.preventDefault();
            $(evt.currentTarget).tooltip('show');
            evt.stopPropagation();
        },

        onHistoryBack: function (evt) {
            evt.preventDefault();
            history.back();
            evt.stopPropagation();
        }
    });

    return BaseView;
});

