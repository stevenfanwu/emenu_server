/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var EditDialog = require('./EditDialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./EditChapterDialog.handlebars')

    });

    var EditChapterDialog = EditDialog.extend({
        headerSuffix: 'Category',

        ContentType: Content,

        formEl: '.form-edit-chapter',

        FormType: require('../form/EditChapterForm')
        
    });
    
    return EditChapterDialog;
    
});
