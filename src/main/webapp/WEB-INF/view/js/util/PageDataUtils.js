/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseUtils = require('./BaseUtils');
    var $ = require('../lib/jquery');
    var _ = require('../lib/underscore');
    
    var PageDataUtils = BaseUtils.extend({
        getData: function (name) {
            var data = document.getElementById(name + 'Data');
            if (!data) {
                return null;
            }

            return JSON.parse($(data).html());
        }
    });
    var Utils = new PageDataUtils();
    Utils.getData = _.memoize(Utils.getData);
    Utils.getLoginUser = Utils.getData.bind(Utils, 'loginUser');

    return Utils;
});

