

var framework = {
    httpGet: function (url, param,processData,contentType) {
        param = param || {};
        return this.ajax(url, param, 'GET',processData,contentType);
    },
    httpPost: function (url, param,processData,contentType) {
        param = param || {};
        return this.ajax(url, param, 'POST',processData,contentType);
    },
    ajax: function (url, param, type,processData,contentType) {
        var result = null;
        url = $("#base").attr('href') + '/' + url;
        url = url.replace('//', '/');
        param._t = new Date().getTime();

        $.ajax({
            async: false,
            type: type,
            url: url,
            data: param,
            dataType : 'json',
            processData: processData,
            contentType: contentType,
            success: function (data) {
                result = data;
            },
            error: function (reason) {
                console.log(reason);
                result = reason;
            }
        });
        return result;
    }
};

/*处理输入*/
framework.handleNode = function (url,param) {
    var result = this.httpGet(url, param,true,false);
    if(result){
        alert("成功");
        return result;
    }else{
        alert("失败");
    }

};

/*处理文件*/
framework.edit = function (url,params) {
    var result = this.httpPost(url, params,false,false);
    if(result.message){
        alert(result.message);
    }else{
        alert("成功");
    }
    return result;
};








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



