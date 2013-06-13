/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var $ = require('../lib/jquery');
    var BasePage = require('./BasePage');
    
    var HomePage = BasePage.extend({
        initialize: function () {
            BasePage.prototype.initialize.apply(this, arguments);
        
            var UploadForm = require('../form/UploadForm');
            this.form = new UploadForm({
                el: '.form-upload'
            });
        },
        
    });

    return HomePage;
});

