/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseForm = require('./BaseForm');
    var Select = require('./item/Select');

    var EditMenuPageForm = BaseForm.extend({
        url: '/api/pages',

        itemConfig: [{
            name: 'dishCount',
            type: Select,
            el: '.input-dishCount'
        }, {
            name: 'ordinal',
            type: Select,
            el: '.input-ordinal'
        }]
        
    });
    
    return EditMenuPageForm;
    
});

