/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";
    
    var EditDialog = require('./EditDialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./EditDishTagDialog.handlebars')

    });

    var EditDishTagDialog = EditDialog.extend({
        headerSuffix: '菜品做法',

        ContentType: Content,

        formEl: '.form-edit-dish-tag',

        FormType: require('../form/EditDishTagForm')
        
    });
    
    return EditDishTagDialog;
});

