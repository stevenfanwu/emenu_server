/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Tr = require('./Tr');
    var Const = require('../../misc/Const');

    var TableItem = Tr.extend({
        tmpl: require('./TableItem.handlebars'),

        events: {
            'click .btn-edit-table': 'onEditTable',
            'click .btn-delete-table': 'onDeleteTable'
        },

        getRenderData: function () {
            var data = Tr.prototype.getRenderData.apply(this, arguments);
            data.typeLabel = Const.TableType.getLabel(data.type);
            data.minChargeLabel = data.minCharge === 0 ? 'N/A' : data.minCharge;
            data.tipModeLabel = Const.TipMode.getLabel(data.tipMode);
            if (data.tipMode === Const.TipMode.NONE.value) {
                data.tipLabel = 'N/A';
            } else if (data.tipMode === Const.TipMode.PERCENTAGE.value) {
                data.tipLabel = data.tip + '%';
            } else {
                data.tipLabel = data.tip;
            }
            return data;
        },
        
        /* -------------------- Event Listener ----------------------- */
        
        onEditTable: function (evt) {
            evt.preventDefault();
            this.model.trigger('edit', this.model);
            evt.stopPropagation();
        },

        onDeleteTable: function (evt) {
            evt.preventDefault();
            this.model.trigger('delete', this.model);
            evt.stopPropagation();
        }
        
        
        
    });
    
    return TableItem;
    
});
