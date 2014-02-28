/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";


    var EditDialog = require('./EditDialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./EditPadDialog.handlebars')
    });

    var EditPadDialog = EditDialog.extend({
        headerSuffix: 'Device',

        ContentType: Content,

        formEl: '.form-edit-pad',

        FormType: require('../form/EditPadForm')
        
    });
    
    return EditPadDialog;
    
});

