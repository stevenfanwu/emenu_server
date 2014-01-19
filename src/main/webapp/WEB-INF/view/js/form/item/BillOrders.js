/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');
    var $ = require('../../lib/jquery');

    var BillOrders = BaseItem.extend({
        getValue: function () {
            var value = [];
            var items = this.form.page.dishItems;
            items.forEach(function (item) {
                var dishId = item.model.get('id');
                var checked = item.$('input').is(':checked');
                if (checked) {
                    value.push(dishId);
                }
            }, this);
            return value;
        },

        setValue: function (dishIds) {
            dishIds = dishIds || [];
            $('.cb-discount').prop('checked', false);
            dishIds.forEach(function (dishId) {
                $('#dish-item-' + dishId + ' .cb-discount').prop('checked', true);
            }, this);
        }
    });
    
    return BillOrders;
    
});

