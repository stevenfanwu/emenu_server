/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BasePage = require('./BasePage');
    var LoginForm = require('../form/LoginForm');

    var Login = BasePage.extend({
        initialize: function () {
            BasePage.prototype.initialize.apply(this, arguments);
        
            var LoginForm = require('../form/LoginForm');
            this.form = new LoginForm({
                el: '.form-login'
            });
        },
    });

    return Login;
});

