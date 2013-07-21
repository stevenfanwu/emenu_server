/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var AccordionItem = require('./AccordionItem');
    var MenuPageList = require('../MenuPageList');

    var ChapterItem = AccordionItem.extend({

        events: {
            'click .btn-edit-chapter': 'onEditChapter',
            'click .btn-delete-chapter': 'onDeleteChapter',
            'click .btn-add-page': 'onAddPage'
        },

        tmpl: require('./ChapterItem.handlebars'),

        createListIfNecessary: function () {
            if (!this.list) {
                this.list = new MenuPageList({
                    parentId: this.model.get('id')
                });
                this.list.render();
                this.$('.wrap-page').html(this.list.el);
                return true;
            }
            return false;
        },

        /* -------------------- Event Listener ----------------------- */
        
        onEditChapter: function (evt) {
            evt.preventDefault();
            this.model.trigger('edit', this.model);
            evt.stopPropagation();
        },

        onDeleteChapter: function (evt) {
            evt.preventDefault();
            this.model.trigger('delete', this.model);
            evt.stopPropagation();
        },

        onAddPage: function (evt) {
            evt.preventDefault();
            var Dialog = require('../../dialog/EditMenuPageDialog');
            var MenuPageModel = require('../../model/MenuPageModel');
            var pageModel = new MenuPageModel({});
            pageModel.set('chapterId', this.model.get('id'));
            var dialog = new Dialog({
                model: pageModel
            });
            dialog.model.on('saved', function () {
                if (!this.createListIfNecessary()) {
                    this.list.refresh();
                }
                this.expand();
            }, this);
            dialog.show();
            evt.stopPropagation();
        },

        onToggle: function () {
            AccordionItem.prototype.onToggle.apply(this, arguments);
            this.createListIfNecessary();
        }
        
        
    });
    
    return ChapterItem;
    
});

