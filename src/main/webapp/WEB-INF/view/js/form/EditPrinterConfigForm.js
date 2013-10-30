/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseForm = require('./BaseForm');

    var AjaxCheckboxGroup = require('./item/AjaxCheckboxGroup');
    var Checkbox = require('./item/CheckBox');

    var PrintTemplateCollection = require('../collection/PrintTemplateCollection');
    
    var EditPrinterConfigForm = BaseForm.extend({
        itemConfig: [{
            name: 'whenOrdered',
            type: Checkbox
        }, {
            name: 'orderedTemplateIds',
            type: AjaxCheckboxGroup,
            CollectionType: PrintTemplateCollection
        }, {
            name: 'whenBill',
            type: Checkbox
        }, {
            name: 'billTemplateIds',
            type: AjaxCheckboxGroup,
            CollectionType: PrintTemplateCollection
        }, {
            name: 'whenAdd',
            type: Checkbox
        }, {
            name: 'addTemplateIds',
            type: AjaxCheckboxGroup,
            CollectionType: PrintTemplateCollection
        }, {
            name: 'whenCancel',
            type: Checkbox
        }, {
            name: 'cancelTemplateIds',
            type: AjaxCheckboxGroup,
            CollectionType: PrintTemplateCollection
        }]
    });
    
    return EditPrinterConfigForm;
    
});
