/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    var Const = require('../misc/Const');
    var TableType = Const.TableType;

    var Mode = {
        ALL: 0,
        ROOM: 1,
        HALL: 2,
        BOOTH: 3
    };

    var TableList = BaseTable.extend({
        heads: ['桌名', '类型', '桌型', '最多人数', '最低消费', '服务费收取模式', '服务费',
            '操作'],

        mode: Mode.ALL,

        ItemType: require('./item/TableItem'),

        CollectionType: require('../collection/TableCollection'),

        filterModel: function (model) {
            BaseTable.prototype.filterModel.apply(this, arguments);
            if (this.mode === Mode.ALL) {
                return true;
            }
            if (this.mode === Mode.ROOM) {
                return model.get('type') === TableType.ROOM.value;
            }
            if (this.mode === Mode.HALL) {
                return model.get('type') === TableType.HALL.value;
            }
            return model.get('type') === TableType.BOOTH.value;
        },

        showAll: function () {
            this.mode = Mode.ALL;
            this.render();
        },

        showRoom: function () {
            this.mode = Mode.ROOM;
            this.render();
        },

        showHall: function () {
            this.mode = Mode.HALL;
            this.render();
        },

        showBooth: function () {
            this.mode = Mode.BOOTH;
            this.render();
        }
    });
    
    return TableList;
    
});

