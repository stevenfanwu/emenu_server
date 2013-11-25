/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseRouter = require('./BaseRouter');

    var PrintComponentRouter = BaseRouter.extend({
        routes: {
            '': 'showComponents',
            'components': 'showComponents',
            'templates': 'showTemplates',
            'printers': 'showPrinters'
        },

        showComponents: function () {
            this.page.trigger('showComponents');
        },

        showTemplates: function () {
            this.page.trigger('showTemplates');
        },

        showPrinters: function () {
            this.page.trigger('showPrinters');
        }
    });

    return PrintComponentRouter;
});

