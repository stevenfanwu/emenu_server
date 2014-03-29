/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var EditDialog = require('./EditDialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./EditPrinterConfigDialog.handlebars')
    });
    
    
    var EditPrinterConfigDialog = EditDialog.extend({
        headerSuffix: 'Print Config',

        ContentType: Content,

        formEl: '.form-edit-printer-config',

        FormType: require('../form/EditPrinterConfigForm')
    });
    
    return EditPrinterConfigDialog;
});
