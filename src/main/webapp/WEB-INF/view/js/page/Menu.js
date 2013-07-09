/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var TabPage = require('./TabPage');
    var MenuRouter = require('../router/MenuRouter');

    var Menu = TabPage.extend({
        RouterType: MenuRouter,

        tabEl: ['.tab-dish', '.tab-menu', '.tab-taste'],

        initEvents: function () {
            TabPage.prototype.initEvents.apply(this, arguments);

            this.on('showDish', function () {
                //TODO 抽取
                this.emptyPullRightTab();
                this.activeTab('.tab-dish');
                var DishAdmin = require('../component/DishAdmin');
                var dishView = new DishAdmin();
                dishView.render();
                this.$('.bottom-content').empty();
                this.$('.bottom-content').append(dishView.el);
            }, this);
            this.on('showMenu', function () {
                this.activeTab('.tab-menu');
                var DishMenu = require('../component/MenuAdmin');
                var menuView = new DishMenu();
                menuView.render();
                this.$('.bottom-content').empty();
                this.$('.bottom-content').append(menuView.el);
                var tab = this.appendPullRight('添加菜单');
                tab.on('click', function () {
                    menuView.trigger('createMenu');
                }, this);
            }, this);
            this.on('showTaste', function () {
                this.emptyPullRightTab();
                this.activeTab('.tab-taste');
            }, this);
        }
        
    });
    
    return Menu;
    
});

