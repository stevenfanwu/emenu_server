/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var TabList = require('./TabList');
    var UserRouter = require('../router/UserRouter');
    var UserModel = require('../model/UserModel');
    var $ = require('../lib/jquery');
    var _ = require('../lib/underscore');
    
    var User = TabList.extend({
        RouterType: UserRouter,

        ListType: require('../list/UserList'),

        tabEl: ['.tab-all', '.tab-admin', '.tab-user'],

        events: {
            'click .btn-create-user': 'onCreateUser'
        },
        
        initEvents: function () {
            TabList.prototype.initEvents.apply(this, arguments);
        
            this.on('showAll', function () {
                this.list.showAll();
                this.activeTab('.tab-all');
            }.bind(this));
            this.on('showAdmin', function () {
                this.list.showAdmin();
                this.activeTab('.tab-admin');
            }.bind(this));
            this.on('showUser', function () {
                this.list.showUser();
                this.activeTab('.tab-user');
            }.bind(this));
            this.list.collection.on('edit', this.onEditUser, this);
            this.list.collection.on('modPw', this.onModPw, this);
            this.list.collection.on('delete', this.onDeleteUser, this);
        },
        
        /* -------------------- Event Listener ----------------------- */
        
        onCreateUser: function (evt) {
            evt.preventDefault();
            var Dialog = require('../dialog/EditUserDialog');
            var dialog = new Dialog({
                model: new UserModel()
            });
            dialog.model.on('saved', function () {
                this.list.refresh();
            }, this);
            dialog.show();
        },

        onDeleteUser: function (model) {
            if (model.get('id') === this.getCurrentUser().id) {
                window.alert("Can't delte yourself");
                return;
            }
            if (window.confirm('Delete user ' + model.get('name') + '?')) {
                model.destroy({
                    success: function () {
                        this.list.refresh();
                    }.bind(this)
                });
            }
        },
        
        onEditUser: function (model) {
            var Dialog = require('../dialog/EditUserDialog');
            var dialog = new Dialog({
                model: model
            });
            dialog.model.on('saved', function () {
                this.list.refresh();
            }, this);
            dialog.show();
        },
        
        onModPw: function (model) {
            var Dialog = require('../dialog/ModPwDialog');
            var dialog = new Dialog({
                model: model
            });
            dialog.model.on('saved', function () {
                //TODO message
                window.alert('Change saved');
            }, this);
            dialog.show();
        }
    });

    return User;
});

