/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');
    var UserType = require('../../misc/Const').UserType;

    var UserItem = BaseItem.extend({
        tmpl: require('./UserItem.handlebars'),

        getRenderData: function () {
            var data = BaseItem.prototype.getRenderData.apply(this, arguments);
            data.typeLabel = UserType.getLabel(data.type);
            var loginUser = this.getCurrentUser();
            if (loginUser.type > UserType.USER.value
                && loginUser.type >= data.type) {
                data.showOp = true;
            } else {
                data.showOp = false;
            }
            return data;
        }
        
    });
    
    return UserItem;
    
});

