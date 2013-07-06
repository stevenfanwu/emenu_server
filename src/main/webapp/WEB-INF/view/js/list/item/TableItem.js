/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');
    var Const = require('../../misc/Const');

    var TableItem = BaseItem.extend({
        tmpl: require('./TableItem.handlebars'),

        getRenderData: function () {
            var data = BaseItem.prototype.getRenderData.apply(this, arguments);
            data.typeLabel = Const.TableType.getLabel(data.type);
            data.shapeLabel = Const.TableShape.getLabel(data.shape);
            data.minChargeLabel = data.minCharge === 0 ? '无' : data.minCharge;
            data.tipModeLabel = Const.TipMode.getLabel(data.tipMode);
            if (data.tipMode === Const.TipMode.NONE.value) {
                data.tipLabel = '无';
            } else if (data.tipMode === Const.TipMode.PERCENTAGE.value) {
                data.tipLabel = data.tip + '%';
            } else {
                data.tipLabel = data.tip;
            }
            return data;
        }
        
        
    });
    
    return TableItem;
    
});
