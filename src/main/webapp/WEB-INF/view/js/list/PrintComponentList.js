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

        initItem: function (model, item) {
            BaseTable.prototype.initItem.apply(this, arguments);

            item.on('edit', function () {
                this.showDialog(model);
            }, this);

            item.on('delete', function () {
                if (window.confirm('确定删除"' + model.get('name') + '"?')) {
                    model.destroy({
                        success: function () {
                            this.refresh();
                        }.bind(this)
                    });
                }
            }, this);
        },

        showDialog: function (model) {
            var dialog = new Dialog({
                model: model
            });
            dialog.model.on('saved', function () {
                this.refresh();
            }, this);
            dialog.show();
        }
        
    });

    return PrintComponentList;
});

