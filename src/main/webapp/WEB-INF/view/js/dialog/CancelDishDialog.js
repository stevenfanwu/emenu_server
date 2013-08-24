/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var FormDialog = require('./FormDialog');
    var BaseContent = require('./BaseContent');

    var Content = BaseContent.extend({
        tmpl: require('./CancelDishDialog.handlebars'),

        getRenderData: function () {
            var data = BaseContent.prototype.getRenderData.apply(this, arguments);
            var total = data.number;
            data.countOptions = [];
            var i = 1;
            while (i <= total) {
                data.countOptions.push(i);
                i = i + 1;
            }
            return data;
        }
        
    });

    var CancelDishDialog = FormDialog.extend({
        header: '退菜',

        FormType: require('../form/CancelDishForm'),

        formEl: '.form-cancel-dish',

        ContentType: Content
    });
    
    return CancelDishDialog;
    
});

