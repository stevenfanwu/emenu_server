/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseRouter = require('./BaseRouter');

    var HistoryRouter = BaseRouter.extend({
        routes: {
            '': 'showOrder',
            'order': 'showOrder',
            'stat': 'showStat',
            'dish-stat': 'showDishStat',
            'menu-stat': 'showMenuStat'
        },
        
        showOrder: function () {
            this.page.trigger('showOrder');
        },
        
        showStat: function () {
            this.page.trigger('showStat');
        },
        
        showDishStat: function () {
            this.page.trigger('showDishStat');
        },

        showMenuStat: function () {
            this.page.trigger('showMenuStat');
        }
    });
    
    return HistoryRouter;
    
    
});

