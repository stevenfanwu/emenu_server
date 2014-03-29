/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    var StatModel = require('../model/StatModel');
    
    var StatList = BaseTable.extend({
        heads: ['Date', 'Transtractions', 'Table Turnover Rate', 'Guests Served', 'Averate Spending Per Order', 'Average Spending Per Person', 'Invoice Issued',
            'Total Invoice Amount', 'Discounts', 'Total Serivice Fee', 'Total Sales'],
        
        ItemType: require('./item/StatItem'),

        CollectionType: require('../collection/StatCollection')
    });
    
    return StatList;
    
});

