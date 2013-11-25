/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BasePage = require('./BasePage');
    var $ = require('../lib/jquery');

    var Lisence = BasePage.extend({
        
        initialize: function () {
            BasePage.prototype.initialize.apply(this, arguments);

            $('.fileupload').fileupload({
                name: "lisence"
            });
        }
    });
    
    return Lisence;
    
});

