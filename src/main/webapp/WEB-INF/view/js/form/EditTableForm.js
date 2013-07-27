/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseForm = require('./BaseForm');

    var Required = require('./validator/Required');
    var Integer = require('./validator/Integer');
    var Number = require('./validator/Number');
    var MoreThan = require('./validator/MoreThan');
    var TableTipValidator = require('./validator/TableTipValidator');

    var Text = require('./item/Text');
    var Radio = require('./item/Radio');
    var TableTip = require('./item/TableTip');

    var EditTableForm = BaseForm.extend({
        url: '/api/tables',
        
        itemConfig: [{
            name: 'name',
            type: Text,
            validators: [{
                type: Required,
                errorMessage: '桌名不能为空'
            }]
        }, {
            name: 'type',
            type: Radio,
            el: '.input-type'
        }, {
            name: 'capacity',
            type: Text,
            validators: [{
                type: Required,
                errorMessage: '最多人数不能为空'
            }, {
                type: MoreThan,
                other: 0,
                errorMessage: '请输入大于0的整数'
            }, {
                type: Integer,
                errorMessage: '请输入大于0的整数'
            }]
        }, {
            name: 'minCharge',
            type: Text,
            validators: [{
                type: Required,
                errorMessage: '请输入最低消费'
            }, {
                type: MoreThan,
                other: 0,
                including: true,
                errorMessage: '请输入大于0的数字'
            }, {
                type: Number,
                errorMessage: '请输入数字'
            }]
        }, {
            name: 'tip',
            type: TableTip,
            validators: [{
                type: TableTipValidator
            }]
        }]
    });
    
    return EditTableForm;
    
});

