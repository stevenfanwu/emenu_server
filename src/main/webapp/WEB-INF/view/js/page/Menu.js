/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var TabPage = require('./TabPage');
    var MenuRouter = require('../router/MenuRouter');
    var MenuModel = require('../model/MenuModel');

    var Menu = TabPage.extend({
        RouterType: MenuRouter,

        tabEl: ['.tab-dish', '.tab-menu', '.tab-taste'],

        initEvents: function () {
            TabPage.prototype.initEvents.apply(this, arguments);

            this.on('showDish', function () {
                this.emptyPullRightTab();
                this.activeTab('.tab-dish');
                var DishAdmin = require('../component/DishAdmin');
                var view = new DishAdmin();
                view.render();
                this.$('.bottom-content').empty();
                this.$('.bottom-content').append(view.el);
            }, this);
            this.on('showMenu', function () {
                this.activeTab('.tab-menu');
                var tab = this.appendPullRight('添加菜单');
                tab.on('click', function () {
                    var Dialog = require('../dialog/EditMenuDialog');
                    var dialog = new Dialog({
                        model: new MenuModel()
                    });
                    dialog.model.on('saved', function () {
                        this.list.refresh();
                    }, this);
                    dialog.show();
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

