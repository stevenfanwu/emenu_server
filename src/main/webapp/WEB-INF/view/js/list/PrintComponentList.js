/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    
    var PrintComponentList = BaseTable.extend({
        heads: ['名称', '操作'],

        ItemType: require('./item/PrintComponentItem'),

        CollectionType: require('../collection/PrintComponentCollection'),

        initialize: function () {
            BaseTable.prototype.initialize.apply(this, arguments);
        
            this.on('createComponent', function () {
            }, this);
        }
        
    });

    return PrintComponentList;
});

