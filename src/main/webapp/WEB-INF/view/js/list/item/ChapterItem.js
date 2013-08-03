/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var AccordionItem = require('./AccordionItem');
    var MenuPage = require('../../component/MenuPage');

    var ChapterItem = AccordionItem.extend({

        events: {
            'click .btn-edit-chapter': 'onEditChapter',
            'click .btn-delete-chapter': 'onDeleteChapter',
            'click .btn-add-page': 'onAddPage'
        },

        tmpl: require('./ChapterItem.handlebars'),

        renderPage: function (options) {
            options = options || {};
            if (!this.menuPage || options.reset) {
                delete options.reset;
                options.parentId = this.model.get('id');
                this.menuPage = new MenuPage(options);
                this.menuPage.render();
                this.$('.page-wrap').html(this.menuPage.el);
            }
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
                var options = {
                    reset: true,
                    menuPageId: pageModel.get('id')
                };
                this.renderPage(options);
                this.expand();
            }, this);
            dialog.show();
            evt.stopPropagation();
        },

        onToggle: function () {
            AccordionItem.prototype.onToggle.apply(this, arguments);
            this.renderPage();
        }
        
        
    });
    
    return ChapterItem;
    
});

