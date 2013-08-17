/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var TabPage = require('./TabPage');
    var StatList = require('../list/StatList');

    var Stat = TabPage.extend({
        RouterType: require('../router/StatRouter'),
        
        tabEl: ['.tab-stat'],

        initEvents: function () {
            TabPage.prototype.initEvents.apply(this, arguments);
            this.on('showStat', function () {
                this.emptyPullRightTab();
                this.activeTab('.tab-stat');
                var list = new StatList();
                list.render();
                this.$('.bottom-content').empty();
                this.$('.bottom-content').append(list.el);
            }, this);
        }
    });
    
    return Stat;
    
});

