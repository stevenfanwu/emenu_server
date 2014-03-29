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
            label: 'Server'
        },
        ADMIN: {
            value: 1,
            label: 'Admin'
        },
        SUPER_USER: {
            value: 2,
            label: 'Super Admin'
        }
    });

    exports.TableType = new Const({
        ROOM: {
            value: 0,
            label: 'Room'
        },
        HALL: {
            value: 1,
            label: 'Hall'
        },
        BOOTH: {
            value: 2,
            label: 'Booth'
        }
    });

    exports.TipMode = new Const({
        NONE: {
            value: 0,
            label: 'No Tips'
        },
        FIXED: {
            value: 1,
            label: 'Flat Rate'
        },
        PERCENTAGE: {
            value: 2,
            label: 'Percentage'
        }
    });

    exports.Spicy = new Const({
        NONE: {
            value: 0,
            label: 'No Spicy'
        },
        LOW: {
            value: 1,
            label: 'Mid'
        },
        MID: {
            value: 2,
            label: 'Spicy'
        },
        HIGH: {
            value: 3,
            label: 'Very Spicy'
        }
    });

    exports.DishUnit = new Const({
        JIN: {
            value: 0,
            label: 'N/A'
        },
        FEN: {
            value: 1,
            label: 'Pound'
        },
        GE: {
            value: 2,
            label: 'N/A'
        },
        ZHI: {
            value: 3,
            label: 'N/A'
        },
        PAN: {
            value: 4,
            label: 'Plate'
        },
        PING: {
            value: 5,
            label: 'Bottle'
        },
        BEI: {
            value: 6,
            label: 'Cup'
        },
        LI: {
            value: 7,
            label: 'N/A'
        },
        WEI: {
            value: 8,
            label: 'Per Person'
        }
    });

    exports.Boolean = new Const({
        ONE: {
            value: 1,
            label: 'One'
        },
        ZERO: {
            value: 0,
            label: 'None'
        },
        TRUE: {
            value: true,
            label: 'Yes'
        },
        FALSE: {
            value: false,
            label: 'No'
        }
    });
});

