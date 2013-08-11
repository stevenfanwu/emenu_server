/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Const = function (options) {
        options = options || {};
        Object.keys(options).forEach(function (key) {
            this[key] = options[key];
        }, this);
    };
    Const.prototype.getLabel = function (value) {
        var label = null;
        Object.keys(this).forEach(function (key) {
            if (typeof this[key] === 'object'
                    && this[key].value === value) {
                label = this[key].label;
            }
        }.bind(this));

        return label;
    };

    exports.UserType = new Const({
        USER: {
            value: 0,
            label: '服务员'
        },
        ADMIN: {
            value: 1,
            label: '管理员'
        },
        SUPER_USER: {
            value: 2,
            label: '超级管理员'
        }
    });

    exports.TableType = new Const({
        ROOM: {
            value: 0,
            label: '包间'
        },
        HALL: {
            value: 1,
            label: '散台'
        },
        BOOTH: {
            value: 2,
            label: '卡座'
        }
    });

    exports.TipMode = new Const({
        NONE: {
            value: 0,
            label: '无服务费'
        },
        FIXED: {
            value: 1,
            label: '固定值'
        },
        PERCENTAGE: {
            value: 2,
            label: '按比例'
        }
    });

    exports.Spicy = new Const({
        NONE: {
            value: 0,
            label: '不辣'
        },
        LOW: {
            value: 1,
            label: '微辣'
        },
        MID: {
            value: 2,
            label: '中辣'
        },
        HIGH: {
            value: 3,
            label: '特辣'
        }
    });

    exports.DishUnit = new Const({
        JIN: {
            value: 0,
            label: '份'
        },
        FEN: {
            value: 1,
            label: '斤'
        }
    });

    exports.Boolean = new Const({
        ONE: {
            value: 1,
            label: '是'
        },
        ZERO: {
            value: 0,
            label: '否'
        },
        TRUE: {
            value: true,
            label: '是'
        },
        FALSE: {
            value: false,
            label: '否'
        }
    });
});

