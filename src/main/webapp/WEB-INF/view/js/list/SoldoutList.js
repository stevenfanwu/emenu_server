/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    var Trie = require('../util/Trie');

    var SoldoutList = BaseTable.extend({
        
        heads: ['菜品', '操作'],

        CollectionType: require('../collection/DishCollection'),

        queryResult: {},

        ItemType: require('./item/SoldoutItem'),
        
        filterModel: function (model) {
            return !this.query || this.queryResult[model.get('id')];
        },

        initialize: function () {
            BaseTable.prototype.initialize.apply(this, arguments);
        
            this.on('clearSoldout', function () {
                this.items.forEach(function (item) {
                    item.setSoldout(false);
                }, this);
            }, this);
        },
        
        doRender: function () {
            BaseTable.prototype.doRender.apply(this, arguments);

            this.trie1 = new Trie({
                getKey: function (model) {
                    return model.get('name');
                }
            });
            this.trie1.build(this.collection);
            this.trie2 = new Trie({
                getKey: function (model) {
                    return model.get('pinyin').replace(/,/g, '').replace(/\s/g, '');
                }
            });
            this.trie2.build(this.collection);
            this.trie3 = new Trie({
                getKey: function (model) {
                    var pinyin = model.get('pinyin').replace(/\s/g, '');
                    var key = '';
                    pinyin.split(',').forEach(function (word) {
                        key = key + word[0];
                    });
                    return key;
                }
            });
            this.trie3.build(this.collection);
        },
        
        /* -------------------- Event Listener ----------------------- */
        
        onQuery: function (query) {
            query = query || '';
            query = query.replace(/\s/g, '');
            if (this.query !== query) {
                this.query = query;
                this.queryResult = {};
                this.trie1.search(query).forEach(function (model) {
                    this.queryResult[model.get('id')] = true;
                }, this);
                this.trie2.search(query).forEach(function (model) {
                    this.queryResult[model.get('id')] = true;
                }, this);
                this.trie3.search(query).forEach(function (model) {
                    this.queryResult[model.get('id')] = true;
                }, this);
                this.render();
            }
        }
    });
    
    return SoldoutList;
    
});

