/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var Node = function () {
        this.children = {};
    };

    Node.prototype.hasChild = function () {
        return Object.keys(this.children).length > 0;
    };

    var Trie = function (options) {
        this.getKey = options.getKey;
        this.root = new Node();
    };

    Trie.prototype.insert = function (item) {
        var key = this.getKey(item);
        var len = key.length;
        var p = this.root;
        var i;
        for (i = 0; i < len; i = i + 1) {
            var c = key.charAt(i);
            if (!p.children[c]) {
                p.children[c] = new Node();
            }
            p = p.children[c];
        }
        p.value = item;
    };

    Trie.prototype.build = function (list) {
        list.forEach(function (item) {
            this.insert(item);
        }, this);
    };

    Trie.prototype.search = function (query) {
        var ret = [];
        if (!query) {
            return ret;
        }
        var len = query.length;
        var p = this.root;
        var i;
        for (i = 0; i < len; i = i + 1) {
            var c = query[i];
            if (!p.children[c]) {
                return ret;
            }
            p = p.children[c];
        }

        //traverse p
        if (p.value) {
            ret.push(p.value);
        }
        var queue = [];
        if (p.hasChild()) {
            queue.push(p);
        }
        var handleChildren = function (c) {
            if (this.children[c].value) {
                ret.push(this.children[c].value);
            }
            if (this.children[c].hasChild()) {
                queue.push(this.children[c]);
            }
        };
        while (queue.length > 0) {
            p = queue.shift();
            Object.keys(p.children).forEach(handleChildren, p);
        }
        return ret;
    };
    
    return Trie;
});

