/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    var Dialog = require('../dialog/EditPrintTemplateDialog');
    var Model = require('../model/PrintTemplateModel');
    
    var PrintTemplateList = BaseTable.extend({
        heads: ['Name', 'Action'],

        ItemType: require('./item/PrintTemplateItem'),

        CollectionType: require('../collection/PrintTemplateCollection'),

        initialize: function () {
            BaseTable.prototype.initialize.apply(this, arguments);
        
            this.on('createTemplate', function () {
                this.showDialog(new Model());
            }, this);
        },

        initItem: function (model, item) {
            BaseTable.prototype.initItem.apply(this, arguments);

            item.on('edit', function () {
                this.showDialog(model);
            }, this);

            item.on('delete', function () {
                if (window.confirm('Delete "' + model.get('name') + '"?')) {
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

    return PrintTemplateList;
    
});

