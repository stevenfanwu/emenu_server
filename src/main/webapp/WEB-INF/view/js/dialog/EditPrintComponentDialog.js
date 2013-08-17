/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var EditDialog = require('./EditDialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./EditPrintComponentDialog.handlebars'),

        events: {
            'click .btn-add-date': 'onAddDate',
            'click .btn-add-custom-num': 'onAddCustomNum',
            'click .btn-add-table-name': 'onAddTableName',
            'click .btn-add-order-id': 'onAddOrderId',
            'click .btn-add-waiter-name': 'onAddWaiterName',
            'click .btn-add-cost': 'onAddCost'
        },

        addText: function (text) {
            this.$('textarea').val(this.$('textarea').val() + text + ' ');
        },
        
        /* -------------------- Event Listener ----------------------- */
        
        onAddDate: function (evt) {
            evt.preventDefault();
            this.addText('$time');
            evt.stopPropagation();
        },

        onAddCustomNum: function (evt) {
            evt.preventDefault();
            this.addText('$order.customerNumber');
            evt.stopPropagation();
        },

        onAddTableName: function (evt) {
            evt.preventDefault();
            this.addText('$table.name');
            evt.stopPropagation();
        },

        onAddOrderId: function (evt) {
            evt.preventDefault();
            this.addText('$order.id');
            evt.stopPropagation();
        },

        onAddWaiterName: function (evt) {
            evt.preventDefault();
            this.addText('$userName');
            evt.stopPropagation();
        },

        onAddCost: function (evt) {
            evt.preventDefault();
            this.addText('$bill.cost');
            evt.stopPropagation();
        }
        
    });
    
    var EditPrintComponentDialog = EditDialog.extend({
        headerSuffix: '页眉页脚',

        ContentType: Content,

        formEl: '.form-edit-print-component',

        FormType: require('../form/EditPrintComponentForm')
    });
    
    return EditPrintComponentDialog;
    
});
