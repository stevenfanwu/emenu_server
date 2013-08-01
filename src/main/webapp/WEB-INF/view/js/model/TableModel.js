/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseModel = require('./BaseModel');

    var TipMode = {
        NONE: 0,
        FIXED: 1,
        PERCENTAGE: 2
    };

    var TableModel = BaseModel.extend({
        urlRoot: '/api/tables',

        computeTip: function (originPrice) {
            var tipMode = this.get('tipMode');
            var tipValue = this.get('tip');
            if (tipMode === TipMode.NONE) {
                return 0;
            }
            if (tipMode === TipMode.FIXED) {
                return tipValue;
            }

            //TODO round
            return originPrice * tipValue / 100;
        }
    });
    
    return TableModel;
});

