/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseForm = require('./BaseForm');

    var Required = require('./validator/Required');
    var Text = require('./item/Text');
    var Radio = require('./item/Radio');
    var AjaxSelect = require('./item/AjaxSelect');

    var PrintTemplateForm = BaseForm.extend({

        itemConfig: [{
            name: 'name',
            type: Text,
            el: '.input-name',
            validators: [{
                type: Required,
                errorMessage: '模板名称不能为空'
            }]
        }, {
            name: 'headerId',
            type: AjaxSelect,
            el: '.input-headerId',
            wrapId: true,
            CollectionType: require('../collection/PrintComponentCollection')
        }, {
            name: 'footerId',
            type: AjaxSelect,
            el: '.input-footerId',
            wrapId: true,
            CollectionType: require('../collection/PrintComponentCollection')
        }, {
            name: 'cutType',
            type: Radio,
            el: '.input-cutType'
        }]
    });
    
    return PrintTemplateForm;
    
});

