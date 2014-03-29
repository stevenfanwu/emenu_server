/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";
    
    var BaseTable = require('./BaseTable');
    var Dialog = require('../dialog/EditPrinterConfigDialog');

    var PrinterConfigList = BaseTable.extend({
        heads: ['Name', 'Acton'],
        
        ItemType: require('./item/PrinterConfigItem'),

        CollectionType: require('../collection/PrinterConfigCollection'),

        initItem: function (model, item) {
            BaseTable.prototype.initItem.apply(this, arguments);

            item.on('edit', function () {
                this.showDialog(model);
            }, this);
        },

        showDialog: function (model) {
            var dialog = new Dialog({
                model: model
            });
            dialog.show();
        }

    });
    
    return PrinterConfigList;
    
});
