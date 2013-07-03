/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseList = require('./BaseList');

    var TableList = BaseList.extend({
        CollectionType: require('../collection/TableCollection'),

        showAll: function () {
        },

        showRoom: function () {
        },

        showHall: function () {
        },

        showBooth: function () {
        }
    });
    
    return TableList;
    
});

