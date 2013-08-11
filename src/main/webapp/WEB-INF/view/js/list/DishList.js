/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    var Const = require('../misc/Const');

    var DishList = BaseTable.extend({
        query: null,

        heads: ['名称', '价格', '会员价', '单位', '辣度', '允许小数份', '特价菜', '菜品描述', '已加入菜单', '操作'],

        ItemType: require('./item/DishItem'),

        CollectionType: require('../collection/DishCollection'),

        filterModel: function (model) {
            if (this.query) {
                return model.get('name').indexOf(this.query) !== -1;
            }
            return true;
        },
        
        
        /* -------------------- Event Listener ----------------------- */
        
        onQuery: function (query) {
            if (this.query !== query) {
                this.query = query;
                this.render();
            }
        }

    });
    
    return DishList;
});

