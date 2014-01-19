/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseForm = require('./BaseForm');
    
    var Required = require('./validator/Required');
    var Text = require('./item/Text');

    var EditPadForm = BaseForm.extend({
        url: '/api/pads',

        itemConfig: [{
            name: 'name',
            type: Text,
            validators: [{
                type: Required,
                errorMessage: '平板名称不能为空'
            }]
        }, {
            name: 'imei',
            type: Text,
            validators: [{
                type: Required,
                errorMessage: 'IMEI码不能为空'
            }]
        }, {
            name: 'desc',
            type: Text
        }]
    });
    
    return EditPadForm;
    
});

