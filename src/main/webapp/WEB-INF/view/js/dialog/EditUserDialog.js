/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var EditDialog = require('./EditDialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./EditUserDialog.handlebars'),

        getRenderData: function () {
            var data = BaseContent.prototype.getRenderData.apply(this, arguments);
            var loginUser = require('../util/PageDataUtils').getData('loginUser');
            data.loginUser = {
                isSuperUser: loginUser.type
                     === require('../misc/Const').UserType.SUPER_USER.value
            };
            return data;
        }
    });

    var EditUserDialog = EditDialog.extend({
        headerSuffix: ' User',

        ContentType: Content,

        formEl: '.form-create-user',

        FormType: require('../form/EditUserForm')
        
        
    });
    
    return EditUserDialog;
    
});

