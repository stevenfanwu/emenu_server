/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');

    var SoldoutList = BaseTable.extend({
        
        heads: ['菜品', '操作'],

        CollectionType: require('../collection/DishCollection'),

        ItemType: require('./item/SoldoutItem'),
        
        filterModel: function (model) {
            if (this.query) {
                return model.get('name').indexOf(this.query) !== -1;
            }
            return true;
        },

        initialize: function () {
            BaseTable.prototype.initialize.apply(this, arguments);
        
            this.on('clearSoldout', function () {
                this.items.forEach(function (item) {
                    item.setSoldout(false);
                }, this);
            }, this);
        },
        
        
        /* -------------------- Event Listener ----------------------- */
        
        onQuery: function (query) {
            if (this.query !== query) {
                this.query = query;
                this.render();
            }
        }
    });
    
    return SoldoutList;
    
});

