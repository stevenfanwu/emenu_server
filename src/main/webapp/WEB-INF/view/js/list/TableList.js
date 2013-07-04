/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseList = require('./BaseList');

    var Mode = {
        ALL: 0,
        ROOM: 1,
        HALL: 2,
        BOOTH: 3
    };

    var TableList = BaseList.extend({
        heads: ['桌名', '类型', '桌型', '最多人数', '最低消费', '服务费收取模式', '服务费',
            '操作'],

        mode: Mode.ALL,

        CollectionType: require('../collection/TableCollection'),

        showAll: function () {
            this.mode = Mode.ALL;
            this.render();
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

