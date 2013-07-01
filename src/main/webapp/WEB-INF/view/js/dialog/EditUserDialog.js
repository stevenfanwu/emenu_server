/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var EditDialog = require('./EditDialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./EditUserDialog.handlebars')
    });

    var EditUserDialog = EditDialog.extend({
        headerSuffix: '用户',

        ContentType: Content,

        FormType: require('../form/EditUserForm')
        
    });
    
    return EditUserDialog;
    
});

