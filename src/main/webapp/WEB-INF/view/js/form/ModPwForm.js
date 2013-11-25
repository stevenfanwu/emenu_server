/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseForm = require('./BaseForm');
    var Required = require('./validator/Required');
    var IsSame = require('./validator/IsSame');
    var Text = require('./item/Text');
    var _ = require('../lib/underscore');

    var ModPwForm = BaseForm.extend({
        url: '/api/users/:id/password',

        itemConfig: [{
            name: 'oldPassword',
            type: Text,
            el: '.input-password-old',
            validators: [{
                type: Required,
                errorMessage: '旧密码不能为空'
            }]
        }, {
            name: 'newPassword',
            type: Text,
            el: '.input-password-new',
            validators: [{
                type: Required,
                errorMessage: '新密码不能为空'
            }]
        }, {
            name: 'passwordConfirm',
            type: Text,
            el: '.input-password-confirm',
            validators: [{
                type: IsSame,
                otherItem: 'newPassword',
                errorMessage: '两次密码输入不一致'
            }]
        }],

        doSubmit: function () {
            var url = '/api/users/' + this.model.get('id') + '/password';
            this.ajaxSubmit({
                url: url,
                type: 'PUT'
            });
        },

        getFormData: function () {
            var data = BaseForm.prototype.getFormData.apply(this, arguments);
            data = _.pick(data, 'oldPassword', 'newPassword');
            return data;
        }
        
    });
    
    return ModPwForm;
    
});

