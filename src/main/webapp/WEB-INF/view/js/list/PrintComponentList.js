/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    var Dialog = require('../dialog/EditPrintComponentDialog');
    var Model = require('../model/PrintComponentModel');
    
    var PrintComponentList = BaseTable.extend({
        heads: ['名称', '操作'],

        ItemType: require('./item/PrintComponentItem'),

        CollectionType: require('../collection/PrintComponentCollection'),

        initialize: function () {
            BaseTable.prototype.initialize.apply(this, arguments);
        
            this.on('createComponent', function () {
                this.showDialog(new Model());
            }, this);
        },

        showDialog: function (model) {
            var dialog = new Dialog({
                model: model
            });
            dialog.show();
        }
        
    });

    return PrintComponentList;
});

