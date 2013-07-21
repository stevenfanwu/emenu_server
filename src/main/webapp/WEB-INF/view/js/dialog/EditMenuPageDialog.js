/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var EditDialog = require('./EditDialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./EditMenuPageDialog.handlebars')

    });
    
    var EditMenuPageDialog = EditDialog.extend({
        headerSuffix: '菜单页',

        ContentType: Content,

        formEl: '.form-edit-menu-page',

        FormType: require('../form/EditMenuPageForm')
        
    });
    
    return EditMenuPageDialog;
    
});
