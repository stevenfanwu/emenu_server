/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    var RestaurantCollection = require('../collection/RestaurantCollection');
    var RestaurantItem = require('./item/RestaurantItem');
    var $ = require('../lib/jquery');

    var RestaurantList = BaseTable.extend({
      heads: ['Name', 'Restaurant Id', 'Actions'],

        CollectionType: RestaurantCollection,

        ItemType: RestaurantItem,
    
    });

    return RestaurantList;
});

 
