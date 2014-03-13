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
        initEvents: function () {
            BasePage.prototype.initEvents.apply(this, arguments);
        
            this.list.collection.on('edit', this.onRestaurant, this);
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
            console.log('onEditRestaurant');
        },

 
        initialize: function () {
          console.log('from super user.');
          var restaurantList = new RestaurantList({
                el: this.$('.wrap-list')
          });
          restaurantList.render();
          this.list = restaurantList;
        }
    });
    return SuperAdmin;
});


