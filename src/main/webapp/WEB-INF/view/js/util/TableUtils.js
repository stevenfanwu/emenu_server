/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    
    var BaseUtils = require('./BaseUtils');
    var $ = require('../lib/jquery');

    var TableUtils = BaseUtils.extend({
        changeTable: function (options) {
            $.ajax({
                url: '/api/tables/change',
                type: 'PUT',
                data: options.data
            }).done(function (dish) {
                options.success.call(this, dish);
            });
        }
    });
    
    var Utils = new TableUtils();
    
    return Utils;
});

