/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BasePage = require('./BasePage');

    var Test = BasePage.extend({

        initialize: function () {
            BasePage.prototype.initialize.apply(this, arguments);

            var job = new print_job();

            job.add_text_nl("Hello World!");
            job.end_job();
            //job.print('192.168.0.250');
            
        }
        

    });
    
    return Test;
    
});

