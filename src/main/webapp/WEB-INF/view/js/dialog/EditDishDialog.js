/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var EditDialog = require('./EditDialog');
    var BaseContent = require('./BaseContent');
    var $ = require('../lib/jquery');

    var Content = BaseContent.extend({
        tmpl: require('./EditDishDialog.handlebars')

    });

    var EditDishDialog = EditDialog.extend({
        headerSuffix: 'Dish',

        ContentType: Content,

        formEl: '.form-edit-dish',

        FormType: require('../form/EditDishForm'),
        
        render: function () {
            var data = this.getRenderData();
            if (data.imageId && !data.uriData) {
                //fetch uri data
                $.ajax({
                    url: "/images/data/" + data.imageId,
                    type: 'GET'
                }).done(function (uriData) {
                    this.model.set('uriData', uriData);
                    EditDialog.prototype.render.apply(this, arguments);
                }.bind(this));
            } else {
                EditDialog.prototype.render.apply(this, arguments);
            }
        }
        
    });
    
    return EditDishDialog;
});

