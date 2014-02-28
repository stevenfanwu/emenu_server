/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Dialog = require('./Dialog');

    var BaseContent = require('./BaseContent');
    var ChangeTableList = require('../list/ChangeTableList');

    var Content = BaseContent.extend({
        list: new ChangeTableList(),

        render: function () {
            BaseContent.prototype.render.apply(this, arguments);
            this.list.render();
            this.$el.html(this.list.el);
            this.list.on('select', function (tableModel) {
                this.dialog.trigger('submit', tableModel);
            }, this);
        },

        destroy: function () {
            BaseContent.prototype.destroy();
            this.list.destroy();
        }
        
    });
    

    var ChangeTableDialog = Dialog.extend({
        ContentType: Content,

        header: 'Choose table',

        confirmLabel: null

    });
    
    return ChangeTableDialog;
    
});

