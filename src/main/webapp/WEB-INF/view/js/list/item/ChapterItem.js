/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var AccordionItem = require('./AccordionItem');
    var MenuPage = require('../../component/MenuPage');
    var MenuPageCollection = require('../../collection/MenuPageCollection');
    var MenuUtils = require('../../util/MenuUtils');

    var ChapterItem = AccordionItem.extend({

        events: {
            'click .btn-edit-chapter': 'onEditChapter',
            'click .btn-delete-chapter': 'onDeleteChapter',
            'click .btn-add-page': 'onAddPage',
            'click .btn-move-up-chapter': 'onMoveUp',
            'click .btn-move-down-chapter': 'onMoveDown'
        },

        tmpl: require('./ChapterItem.handlebars'),

        menuPageCollection: null,

        initialize: function () {
            AccordionItem.prototype.initialize.apply(this, arguments);
            this.menuPageCollection = new MenuPageCollection();
        },

        renderPage: function (options) {
            options = options || {};
            if (!this.menuPage || options.reset) {
                delete options.reset;
                options.parentId = this.model.get('id');
                this.menuPage = new MenuPage(options);
                this.menuPage.collection = this.menuPageCollection;
                this.menuPage.render();
                this.$('.page-wrap').html(this.menuPage.el);
                this.menuPage.on('editPage', this.onEditPage, this);
                this.menuPage.on('deletePage', this.onDeletePage, this);
            }
        },

        getRenderData: function () {
            var data = AccordionItem.prototype.getRenderData.apply(this, arguments);
            data.isFirst = this.isFirst;
            data.isLast = this.isLast;
            return data;
        },
        

        showDialog: function (pageModel) {
            var Dialog = require('../../dialog/EditMenuPageDialog');
            pageModel.set('chapterId', this.model.get('id'));
            this.menuPageCollection.parentId = this.model.get('id');
            var dialog = new Dialog({
                model: pageModel,
                menuPageCollection: this.menuPageCollection
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

        onDeletePage: function (pageModel) {
            if (window.confirm('确定删除菜单分页?')) {
                pageModel.destroy({
                    success: function () {
                        var options = {
                            reset: true
                        };
                        this.renderPage(options);
                    }.bind(this)
                });
            }
        },

        onEditPage: function (pageModel) {
            this.showDialog(pageModel);
        },

        onAddPage: function (evt) {
            evt.preventDefault();
            var MenuPageModel = require('../../model/MenuPageModel');
            var pageModel = new MenuPageModel();
            this.showDialog(pageModel);
            evt.stopPropagation();
        },

        onToggle: function () {
            AccordionItem.prototype.onToggle.apply(this, arguments);
            this.renderPage();
        },

        onMoveUp: function (evt) {
            evt.preventDefault();

            MenuUtils.moveUpChapter({
                chapterId: this.model.get('id'),
                success: function () {
                    this.trigger('refreshList');
                }.bind(this)
            });

            evt.stopPropagation();
        },

        onMoveDown: function (evt) {
            evt.preventDefault();
            
            MenuUtils.moveDownChapter({
                chapterId: this.model.get('id'),
                success: function () {
                    this.trigger('refreshList');
                }.bind(this)
            });

            evt.stopPropagation();
        }
        
    });
    
    return ChapterItem;
    
});

