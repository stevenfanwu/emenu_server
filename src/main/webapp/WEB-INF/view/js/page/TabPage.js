/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BasePage = require('./BasePage');
    
    var TabPage = BasePage.extend({
        
        tabEl: [],

        activeTab: function (tab) {
            //TODO tab animation
            this.tabEl.forEach(function (t) {
                if (t === tab) {
                    this.$(t).addClass('active');
                } else {
                    this.$(t).removeClass('active');
                }
            }, this);
        },

        emptyPullRightTab: function () {
            this.$('.li-right').empty();
        },

        appendPullRight: function (label) {
            this.emptyPullRightTab();
            var PullRightTab = require('../component/widget/PullRightTab');
            var tab = new PullRightTab();
            tab.label = label;
            tab.render();
            this.$('.li-right').append(tab.el);
            return tab;
        }
    });
    
    return TabPage;
    
});

