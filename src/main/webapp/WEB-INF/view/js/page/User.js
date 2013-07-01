/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BasePage = require('./BasePage');
    var UserRouter = require('../router/UserRouter');
    var UserList = require('../list/UserList');
    var UserModel = require('../model/UserModel');
    var $ = require('../lib/jquery');
    
    var User = BasePage.extend({
        RouterType: UserRouter,

        events: {
            'click .btn-create-user': 'onCreateUser'
        },
        
        initEvents: function () {
            BasePage.prototype.initEvents.apply(this, arguments);
        
            this.on('showAll', function () {
                this.list.showAll();
                this.$('.tab-all').addClass('active');
                this.$('.tab-admin').removeClass('active');
                this.$('.tab-user').removeClass('active');
            }.bind(this));
            this.on('showAdmin', function () {
                this.list.showAdmin();
                this.$('.tab-admin').addClass('active');
                this.$('.tab-all').removeClass('active');
                this.$('.tab-user').removeClass('active');
            }.bind(this));
            this.on('showUser', function () {
                this.list.showUser();
                this.$('.tab-user').addClass('active');
                this.$('.tab-admin').removeClass('active');
                this.$('.tab-all').removeClass('active');
            }.bind(this));
        },

        initialize: function () {
            this.list = new UserList({
                el: this.$('.wrap-user-list')
            });
            BasePage.prototype.initialize.apply(this, arguments);
        },

        render: function () {
            this.list.render();
        },

        
        /* -------------------- Event Listener ----------------------- */
        
        onCreateUser: function (evt) {
            evt.preventDefault();
            var Dialog = require('../dialog/CreateUserDialog');
            var dialog = new Dialog({
                model: new UserModel()
            });
            dialog.model.on('saved', function () {
                this.list.refresh();
            }, this);
            dialog.show();
        }
    });

    return User;
});

