/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BasePage = require('./BasePage');
    var RestaurantModel = require('../model/RestaurantModel');
    var UserModel = require('../model/UserModel');
    var RestaurantList = require('../list/RestaurantList');
    var SuperAdmin = BasePage.extend({

        events: {
            'click .btn-create-user': 'onCreateUser',
            'click .btn-create-restaurant': 'onCreateRestaurant',
      
        },

        initialize: function () {
          var restaurantList = new RestaurantList({
                el: this.$('.wrap-list')
          });
          this.list = restaurantList;
          this.list.render();
          BasePage.prototype.initialize.apply(this, arguments);
          console.log('init called in SuperAdmin');
        },

        render: function() {
            console.log('render called in SuperAdmin');
            this.list.render();
        },

        initEvents: function () {
            BasePage.prototype.initEvents.apply(this, arguments);
        
            this.list.collection.on('edit', this.onEditRestaurant, this);
            this.list.collection.on('delete', this.onDeleteRestaurant, this);
        },

        onCreateUser: function(evt) {
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

        onCreateRestaurant: function(evt) {
            evt.preventDefault();
            var Dialog = require('../dialog/EditRestaurantDialog');
            var dialog = new Dialog({
                model: new RestaurantModel()
            });
            dialog.model.on('saved', function () {
                this.list.refresh();
            }, this);
            dialog.show();
        },

        onEditRestaurant: function(model) {
            // TODO(Nicholas): add update 
            var Dialog = require('../dialog/EditRestaurantDialog');
            var dialog = new Dialog({
                model: model 
            });
            dialog.model.on('saved', function () {
                this.list.refresh();
            }, this);
            dialog.show();
        },

        onDeleteRestaurant: function(model) {
            if (window.confirm('Delete restaurant ' + model.get('name') + '?')) {
                model.destroy({
                    success: function () {
                        this.list.refresh();
                    }.bind(this)
                });
            }
        }
    });
    return SuperAdmin;
});


