/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseForm = require('./BaseForm');
    var Required = require('./validator/Required');
    var Number = require('./validator/Number');
    var MoreThan = require('./validator/MoreThan');
    var LessThan = require('./validator/LessThan');
    var Text = require('./item/Text');
    var AjaxSelect = require('./item/AjaxSelect');
    var Select = require('./item/Select');
    var CheckBox = require('./item/CheckBox');
    var BillOrders = require('./item/BillOrders');
    var Invoice = require('./item/Invoice');
    var InvoiceValidator = require('./validator/InvoiceValidator');
    var Textarea = require('./item/Textarea');

    var BillForm = BaseForm.extend({
        url: '/api/bills',
        
        itemConfig: [{
            name: 'tip',
            type: Text,
            el: '.input-tip',
            validators: [{
                type: Required,
                errorMessage: '请输入服务费'
            }, {
                type: Number,
                errorMessage: '请输入数字'
            }, {
                type: MoreThan,
                other: 0,
                including: true,
                errorMessage: '不能小于0'
            }]
        }, {
            name: 'discount',
            type: Text,
            el: '.input-discount',
            validators: [{
                type: Required,
                errorMessage: '请输入折扣'
            }, {
                type: Number,
                errorMessage: '请输入数字'
            }, {
                type: MoreThan,
                other: 0,
                including: true,
                errorMessage: '不能小于0'
            }, {
                type: LessThan,
                other: 10,
                including: true,
                errorMessage: '不能大于10'
            }]
        }, {
            name: 'cost',
            type: Text,
            el: '.input-cost',
            validators: [{
                type: Required,
                errorMessage: '请输入实收金额'
            }, {
                type: Number,
                errorMessage: '请输入数字'
            }, {
                type: MoreThan,
                other: 0,
                including: true,
                errorMessage: '不能小于0'
            }]
        }, {
            name: 'invoice',
            type: Invoice,
            el: '.invoice-wrap',
            validators: [{
                type: InvoiceValidator
            }]
        }, {
            name: 'payType',
            type: AjaxSelect,
            el: '.input-payType',
            wrapId: true,
            CollectionType: require('../collection/PayTypeCollection'),
            validators: [{
                type: Required
            }]
        }, {
            name: 'remarks',
            type: Textarea,
            el: '.input-remarks'
        }, {
            name: 'discountDishIds',
            type: BillOrders
        }],

        getFormData: function () {
            var data = BaseForm.prototype.getFormData.apply(this, arguments);
            if (!this.$('.cb-printer').is(':checked')) {
                data.printer = "";
            }
            return data;
        },
        

        /* -------------------- Event Listener ----------------------- */
        
        onSuccess: function (evt) {
            window.alert('结账成功');
            window.location = '/home';
        },

        onSubmit: function (evt) {
            var payType = this.findItemByName('payType').getLabel();
            if (window.confirm('是否' + payType + '结账?')) {
                BaseForm.prototype.onSubmit.apply(this, arguments);
            }
        }
        
    });
    
    return BillForm;
    
});

