/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseTable = require('./BaseTable');
    var UserCollection = require('../collection/UserCollection');
    var UserItem = require('./item/UserItem');
    var UserType = require('../misc/Const').UserType;
    var $ = require('../lib/jquery');

    var Mode = {
        ALL: 0,
        ADMIN: 1,
        USER: 2
    };

    var UserList = BaseTable.extend({
        heads: ['User Name', 'Real Name', 'Note', 'Type', 'Operation'],

        mode: Mode.ALL,

        CollectionType: UserCollection,

        ItemType: UserItem,

        filterModel: function (model) {
            BaseTable.prototype.filterModel.apply(this, arguments);
            if (this.mode === Mode.ALL) {
                return true;
            }
            if (this.mode === Mode.ADMIN) {
                return model.get('type') !== UserType.USER.value;
            }
            return model.get('type') === UserType.USER.value;
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

