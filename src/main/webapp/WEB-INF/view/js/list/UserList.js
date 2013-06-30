/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseList = require('./BaseList');
    var UserCollection = require('../collection/UserCollection');
    var UserItem = require('./item/UserItem');
    var UserType = require('../misc/Const').UserType;
    var $ = require('../lib/jquery');

    var Mode = {
        ALL: 0,
        ADMIN: 1,
        USER: 2
    };

    var UserList = BaseList.extend({
        heads: ['登陆名', '真实姓名', '备注', '类型'],

        mode: Mode.ALL,

        CollectionType: UserCollection,

        ItemType: UserItem,

        filterModel: function (model) {
            BaseList.prototype.filterModel.apply(this, arguments);
            if (this.mode === Mode.ALL) {
                return true;
            } else if (this.mode === Mode.ADMIN) {
                return model.get('type') !== UserType.USER.value;
            } else {
                return model.get('type') === UserType.USER.value;
            }
        },
        
        showAll: function () {
            this.mode = Mode.ALL;
            this.render();
        },
        
        showAdmin: function () {
            this.mode = Mode.ADMIN;
            this.render();
        },
        
        showUser: function () {
            this.mode = Mode.USER;
            this.render();
        }
    });
    
    return UserList;
    
});

