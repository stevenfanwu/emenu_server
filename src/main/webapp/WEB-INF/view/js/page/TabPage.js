/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BasePage = require('./BasePage');
    
    var TabPage = BasePage.extend({
        
        tabEl: [],

        activeTab: function (tab) {
            this.tabEl.forEach(function (t) {
                if (t === tab) {
                    this.$(t).addClass('active');
                } else {
                    this.$(t).removeClass('active');
                }
            }, this);
        }
    });
    
    return TabPage;
    
});

