/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseRouter = require('./BaseRouter');

    var DeviceRouter = BaseRouter.extend({
        routes: {
            '': 'showPad',
            'pad': 'showPad'
        },

        showPad: function () {
            this.page.trigger('showPad');
        }
    });

    return DeviceRouter;
});

