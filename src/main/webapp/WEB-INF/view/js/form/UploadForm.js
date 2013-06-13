/*jslint browser: true, devel: true, indent: 4, nomen:true, vars: true */
/*global define */

define(function (require, exports, module) {
    "use strict";

    var BaseForm = require('./BaseForm');
    var $ = require('../lib/jquery');

    var UploadForm = BaseForm.extend({
        events: {
            'click .btn-submit': 'onSubmit'
        },

        url: "/upload",

        validateForm: function () {
            BaseForm.prototype.validateForm.apply(this, arguments);
        
            var apkFile = $('input[name="apkFile"]').val();
            if (!apkFile) {
                alert('请选择apk文件路径');
                return false;
            }
            if (apkFile.indexOf('.apk', apkFile.length-4) == -1) {
                alert('只支持上传apk文件');
                return false;
            }
            return true;
        },

        onSuccess: function () {
            alert("上传成功");
        }
        
    });

    return UploadForm;
});

