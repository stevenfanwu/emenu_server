/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseCollection = require('./BaseCollection');
    var UserModel = require('../model/UserModel');

    var UserCollection = BaseCollection.extend({
        url: '/api/users',

        model: UserModel
    });
    
    return UserCollection;
});

