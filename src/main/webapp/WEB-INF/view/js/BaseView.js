/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Backbone = require('./lib/backbone');
    var PageDataUtils = require('./util/PageDataUtils');

    var BaseView = Backbone.View.extend({

        tmpl: null,

        template : function (data) {
            var handlebars = require('./lib/handlebars');
            var template = handlebars.compile(this.tmpl.template);
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
        }
    });

    return BaseView;
});

