/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Tr = require('./Tr');
    var MenuUtils = require('../../util/MenuUtils');

    var SoldoutItem = Tr.extend({
        
        tmpl: require('./SoldoutItem.handlebars'),

        events: {
            'switch-change .make-switch': 'onSwitchChange'
        },

        initialize: function () {
            Tr.prototype.initialize.apply(this, arguments);
        
            this.on('soldout', this.onSoldoutChange, this);
        },

        ignoreEvent: false,

        render: function () {
            Tr.prototype.render.apply(this, arguments);
            this.$('.make-switch').bootstrapSwitch();
        },

        isSoldout: function () {
            return !this.$('.make-switch').bootstrapSwitch('status');
        },

        setSoldout: function (soldout) {
            this.ignoreEvent = true;
            this.$('.make-switch').bootstrapSwitch('setState', !soldout);
            this.ignoreEvent = false;
        },
        
        /* -------------------- Event Listener ----------------------- */
        
        onSwitchChange: function (e, data) {
            if (this.ignoreEvent) {
                return;
            }
            var soldout = !data.value;
            this.onSoldoutChange(soldout);
        },

        onSoldoutChange: function (soldout) {
            if (soldout) {
                MenuUtils.soldoutDish({
                    id: this.model.get('id'),
                    error: function () {
                        this.setSoldout(false);
                    }.bind(this)
                });
            } else {
                MenuUtils.unsoldoutDish({
                    id: this.model.get('id'),
                    error: function () {
                        this.setSoldout(true);
                    }.bind(this)
                });
            }
        }
        
    });
    
    return SoldoutItem;
    
});

