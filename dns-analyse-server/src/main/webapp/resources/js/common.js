Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};

/**
 * 全文替换
 * @param FindText
 * @param RepText
 * @returns {string}
 */
String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
};

/**
 * http同步请求
 * @type {{get: HTTP.get, post: HTTP.post, ajax: HTTP.ajax}}
 */
var HTTP = {

    get: function (url, param) {
        param = param || {};
        return this.ajax(url, param, 'GET');
    },
    post: function (url, param) {
        param = param || {};
        return this.ajax(url, param, 'POST');
    },
    ajax: function (url, param, type) {
        var result = null;
        url = $("#base").attr('href') + '/' + url;
        url = url.replace('//', '/');
        param._t = new Date().getTime();

        $.ajax({
            async: false,
            type: type,
            url: url,
            data: param,
            success: function (data) {
                result = data;
            },
            error: function (reason) {
                console.log(reason);
            }
        });

        return result;
    }
};


