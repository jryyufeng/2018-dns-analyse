<#import "spring.ftl" as spring/>
<#assign ctx=request.contextPath />
<!DOCTYPE html>
<html lang="en">
<head>
    <base id="base" href="${ctx}"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>服务器重要度</title>
    <link href="${ctx}/resources/admin/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctx}/resources/admin/assets/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${ctx}/resources/admin/assets/css/jquery.jsonview.css"/>
    <link href="${ctx}/resources/admin/assets/css/bootstrap-table.min.css" rel="stylesheet">
    <link href="${ctx}/resources/admin/assets/css/bootstrap-multiselect.css"/>
    <script src='${ctx}/resources/admin/assets/js/jquery-2.0.3.min.js'></script>
    <script src='${ctx}/resources/admin/assets/js/bootstrap.js'></script>
    <script src="${ctx}/resources/admin/assets/js/jquery.jsonview.js"></script>
    <script src="${ctx}/resources/admin/assets/js/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="${ctx}/resources/admin/assets/js/bootstrap-table/bootstrap-table-locale-all.min.js"></script>
    <script src="${ctx}/resources/admin/assets/js/common.js"></script>

</head>
<body>
<div id="toolbar">
    <form class="form-inline" role="queryOrderForm">
        <div class="form-group">
            <input type="text" class="form-control" id="domain" name="domain" placeholder="域名"/>
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
    // 搜索
    $('#searchTraceBtn').click(function () {
        var params = {
            pageNumber: 1
        };
        $("#orderDataTable").bootstrapTable('refresh', params);
    });

    $("#orderDataTable").bootstrapTable({
        url: $("#base").attr('href') + '/admin/treeAnalyse/api/list',
        cache: false,
        striped: true,
        pagination: true,
        pageNumber: 1,
        pageSize: 20,
        toolbar: '#toolbar', // 搜索bar
        locales: 'zh-CN',
        sidePagination: 'server',
        detailView: false,

        queryParams: function (params) {
            return {
                pageNum: (params.offset + params.limit) / params.limit,
                pageSize: params.limit,
                domain: $('#domain').val(),
                tag: 1  //新建字段
            }
        },
        responseHandler: function (res) {
            return {
                "rows": res.result,
                "total": res.totalCount
            }
        },
        columns: [
            {
                field: 'domain',
                title: '域名'
            },
            {
                field: 'server',
                title: '割集数目'
            },
            {
                field: 'importance',
                title: '重要度'
            }
//            {
//                field: 'createdTime',
//                title: '创建时间'
//                formatter: function (value) {
//                    return new Date(value).format(PATTERN);
//                }
//            }
        ]
    });

</script>