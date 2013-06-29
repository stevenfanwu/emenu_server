/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BasePage = require('./BasePage');
    var UserRouter = require('../router/UserRouter');
    var Const = require('../misc/Const');
    var UserList = require('../list/UserList');
    
    var User = BasePage.extend({
        RouterType: UserRouter,

        mode: Const.UserType.USER,
        
        initEvents: function () {
            BasePage.prototype.initEvents.apply(this, arguments);
        
            this.on('showAll', this.showAll);
            this.on('showAdmin', this.showAdmin);
            this.on('showUser', this.showUser);
        },

        initialize: function () {
            BasePage.prototype.initialize.apply(this, arguments);
        
            this.list = new UserList({
                el: this.$('.wrap-user-list')
            });
        },

        render: function () {
            this.list.render();
        },

        showAll: function () {
            this.render();
        },

        showAdmin: function () {
        },

        showUser: function () {
        }
        
    });

    return User;
});

