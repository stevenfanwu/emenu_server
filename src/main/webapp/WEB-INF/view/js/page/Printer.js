/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var TabPage = require('./TabPage');

    var Printer = TabPage.extend({
        RouterType: require('../router/PrinterRouter'),
        
        tabEl: ['.tab-components', '.tab-templates', '.tab-printers'],

        initEvents: function () {
            TabPage.prototype.initEvents.apply(this, arguments);

            this.on('showComponents', function () {
                this.emptyPullRightTab();
                this.activeTab('.tab-components');
                var PrintComponentList = require('../list/PrintComponentList');
                var list = new PrintComponentList();
                list.render();
                this.$('.bottom-content').empty();
                this.$('.bottom-content').append(list.el);
                var tab = this.appendPullRight('Add footer/header');
                tab.on('click', function () {
                    list.trigger('createComponent');
                }, this);
            }, this);

            this.on('showTemplates', function () {
                this.emptyPullRightTab();
                this.activeTab('.tab-templates');
                var PrintTemplateList = require('../list/PrintTemplateList');
                var list = new PrintTemplateList();
                list.render();
                this.$('.bottom-content').empty();
                this.$('.bottom-content').append(list.el);
                var tab = this.appendPullRight('Add template');
                tab.on('click', function () {
                    list.trigger('createTemplate');
                }, this);
            }, this);

            this.on('showPrinters', function () {
                this.emptyPullRightTab();
                this.activeTab('.tab-printers');
                var PrinterConfigList = require('../list/PrinterConfigList');
                var list = new PrinterConfigList();
                list.render();
                this.$('.bottom-content').empty();
                this.$('.bottom-content').append(list.el);
            }, this);
        }
    });
    
    return Printer;
    
});

