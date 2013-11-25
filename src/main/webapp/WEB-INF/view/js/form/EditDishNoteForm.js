/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseForm = require('./BaseForm');
    var Required = require('./validator/Required');
    var Text = require('./item/Text');

    var EditDishNoteForm = BaseForm.extend({
        url: '/api/dish/notes',
        
        itemConfig: [{
            name: 'name',
            type: Text,
            el: '.input-name',
            validators: [{
                type: Required,
                errorMessage: '名称不能为空'
            }]
        }]
    });
    
    return EditDishNoteForm;

});

