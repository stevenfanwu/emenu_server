/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var $ = window.jQuery.noConflict();

    var ajax = $.ajax;

    $.ajax = function (url, options) {
        if (typeof url === "object") {
            options = url;
            url = undefined;
        }
        options = options || {};
        var error = options.error;
        options.error = function (xhr) {
            var status = xhr.status;
            if (status === 500 || status === 412 || status === 409) {
                var resp = JSON.parse(xhr.responseText);
                window.alert(resp.message);
            } else if (status === 401) {
                window.alert('长时间未操作，请重新登陆');
                window.location = '/login';
            } else if (status === 512) {
                window.location = '/upgrading';
            } else if (status === 0) {
                window.alert("网络错误, 状态码=0");
            } else {
                if (error) {
                    error.apply(this, arguments);
                } else {
                    window.alert('网络错误!');
                }
            }
        };
        return ajax.call(this, options);
    };

    return $;
});
