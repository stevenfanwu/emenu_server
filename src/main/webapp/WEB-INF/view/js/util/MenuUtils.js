/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseUtils = require('./BaseUtils');
    var $ = require('../lib/jquery');

    var MenuUtils = BaseUtils.extend({
        
        /**
         *
         * options = {
         *     data: {
         *         dishId: ,
         *         menuPageId: ,
         *         pos:
         *     },
         *
         *     success: function (dish) {
         *     }
         * }
         *
         * */
        bindDish: function (options) {
            $.ajax({
                url: '/api/menus/bind',
                type: 'PUT',
                data: options.data
            }).done(function (dish) {
                options.success.call(this, dish);
            });
        },

        unbindDish: function (options) {
            $.ajax({
                url: '/api/menus/unbind',
                type: 'PUT',
                data: options.data
            }).done(function (dish) {
                options.success.call(this, dish);
            });
        }
    });

    var Utils = new MenuUtils();
    
    return Utils;
    
});

