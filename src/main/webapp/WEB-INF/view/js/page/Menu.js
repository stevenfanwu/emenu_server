/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var TabPage = require('./TabPage');
    var MenuRouter = require('../router/MenuRouter');

    var Menu = TabPage.extend({
        RouterType: MenuRouter,

        tabEl: ['.tab-dish', '.tab-menu', '.tab-dishNote'],

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
                var tab = this.appendPullRight('Add menu');
                tab.on('click', function () {
                    menuView.trigger('createMenu');
                }, this);
                menuView.on('fetched', function (collection) {
                    if (collection.size() > 0) {
                        this.emptyPullRightTab();
                    }
                }, this);
            }, this);
            this.on('showDishNote', function () {
                this.emptyPullRightTab();
                this.activeTab('.tab-dishNote');
                var DishNoteList = require('../list/DishNoteList');
                var list = new DishNoteList();
                list.render();
                this.$('.bottom-content').empty();
                this.$('.bottom-content').append(list.el);
                var tab = this.appendPullRight('Add dish note');
                tab.on('click', function () {
                    list.trigger('createDishNote');
                }, this);
            }, this);
        }
        
    });
    
    return Menu;
    
});

