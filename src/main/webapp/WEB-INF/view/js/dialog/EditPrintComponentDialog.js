/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var EditDialog = require('./EditDialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./EditPrintComponentDialog.handlebars')
    });
    
    var EditPrintComponentDialog = EditDialog.extend({
        headerSuffix: '页眉页脚',

        ContentType: Content,

        FormType: require('../form/EditPrintComponentForm')
    });
    
    return EditPrintComponentDialog;
    
});

