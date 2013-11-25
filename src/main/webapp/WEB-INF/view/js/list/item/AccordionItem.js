/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');
    var $ = require('../../lib/jquery');

    var Accordion = BaseItem.extend({
        className: 'accordion-group',

        $toggleButton: null,

        $collapseView: null,

        render: function () {
            BaseItem.prototype.render.apply(this, arguments);
        
            //TODO how to select the top items?
            this.$toggleButton = $(this.$('.toggle-botton')[0]);
            this.$collapseView = $(this.$('.collapse-view')[0]);

            this.$toggleButton.click(this.onToggle.bind(this));
        },

        isExpanded: function () {
            return this.$collapseView.hasClass('in');
        },

        expand: function () {
            if (!this.isExpanded()) {
                this.$collapseView.collapse('show');
            }
        },
        
        /* -------------------- Event Listener ----------------------- */
        
        onToggle: function (evt) {
            evt.preventDefault();
            this.$collapseView.collapse('toggle');
            evt.stopPropagation();
        }
        
    });
    
    return Accordion;
    
});

