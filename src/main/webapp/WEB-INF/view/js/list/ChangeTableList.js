/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseList = require('./BaseList');
    var TableCollection = require('../collection/TableCollection');
    var ChangeTableItem = require('./item/ChangeTableItem');

    var ChangeTableList = BaseList.extend({
        CollectionType: TableCollection,

        tagName: 'ul',

        className: 'thumbnails change-table-dialog-wrap',

        ItemType: ChangeTableItem,

        preProcessCollection: function (collection) {
            BaseList.prototype.preProcessCollection.apply(this, arguments);

            var emptyIndex = 0;
            var occupiedIndex = collection.length;

            var tables = collection.sortBy(function (model) {
                if (model.get('status')) {
                    occupiedIndex = occupiedIndex + 1;
                    return occupiedIndex;
                }
                emptyIndex = emptyIndex + 1;
                return emptyIndex;
            }, this);

            this.collection.reset(tables);

        },

        initItem: function (model, item) {
            BaseList.prototype.initItem.apply(this, arguments);
        
            item.on('select', function (tableModel) {
                this.trigger('select', tableModel);
            }, this);
        }
        

    });
    
    return ChangeTableList;
    
});

