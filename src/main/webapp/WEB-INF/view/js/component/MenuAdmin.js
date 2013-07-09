/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseView = require('../BaseView');
    var MenuModel = require('../model/MenuModel');

    var MenuAdmin = BaseView.extend({

        initialize: function () {
            BaseView.prototype.initialize.apply(this, arguments);
        
            this.on('createMenu', this.onCreateMenu, this);
        },

        render: function () {
            BaseView.prototype.render.apply(this, arguments);
        
            if (!this.list) {
                var List = require('../list/MenuList');
                this.list = new List();
            }
            this.list.render();
            this.$el.append(this.list.el);
        },

        
        /* -------------------- Event Listener ----------------------- */
        
        onCreateMenu: function () {
            var Dialog = require('../dialog/EditMenuDialog');
            var dialog = new Dialog({
                model: new MenuModel()
            });
            dialog.model.on('saved', function () {
                this.list.refresh();
            }, this);
            dialog.show();
        }
        
        
    });
    
    return MenuAdmin;
    
});

