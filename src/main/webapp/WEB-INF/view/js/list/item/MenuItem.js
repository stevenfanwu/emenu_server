/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";
    
    var BaseItem = require('./BaseItem');
    var ChapterModel = require('../../model/ChapterModel');
    var $ = require('../../lib/jquery');

    var MenuItem = BaseItem.extend({
        events: {
            'mouseenter .hover-tip': 'onMouseEnter',
            'click .btn-toggle-menu': 'onToggleMenu',
            'click .btn-edit-menu': 'onEditMenu',
            'click .btn-delete-menu': 'onDeleteMenu',
            'click .btn-add-chapter': 'onAddChapter'
        },

        tmpl: require('./MenuItem.handlebars'),

        showEditChapterDialog: function (chapterModel) {
            var Dialog = require('../../dialog/EditChapterDialog');
            var dialog = new Dialog({
                model: chapterModel
            });
            dialog.model.on('saved', function () {
                this.list.refresh();
            }, this);
            dialog.show();
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
            this.showEditChapterDialog(new ChapterModel());
            evt.stopPropagation();
        },
        
        onToggleMenu: function (evt) {
            evt.preventDefault();
            evt.stopPropagation();
        }
    });
    
    return MenuItem;
    
});

