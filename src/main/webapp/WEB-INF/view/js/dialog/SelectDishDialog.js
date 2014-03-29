/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Dialog = require('./Dialog');
    var BaseContent = require('./BaseContent');
    var $ = require('../lib/jquery');

    var Content = BaseContent.extend({
        tmpl: require('./SelectDishDialog.handlebars')

    });

    var SelectDishDialog = Dialog.extend({
        header: 'Chose Dish',

        confirmLabel: null,

        ContentType: Content,

        fetched: false,

        dishNames: [],

        dishIds: [],

        render: function () {
            if (!this.fetched) {
                $.ajax({
                    url: '/api/dishes/suggestion'
                }).done(function (data) {
                    this.dishNames = [];
                    this.dishIds = [];
                    data.forEach(function (dish) {
                        this.dishNames.push(dish.name);
                        this.dishIds.push(dish.id);
                    }, this);
                    this.fetched = true;
                    this.doRender();
                }.bind(this));
            }
            this.doRender();
        },

        doRender: function () {
            Dialog.prototype.render.apply(this, arguments);

            this.$('.typeahead').typeahead({
                source: this.dishNames,

                updater: function (dishName) {
                    var i = this.dishNames.indexOf(dishName);
                    var item = {
                        id: this.dishIds[i],
                        name: dishName

                    };
                    this.trigger('submit', item);
                    return dishName;
                }.bind(this)
            });
        },

        onSubmit: function () {
            Dialog.prototype.onSubmit.apply(this, arguments);
            this.hide();
        }


    });
    
    return SelectDishDialog;

});

