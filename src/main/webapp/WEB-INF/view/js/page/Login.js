/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BasePage = require('./BasePage');
    var LoginForm = require('../form/LoginForm');
    var UserModel = require('../model/UserModel');

    var Login = BasePage.extend({
        initialize: function () {
            BasePage.prototype.initialize.apply(this, arguments);
        
            var LoginForm = require('../form/LoginForm');
            this.form = new LoginForm({
                model: new UserModel()
            });
            this.form.init('.form-login');
        }
    });

    return Login;
});

