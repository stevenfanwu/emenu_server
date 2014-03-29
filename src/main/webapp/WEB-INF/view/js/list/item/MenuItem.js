/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";
    
    var AccordionItem = require('./AccordionItem');
    var ChapterModel = require('../../model/ChapterModel');
    var $ = require('../../lib/jquery');
    var _ = require('../../lib/underscore');
    var ChapterList = require('../ChapterList');
    var ChapterCollection = require('../../collection/ChapterCollection');

    var MenuItem = AccordionItem.extend({

        events: _.defaults({
            'click .btn-edit-menu': 'onEditMenu',
            'click .btn-delete-menu': 'onDeleteMenu',
            'click .btn-add-chapter': 'onAddChapter'
        }, AccordionItem.prototype.events),

        tmpl: require('./MenuItem.handlebars'),

        showEditChapterDialog: function (chapterModel) {
            var Dialog = require('../../dialog/EditChapterDialog');
            var dialog = new Dialog({
                model: chapterModel
            });
            dialog.model.on('saved', function () {
                if (!this.createListIfNecessary()) {
                    this.list.refresh();
                }
                this.expand();
            }, this);
            dialog.show();
        },

        createListIfNecessary: function () {
            if (!this.list) {
                this.list = new ChapterList({
                    parentId: this.model.get('id')
                });
                this.list.collection.on('edit', this.onEditChapter, this);
                this.list.collection.on('delete', this.onDeleteChapter, this);
                this.list.render();
                this.$('.wrap-chapter').html(this.list.el);
                return true;
            }
            return false;
        },

        /* -------------------- Event Listener ----------------------- */
        
        onMouseEnter: function (evt) {
            evt.preventDefault();
            $(evt.currentTarget).tooltip('show');
            evt.stopPropagation();
        },

        onDeleteMenu: function (evt) {
            evt.preventDefault();
            this.model.trigger('delete', this.model);
            evt.stopPropagation();
        },

        onEditMenu: function (evt) {
            evt.preventDefault();
            this.model.trigger('edit', this.model);
            evt.stopPropagation();
        },

        onAddChapter: function (evt) {
            evt.preventDefault();
            var chapterModel = new ChapterModel();
            chapterModel.set('menuId', this.model.get('id'));
            this.showEditChapterDialog(chapterModel);
            evt.stopPropagation();
        },

        onEditChapter: function (chapterModel) {
            this.showEditChapterDialog(chapterModel);
        },

        onDeleteChapter: function (chapterModel) {
            if (window.confirm('Delete category "' + chapterModel.get('name') + '"?')) {
                chapterModel.destroy({
                    success: function () {
                        this.list.refresh();
                    }.bind(this)
                });
            }
        },

        onToggle: function () {
            AccordionItem.prototype.onToggle.apply(this, arguments);
            this.createListIfNecessary();
        }
        
    });
    
    return MenuItem;
    
});
