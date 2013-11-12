/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var EditDialog = require('./EditDialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./EditTableDialog.handlebars')
    });

    var EditTableDialog = EditDialog.extend({
        headerSuffix: '餐桌',

        ContentType: Content,

        formEl: '.form-create-table',

        FormType: require('../form/EditTableForm')
        
    });
    
    return EditTableDialog;
});

