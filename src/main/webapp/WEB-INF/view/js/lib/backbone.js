/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var backbone = window.Backbone;

    var sync = backbone.sync;


    backbone.sync = function (method, model, jqueryOptions) {
        jqueryOptions = jqueryOptions || {};
        var error = jqueryOptions.error;
        jqueryOptions.error = function (xhr) {
            var status = xhr.status;
            if (status === 500 || status === 412 || status === 409) {
                var resp = JSON.parse(xhr.responseText);
                window.alert(resp.message);
            } else if (status === 401) {
                window.alert('长时间未操作，请重新登陆');
                window.location = '/login';
            } else if (status === 512) {
                window.location = '/upgrading';
            } else {
                error.apply(this, arguments);
            }
        };
        sync.call(this, method, model, jqueryOptions);
    };

    return backbone;
});

