/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    return {
        UserType: {
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
            },

            getLabel: function (value) {
                var label = null;
                Object.keys(this).forEach(function (key) {
                    if (typeof this[key] === 'object'
                            && this[key].value === value) {
                        label = this[key].label;
                    }
                }.bind(this));

                return label;
            }
        }
    };
});

