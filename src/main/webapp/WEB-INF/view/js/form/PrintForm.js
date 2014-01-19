/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseForm = require('./BaseForm');

    var AjaxSelect = require('./item/AjaxSelect');
    var Required = require('./validator/Required');
    var _ = require('../lib/underscore');

    var PrintForm = BaseForm.extend({
        itemConfig: [{
            name: 'printerId',
            type: AjaxSelect,
            CollectionType: require('../collection/PrinterConfigCollection'),
            wrapId: true,
            validators: [{
                type: Required
            }]
        }, {
            name: 'templateId',
            type: AjaxSelect,
            CollectionType: require('../collection/PrintTemplateCollection'),
            wrapId: true,
            validators: [{
                type: Required,
                errorMessage: '请选择打印模板'
            }]
        }],

        getFormData: function () {
            var data = BaseForm.prototype.getFormData.apply(this, arguments);
            data.orderId = this.model.get('id');
            return _.pick(data, 'orderId', 'templateId', 'printerId');
        },
        
        doSubmit: function () {
            this.ajaxSubmit({
                url: '/api/printers/print'
            });
        }
        
    });
    
    return PrintForm;
    
});

