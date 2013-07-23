/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Backbone = require('./lib/backbone');
    var PageDataUtils = require('./util/PageDataUtils');
    var $ = require('./lib/jquery');

    var BaseView = Backbone.View.extend({

        tmpl: null,

        events: {
            'mouseenter .hover-tip': 'onMouseEnter'
        },

        template : function (data, tmpl) {
            var handlebars = require('./lib/handlebars');
            tmpl = tmpl || this.tmpl;
            var template = handlebars.compile(tmpl.template);
            return template(data);
        },

        resetContent: function () {
            this.$el.empty();
            this.render();
        },

        render: function () {
            Backbone.View.prototype.render.apply(this, arguments);
            if (this.tmpl) {
                this.el.innerHTML = this.template(this.getRenderData());
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

        /* -------------------- Event Listener ----------------------- */
        
        onMouseEnter: function (evt) {
            evt.preventDefault();
            $(evt.currentTarget).tooltip('show');
            evt.stopPropagation();
        }
    });

    return BaseView;
});

