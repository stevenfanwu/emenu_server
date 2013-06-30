/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseForm = require('./BaseForm');
    var Required = require('./validator/Required');
    var IsSame = require('./validator/IsSame');
    var Text = require('./item/Text');

    var CreateUserForm = BaseForm.extend({
        url: '/api/users',

        itemConfig: [{
            name: 'name',
            type: Text,
            el: '.input-name',
            validators: [{
                type: Required,
                errorMessage: '用户名不能为空'
            }]
        }, {
            name: 'password',
            type: Text,
            el: '.input-password',
            validators: [{
                type: Required,
                errorMessage: '密码不能为空'
            }]
        }, {
            name: 'passwordConfirm',
            type: Text,
            el: '.input-password-confirm',
            validators: [{
                type: IsSame,
                otherItem: 'password',
                errorMessage: '两次密码输入不一致'
            }]
        }, {
            name: 'realName',
            type: Text,
            el: '.input-realName',
            validators: [{
                type: Required,
                errorMessage: '姓名不能为空'
            }]
        }]
    });
    
    return CreateUserForm;
    
});

