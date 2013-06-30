/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Dialog = require('./Dialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./CreateUserDialog.handlebars')
    });

    var CreateUserDialog = Dialog.extend({
        header: '新建用户',

        ContentType: Content,

        initialize: function () {
            Dialog.prototype.initialize.apply(this, arguments);
        
            var CreateUserForm = require('../form/CreateUserForm');
            this.form = new CreateUserForm();
        },

        render: function () {
            Dialog.prototype.render.apply(this, arguments);
            this.form.init(this.$('.form-create-user')[0]);
        },
        

        onConfirm: function () {
            Dialog.prototype.onConfirm.apply(this, arguments);
            this.form.trySubmit();
        }
        
    });
    
    return CreateUserDialog;
    
});

