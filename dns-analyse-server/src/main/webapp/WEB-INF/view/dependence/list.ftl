<#import "spring.ftl" as spring/>
<#assign ctx=request.contextPath />
<!DOCTYPE html>
<html lang="en">
<head>
    <base id="base" href="${ctx}"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>原始依赖信息</title>
    <link href="${ctx}/resources/admin/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctx}/resources/admin/assets/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${ctx}/resources/css/jquery.jsonview.css"/>
    <link href="${ctx}/resources/admin/assets/css/bootstrap-table.min.css" rel="stylesheet">
    <link href="${ctx}/resources/css/bootstrap-multiselect.css"/>
    <script src='${ctx}/resources/admin/assets/js/jquery-2.0.3.min.js'></script>
    <script src='${ctx}/resources/js/bootstrap.js'></script>
    <script src="${ctx}/resources/js/jquery.jsonview.js"></script>
    <script src="${ctx}/resources/admin/assets/js/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="${ctx}/resources/admin/assets/js/bootstrap-table/bootstrap-table-locale-all.min.js"></script>
    <script src="${ctx}/resources/js/common.js"></script>
    <script src="${ctx}/resources/js/bootstrap-multiselect.js"/>
</head>
<body>
<script type="text/javascript">
    $(document).ready(function() {
        $('#select').multiselect();
    });
</script>
<div id="toolbar">
    <form class="form-inline" role="queryOrderForm">
        <div class="form-group">
            <input type="text" class="form-control" id="domain" name="domain" placeholder="域名"/>
        </div>
        <div class="form-group">
            <label>&nbsp;&nbsp;是否存在跨域依赖:</label>
            <#--<select id="select" style="height: 30px;z-index:auto;">-->
                <#--<option value="2">不限</option>-->
                <#--<option value="1">是</option>-->
                <#--<option value="0">否</option>-->
            <#--</select>-->
        </div>
        <div class="form-group">
            <input type="button" id="searchTraceBtn" class="btn btn-danger icon-search" value="查询"/>
        </div>
    </form>
</div>

<div class="panel panel-default">
    <div class="panel-body">
        <table id="orderDataTable"></table>
    </div>
</div>


<!-- 模态框（Modal） -->
<div class="modal fade" id="orderDetailModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">详情</h4>
            </div>
            <div class="modal-body">
                <div id="orderResultDIV"
                     style="font-size:10pt;word-wrap:break-word;word-break:break-all;overflow-y: auto;"></div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
<script>
    var PATTERN = 'yyyy-MM-dd hh:mm:ss';
    // 搜索
    $('#searchTraceBtn').click(function () {
        var params = {
            pageNumber: 1
        };
        $("#orderDataTable").bootstrapTable('refresh', params);
    });

    $("#orderDetailModal").on("hidden.bs.modal", function () {
        // 这个#showModal是模态框的id
        $(this).removeData("bs.modal");
        $('#orderResultDIV').html("");
    });


    $("#orderDataTable").bootstrapTable({
        url: $("#base").attr('href') + '/admin/dependence/api/list',
        cache: false,
        striped: true,
        pagination: true,
        pageNumber: 1,
        pageSize: 20,
        toolbar: '#toolbar', // 搜索bar
        locales: 'zh-CN',
        sidePagination: 'server',
        detailView: true,
        queryParams: function (params) {
            return {
                pageNum: (params.offset + params.limit) / params.limit,
                pageSize: params.limit,
                domain: $('#domain').val(),
                crossDomain: 2//$('#select').val()   //新建字段
            }
        },
        responseHandler: function (res) {
            return {
                "rows": res.po,
                "total": res.totalCount
            }
        },
        columns: [
            {
                field: 'domain',
                title: '域名'
            },
            {
                field: 'domainTree',
                title: '域名依赖树',
                formatter: function (value) {
                    return '<xmp>' + value.slice(0,40)+'...' + '</xmp>';
                }
            },
            {
                field: 'domainIp',
                title: 'Ip依赖树',
                formatter: function (value) {
                    return '<xmp>' + value.slice(0,40)+'...' + '</xmp>';
                }
            },
            {
                field: 'domainNum',
                title: '子域数目'
            },
            {
                field: 'crossDomain',
                title: '是否跨域依赖'
//                formatter: function (value) {
//                    return SQTBusinessTypeEnum[value] + '(' + value + ')';
//                }
            }
//            {
//                field: 'createdTime',
//                title: '创建时间'
//                formatter: function (value) {
//                    return new Date(value).format(PATTERN);
//                }
//            }
        ],
        onExpandRow: function (index,row,$detail) {
            loadOrderPayTable(index, row,$detail);
        }
    });
    var loadOrderPayTable = function (index, row, $detail) {
        console.log($detail);
        var serialNum = row.Id; // 父表格的id
        var payTable = $detail.html('<table></table>').find('table');
        ///////////
        $(payTable).bootstrapTable({
            url: '/api/monitor/alive',
            cache: false,
            pagination: true,
            locales: 'zh-CN',
            sidePagination: 'server',
            detailView: true,
            responseHandler: function (res) {
                var rows =[row];
                console.log(rows);
                return {
                    "rows": rows,
                    "total": 1
                }
            },
            columns: [
                {
                    field: 'domainTree',
                    title: '域名依赖树',
                    formatter: function (value) {
                        return '<xmp>' + formatXml(value) + '</xmp>';
                    }
                },
                {
                    field: 'domainIp',
                    title: 'Ip依赖树',
                    formatter: function (value) {
                        return '<xmp>' + formatXml(value) + '</xmp>';
                    }
                }
            ]
        });
    };

//    String.prototype.removeLineEnd = function(){
//        return this.replace(/(<.+?\s+?)(?:\n\s*?(.+?=".*?"))/g,'$1 $2');
//    }
    function formatXml(text){
        //去掉多余的空格
        text = '\n' + text.replace(/(<\w+)(\s.*?>)/g,
                        function($0, name, props){
                            return name + ' ' + props.replace(/\s+(\w+=)/g," $1");
                        }).replace(/>\s*?</g,">\n<");

        //把注释编码
        text = text.replace(/\n/g,'\r').replace(/<!--(.+?)-->/g,
                function($0, text){
                    var ret = '<!--' + escape(text) + '-->';
                    return ret;
                }).replace(/\r/g,'\n');

        //调整格式
        var rgx = /\n(<(([^\?]).+?)(?:\s|\s*?>|\s*?(\/)>)(?:.*?(?:(?:(\/)>)|(?:<(\/)\2>)))?)/mg;
        var nodeStack = [];
        var output = text.replace(rgx,function($0,all,name,isBegin,isCloseFull1,isCloseFull2 ,isFull1,isFull2){
            var isClosed = (isCloseFull1 == '/') || (isCloseFull2 == '/' ) || (isFull1 == '/') || (isFull2 == '/');
            var prefix = '';
            if(isBegin == '!'){
                prefix = getPrefix(nodeStack.length);
            }else {
                if(isBegin != '/'){
                    prefix = getPrefix(nodeStack.length);
                    if(!isClosed){
                        nodeStack.push(name);
                    }
                }else{
                    nodeStack.pop();
                    prefix = getPrefix(nodeStack.length);
                }
            }
            var ret =  '\n' + prefix + all;
            return ret;
        });

        var prefixSpace = -1;
        var outputText = output.substring(1);

        //把注释还原并解码，调格式
        outputText = outputText.replace(/\n/g,'\r').replace(/(\s*)<!--(.+?)-->/g,
                function($0, prefix,  text){
                    if(prefix.charAt(0) == '\r')  prefix = prefix.substring(1);
                    text = unescape(text).replace(/\r/g,'\n');
                    var ret = '\n' + prefix + '<!--' + text.replace(/^\s*/mg, prefix ) + '-->';
                    return ret;
                });
        return outputText.replace(/\s+$/g,'').replace(/\r/g,'\r\n');
    }

    function getPrefix(prefixIndex){
        var span = '    ';
        var output = [];
        for(var i = 0 ; i < prefixIndex; ++i){
            output.push(span);
        }
        return output.join('');
    }


</script>