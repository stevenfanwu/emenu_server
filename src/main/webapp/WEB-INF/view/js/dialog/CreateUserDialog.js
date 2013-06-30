/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Dialog = require('./Dialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./CreateUserDialog.handlebars')
    });

    var CreateUserDialog = Dialog.extend({
        header: '新建用户',

        ContentType: Content
    });
    
    return CreateUserDialog;
    
});

