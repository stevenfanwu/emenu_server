/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseModel = require('./BaseModel');
    var TableModel = require('./TableModel');
    var DishCollection = require('../collection/DishCollection');

    var OrderModel = BaseModel.extend({
        urlRoot: '/api/orders',

        tableModel: new TableModel(),

        dishCollection: new DishCollection(),

        initialize: function () {
            BaseModel.prototype.initialize.apply(this, arguments);
            this.tableModel.set(this.get('table'));
            this.dishCollection.reset(this.get('dishes'));
        }
        
    });
    
    return OrderModel;
});

