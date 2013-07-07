/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseItem = require('./BaseItem');
    var $ = require('../../lib/jquery');

    var Image = BaseItem.extend({

        getValue: function () {
            return this.$('.fileupload-exists.thumbnail img').attr('src');
        },

        setValue: function (value) {
            if (value) {
                this.showImage(value);
            }
        },

        showImage: function (data) {
            this.$('.fileupload-new').fileupload({});
            var fileupload = this.$('.fileupload-new').data('fileupload');
            fileupload.$hidden.val('');
            fileupload.$hidden.attr('name', '');
            fileupload.$input.attr('name', this.name);

            var preview = fileupload.$preview;
            var element = fileupload.$element;

            preview.html('<img src="' + data + '" ' + (preview.css('max-height') !== 'none' ? 'style="max-height: ' + preview.css('max-height') + ';"' : '') + ' />');
            element.addClass('fileupload-exists').removeClass('fileupload-new');
        }
    });
    
    return Image;
    
});
