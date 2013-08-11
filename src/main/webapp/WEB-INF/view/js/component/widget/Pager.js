/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseView = require('../../BaseView');
    var $ = require('../../lib/jquery');

    var Pager = BaseView.extend({
        tmpl: require('./Pager.handlebars'),

        events: {
            'click .page-item': 'onPageClick',
            'click .btn-go': 'onPageGo'
        },

        currentIndex: 1,
        
        collection: null,

        getRenderData: function () {
            var data = BaseView.prototype.getRenderData.apply(this, arguments);
        
            data.maxIndex = this.collection.length;
            data.lastIndex = this.currentIndex - 1;
            data.nextIndex = this.currentIndex + 1;
            data.hasLast = data.lastIndex > 0;
            data.hasNext = data.nextIndex <= data.maxIndex;
            data.currentIndex = this.currentIndex;

            var pagers = [];
            var i = 1;
            var walk = function (end) {
                while (i <= data.maxIndex && i <= end) {
                    pagers.push({
                        isActive: i === this.currentIndex,
                        isNormal: i !== this.currentIndex,
                        index: i
                    });
                    i = i + 1;
                }
            }.bind(this);
            walk(3);
            if (this.currentIndex - 1 > i) {
                pagers.push({
                    isDot: true
                });
                i = this.currentIndex - 1;
            }
            walk(this.currentIndex + 1);
            if (i < data.maxIndex - 2) {
                pagers.push({
                    isDot: true
                });
                i = data.maxIndex - 2;
            }
            walk(data.maxIndex);

            data.pagers = pagers;
            return data;
            
        },
        
        /* -------------------- Event Listener ----------------------- */
        
        onPageClick: function (evt) {
            evt.preventDefault();
            var index = parseInt($(evt.target).attr('value'), 10);
            if (index !== this.currentIndex && index >= 1 && index <= this.collection.length) {
                this.trigger('pager', index);
            }
            evt.stopPropagation();
        },

        onPageGo: function (evt) {
            evt.preventDefault();
            var value = this.$('.input-page').val();
            var index = parseInt(value, 10);
            if (String(index) === String(value)) {
                this.trigger('pager', index);
            }
            evt.stopPropagation();
        }

        
    });
    
    return Pager;
    
});

