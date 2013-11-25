/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseRouter = require('./BaseRouter');

    var UserRouter = BaseRouter.extend({
        routes: {
            '': 'showAll',
            'all': 'showAll',
            'admin': 'showAdmin',
            'user': 'showUser'
        },

        showAll: function () {
            this.page.trigger('showAll');
        },

        showAdmin: function () {
            this.page.trigger('showAdmin');
        },

        showUser: function () {
            this.page.trigger('showUser');
        }
    });

    return UserRouter;
});
