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
        },

        soldoutDish: function (options) {
            var url = '/api/dishes/' + options.id + '/soldout';
            $.ajax({
                url: url,
                type: 'PUT'
            }).error(function () {
                options.error.apply(this, arguments);
            });
        },

        unsoldoutDish: function (options) {
            var url = '/api/dishes/' + options.id + '/unsoldout';
            $.ajax({
                url: url,
                type: 'PUT'
            }).error(function () {
                options.error.apply(this, arguments);
            });
        },

        unsoldoutAllDish: function (options) {
            var url = '/api/dishes/unsoldout';
            $.ajax({
                url: url,
                type: 'PUT'
            }).done(function () {
                options.success.apply(this, arguments);
            });
        },

        moveDownChapter: function (options) {
            this.moveChapter(options, false);
        },

        moveUpChapter: function (options) {
            this.moveChapter(options, true);
        },

        moveChapter: function (options, up) {
            var url = '/api/chapters/' + options.chapterId + '/'
                + (up ? 'up' : 'down');
            $.ajax({
                url: url,
                type: 'PUT'
            }).done(function () {
                options.success.apply(this, arguments);
            });
        }
    });

    var Utils = new MenuUtils();
    
    return Utils;
    
});

