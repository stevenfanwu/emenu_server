/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseForm = require('./BaseForm');
    var $ = require('../lib/jquery');

    var Text = require('./item/Text');
    var Required = require('./validator/Required');

    var LoginForm = BaseForm.extend({
        itemConfig: [{
            name: 'name',
            type: Text,
            el: '.input-name',
            validators: [{
                type: Required,
                errorMessage: "User name can't be emtpty"
            }]
        }, {
            name: 'password',
            type: Text,
            el: '.input-password',
            validators: [{
                type: Required,
                errorMessage: "Password can't be empty"
            }]
        }],

        doSubmit: function () {
            this.ajaxSubmit({
                url: '/api/login',
                statusCode: {
                    401: function () {
                        this.resetItems();
                        this.findItemByName('password').showError('Username or password is incorrect');
                    }.bind(this)
                }
            });
        },

        onSuccess: function () {
            window.location = 'home';
        }
    });

    return LoginForm;
});
