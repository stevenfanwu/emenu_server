/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseForm = require('./BaseForm');
    var Select = require('./item/Select');
    var Required = require('./validator/Required');

    var CancelDishForm = BaseForm.extend({
        itemConfig: [{
            name: 'count',
            type: Select,
            el: '.input-count',
            validators: [{
                type: Required
            }]
        }],

        init: function () {
            BaseForm.prototype.init.apply(this, arguments);

            var countItem = this.findItemByName('count');
            countItem.setValue(this.model.get('number'));
        },

        doSubmit: function () {
            var url = '/api/orders/' + this.model.get('orderId')
                    + '/dishes/' + this.model.get('id') + '/';
            url = url + (this.mode === 'cancel' ? 'cancel' : 'free');
            this.ajaxSubmit({
                type: 'PUT',
                url: url,
                data: {
                    count: this.getFormData().count
                }
            });
        }
        
        
        
    });
    
    return CancelDishForm;
    
});
