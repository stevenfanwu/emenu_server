/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Tr = require('./Tr');
    var UserType = require('../../misc/Const').UserType;

    var UserItem = Tr.extend({
        tmpl: require('./UserItem.handlebars'),

        events: {
            'click .btn-edit-user': 'onEditUser',
            'click .btn-delete-user': 'onDeleteUser',
            'click .btn-mod-pw': 'onModPw'
        },

        getRenderData: function () {
            var data = Tr.prototype.getRenderData.apply(this, arguments);
            data.typeLabel = UserType.getLabel(data.type);
            var loginUser = this.getCurrentUser();
            if (loginUser.type > UserType.USER.value
                    && loginUser.type >= data.type) {
                data.showOp = true;
            } else {
                data.showOp = false;
            }
            data.isMe = loginUser.id === data.id;
            return data;
        },

        
        /* -------------------- Event Listener ----------------------- */
        
        onEditUser: function (evt) {
            evt.preventDefault();
            this.model.trigger('edit', this.model);
            evt.stopPropagation();
        },
        
        onModPw: function (evt) {
            evt.preventDefault();
            this.model.trigger('modPw', this.model);
            evt.stopPropagation();
        },

        onDeleteUser: function (evt) {
            evt.preventDefault();
            this.model.trigger('delete', this.model);
            evt.stopPropagation();
        }
        
        
    });
    
    return UserItem;
    
});

