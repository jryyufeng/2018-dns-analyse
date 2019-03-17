<#import "spring.ftl" as spring/>
<#assign ctx=request.contextPath />
<!DOCTYPE html>
<html lang="en">
<head>
    <base id="base" href="${ctx}"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>使用cdn域名总览</title>
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
    <ul id="myTab" class="nav nav-tabs">
        <li class="active"><a href="#home" data-toggle="tab">
            域名总览</a>
        </li>
        <li><a href="#ios" data-toggle="tab">cdn依赖详情</a></li>
    </ul>
    <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade in active" id="home">
            <div class="panel-body">
                <table id="orderDataTable"></table>
            </div>
        </div>
        <div class="tab-pane fade" id="ios" style="height: 700px; width:1000px;">
            <div class="panel-body">
                <table id="cdnDetailTable"></table>
            </div>
        </div>
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
        $("#cdnDetailTable").bootstrapTable('refresh', params);
    });

    $("#orderDataTable").bootstrapTable({
        url: $("#base").attr('href') + '/admin/dependence/api/list',
        cache: false,
        striped: true,
        pagination: true,
        pageNumber: 1,
        pageSize: 20,
        locales: 'zh-CN',
        sidePagination: 'server',
        detailView: false,
        queryParams: function (params) {
            return {
                pageNum: (params.offset + params.limit) / params.limit,
                pageSize: params.limit,
                isCdn:1,
                domain:$('#domain').val(),
                crossDomain: 2
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
                field: 'isCn',
                title: '一级域名类型'
            }
        ]
    });

    $("#cdnDetailTable").bootstrapTable({
        url: $("#base").attr('href') + '/admin/dependence/api/cdnServer',
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
                title: 'cdn服务器',
                align: 'left',
                valign: 'middle',
                width: 200
                // formatter: function (val,row,index) {
                //     if(row.needUrl!=''&&row.needUrl!=undefined){
                //         return '<a   href="'+row.needUrl+'" target="_blank" >'+row.needName+'</a>';
                //     }else{
                //         if(row.needName!=null){
                //             return  row.needName;
                //         }else{
                //             return '<span class="label label-primary arrowed-in arrowed-in-right"  onclick="needAssign(\'table\','+index+',' + row.id + ')" id="copyRow" >关联需求</span>&nbsp;&nbsp;';
                //         }
                //     }
                // }
            },
            {
                field: 'serverIp',
                title: 'cdn服务器ip',
                align: 'left',
                valign: 'middle',
                width: 200

            },
            {
                field: 'serverDetail',
                title: 'cdn服务器信息',
                align: 'left',
                valign: 'middle',
                width: 200
            }
        ]
    });
</script>