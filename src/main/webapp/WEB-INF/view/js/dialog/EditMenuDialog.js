/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var EditDialog = require('./EditDialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./EditMenuDialog.handlebars')

    });

    var EditMenuDialog = EditDialog.extend({
        headerSuffix: 'Menu',

        ContentType: Content,

        formEl: '.form-edit-menu',

        FormType: require('../form/EditMenuForm')
        
    });
    
    return EditMenuDialog;
});

