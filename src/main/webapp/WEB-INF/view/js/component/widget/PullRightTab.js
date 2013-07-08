/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseView = require('../../BaseView');


    var PullRightTab = BaseView.extend({
        tmpl: require('./PullRightTab.handlebars'),

        events: {
            'click a': 'onClick'
        },

        label: null,

        getRenderData: function () {
            var data = BaseView.prototype.getRenderData.apply(this, arguments);
            data.label = this.label;
            return data;
        },

        
        /* -------------------- Event Listener ----------------------- */
        
        onClick: function (evt) {
            evt.preventDefault();
            this.trigger('click');
            evt.stopPropagation();
        }
    });
    
    return PullRightTab;
    
    
});

