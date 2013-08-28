/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var $ = require('../lib/jquery');

    var BroadcastManager = function () {
    };

    var receivers = {};

    var getReceiverList = function (type) {
        var key = String(type);
        receivers[key] = receivers[key] || [];
        return receivers[key];
    };

    var broadcast = function (message) {
        if (!message || message.type === 0) {
            return;
        }
        var list = getReceiverList(message.type);
        list.forEach(function (cbk) {
            cbk.call(null, message.data);
        }, this);
    };

    var polling = function () {
        $.ajax({
            url: '/api/polling',
            success: function (message) {
                broadcast.call(this, message);
            }.bind(this)
        });
    };

    BroadcastManager.prototype.register = function (type, cbk) {
        var list = getReceiverList(type);
        list.push(cbk);
    };

    BroadcastManager.prototype.start = function () {
        window.setInterval(polling.bind(this), 8000);
    };

    return BroadcastManager;
});

