/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseForm = require('./BaseForm');

    var Required = require('./validator/Required');
    var Text = require('./item/Text');
    var Textarea = require('./item/Textarea');

    var EditPrintComponentForm = BaseForm.extend({
        url: '/api/printer/components',

        itemConfig: [{
            name: 'name',
            type: Text,
            el: '.input-name',
            validators: [{
                type: Required,
                errorMessage: '页眉页脚名称不能为空'
            }]
        }, {
            name: 'content',
            type: Textarea,
            el: '.input-content'
        }]
        
    });
    
    return EditPrintComponentForm;
    
});
