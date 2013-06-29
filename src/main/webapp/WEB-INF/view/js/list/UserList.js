/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseList = require('./BaseList');
    var UserCollection = require('../collection/UserCollection');
    var UserItem = require('./item/UserItem');
    
    var UserList = BaseList.extend({
        heads: ['登陆名', '真实姓名', '备注', '类型'],

        CollectionType: UserCollection,

        ItemType: UserItem
    });
    
    return UserList;
    
});

