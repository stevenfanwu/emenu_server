/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseView = require('../../BaseView');

    var BaseItem = BaseView.extend({
        index: null,
        isFirst: false,
        isLast: false
    });
    
    return BaseItem;
    
    
});

