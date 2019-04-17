<#import "spring.ftl" as spring/>
<#assign ctx=request.contextPath />
<!DOCTYPE html>
<html lang="en">
<head>
    <base id="base" href="${ctx}"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>核心域总览</title>
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
    <script src="${ctx}/resources/js/echarts.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/js/china.js" ></script>
</head>
<body>
</br>
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
</br>
<div class="panel panel-default">
    <div class="panel-body">
        <table id="orderDataTable"></table>
    </div>
</div>
</body>
</html>

<script>
    $('#searchTraceBtn').click(function () {
        var params = {
            pageNumber: 1
        };
        $("#orderDataTable").bootstrapTable('refresh', params);
    });
    $("#orderDataTable").bootstrapTable({
        url: $("#base").attr('href') + '#',
        locales: 'zh-CN',
        method: 'get',
        dataType: "json",
        clickToSelect: true,
        cache: false,
        //uniqueId: 'index', //将index列设为唯一索引
        striped: true,
        //search: true,
        smartDisplay:true,
        queryParams: function (params) {
            return {
                domain: $('#domain').val()
            }
        },
        columns: [

            {
                field: 'server',
                title: '域名',
                align: 'left',
                valign: 'middle',
                width: 200
            },
            {
                field: 'serverIp',
                title: '入度',
                align: 'left',
                valign: 'middle',
                width: 200

            },
            {
                field: 'serverDetail',
                title: '占比重要度',
                align: 'left',
                valign: 'middle',
                width: 200
            }
        ]
    });
</script>