/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var AccordionItem = require('./AccordionItem');

    var ChapterItem = AccordionItem.extend({

        events: {
            'click .btn-edit-chapter': 'onEditChapter',
            'click .btn-delete-chapter': 'onDeleteChapter',
            'click .btn-add-page': 'onAddPage'
        },

        tmpl: require('./ChapterItem.handlebars'),

        
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
            evt.stopPropagation();
        }
        
        
    });
    
    return ChapterItem;
    
});

