/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');
    var ChangeTableDialog = require('../../dialog/ChangeTableDialog');
    var TableUtils = require('../../util/TableUtils');

    var TableStatusItem = BaseItem.extend({
        tmpl: require('./TableStatusItem.handlebars'),

        tagName: 'li',

        className: 'span2 thumbnail table-thumbnail',

        events: {
            'click .btn-bill': 'onBill',
            'click .btn-change': 'onChange'
        },

        getRenderData: function () {
            var data = BaseItem.prototype.getRenderData.apply(this, arguments);
            data.empty = data.status === 0;
            data.hasOrder = !data.empty && data.orderId > 0;
            return data;
        },
        
        /* -------------------- Event Listener ----------------------- */
        
        onBill: function (evt) {
            if (this.model.get('status') === 0 || this.model.get('orderId') === 0) {
                evt.preventDefault();
            }
        },

        onChange: function (evt) {
            evt.preventDefault();
            if (this.model.get('status') === 0) {
                return;
            }
            var dialog = new ChangeTableDialog();
            dialog.show();
            dialog.on('submit', function (tableModel) {
                TableUtils.changeTable({
                    data: {
                        fromId: this.model.get('id'),
                        toId: tableModel.get('id')
                    },

                    success: function () {
                        dialog.hide();
                        this.trigger('refreshList');
                    }.bind(this)
                }, this);
            }, this);
            evt.stopPropagation();
        }
        
        
    });
    
    return TableStatusItem;
    
});

