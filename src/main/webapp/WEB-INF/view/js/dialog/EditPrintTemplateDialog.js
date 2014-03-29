/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var EditDialog = require('./EditDialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./EditPrintTemplateDialog.handlebars')
    });
    
    var EditPrintTemplateDialog = EditDialog.extend({
        headerSuffix: ' Template',

        ContentType: Content,

        formEl: '.form-edit-print-template',

        FormType: require('../form/EditPrintTemplateForm')
    });
    
    return EditPrintTemplateDialog;
    
    
});

