/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseForm = require('./BaseForm');
    var Required = require('./validator/Required');
    var IsSame = require('./validator/IsSame');
    var Text = require('./item/Text');
    var Radio = require('./item/Radio');

    var EditUserForm = BaseForm.extend({
        url: '/api/users',

        itemConfig: [{
            name: 'name',
            type: Text,
            el: '.input-name',
            validators: [{
                type: Required,
                errorMessage: "Username can't be empty."
            }]
        }, {
            name: 'password',
            type: Text,
            el: '.input-password',
            validators: [{
                type: Required,
                errorMessage: "Password can't be emtpy."
            }]
        }, {
            name: 'passwordConfirm',
            type: Text,
            el: '.input-password-confirm',
            validators: [{
                type: IsSame,
                otherItem: 'password',
                errorMessage: 'Passwords are different.'
            }]
        }, {
            name: 'realName',
            type: Text,
            el: '.input-realName',
            validators: [{
                type: Required,
                errorMessage: "Real name can't be empty"
            }]
        }, {
            name: 'type',
            type: Radio,
            el: '.input-type'
        }, {
            name: 'restaurantId',
            type: Text,
            el: '.input-restaurantId'
        }, {
            name: 'comment',
            type: Text,
            el: '.input-comment'
        }],

        initialize: function () {
            BaseForm.prototype.initialize.apply(this, arguments);

            if (!this.model.isNew()) {
                this.hiddenItems = ['password', 'passwordConfirm'];
            } else {
                this.hiddenItems = [];
            }
        }

    });
    
    return EditUserForm;
    
});
