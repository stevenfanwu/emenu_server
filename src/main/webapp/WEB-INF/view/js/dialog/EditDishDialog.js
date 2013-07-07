/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var EditDialog = require('./EditDialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./EditDishDialog.handlebars')

    });

    var EditDishDialog = EditDialog.extend({
        headerSuffix: '菜品',

        ContentType: Content,

        formEl: '.form-edit-dish',

        FormType: require('../form/EditDishForm')
        
    });
    
    return EditDishDialog;
});

