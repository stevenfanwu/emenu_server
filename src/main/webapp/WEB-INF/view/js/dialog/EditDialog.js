/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var FormDialog = require('./FormDialog');

    var EditDialog = FormDialog.extend({
        headerSuffix: null,

        getHeader: function () {
            FormDialog.prototype.getHeader.apply(this, arguments);
            if (this.headerSuffix) {
                return (this.isEditing() ? '编辑' : '新建') + this.headerSuffix;
            }
        },

        getRenderData: function () {
            var data = FormDialog.prototype.getRenderData.apply(this, arguments);
            data.editing = this.isEditing();
            return data;
        },
        
        isEditing: function () {
            return !this.model.isNew();
        }
    });
    
    return EditDialog;
    
});

