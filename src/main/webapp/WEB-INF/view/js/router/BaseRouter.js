/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Backbone = require('../lib/backbone');

    var BaseRouter = Backbone.Router.extend({
        initialize: function (options) {
            Backbone.Router.prototype.initialize.apply(this, arguments);

            this.page = options.page;
        },

        routes: {
            "": "renderPage"
        },

        page: null,

        renderPage: function () {
            this.page.render();
        }
        
    });

    return BaseRouter;
});

