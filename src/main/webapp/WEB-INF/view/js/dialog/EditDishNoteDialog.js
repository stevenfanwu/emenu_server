/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";
    
    var EditDialog = require('./EditDialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./EditDishNoteDialog.handlebars')

    });

    var EditDishNoteDialog = EditDialog.extend({
        headerSuffix: 'Dish note',

        ContentType: Content,

        formEl: '.form-edit-dish-note',

        FormType: require('../form/EditDishNoteForm')
        
    });
    
    return EditDishNoteDialog;

    
});

