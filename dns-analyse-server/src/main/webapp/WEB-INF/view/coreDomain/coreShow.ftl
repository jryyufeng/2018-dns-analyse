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
    <link rel="stylesheet" href="${ctx}/resources/admin/assets/css/jquery.jsonview.css"/>
    <link href="${ctx}/resources/admin/assets/css/bootstrap-table.min.css" rel="stylesheet">
    <link href="${ctx}/resources/admin/assets/css/bootstrap-multiselect.css"/>
    <script src='${ctx}/resources/admin/assets/js/jquery-2.0.3.min.js'></script>
    <script src='${ctx}/resources/admin/assets/js/bootstrap.js'></script>
    <script src="${ctx}/resources/admin/assets/js/jquery.jsonview.js"></script>
    <script src="${ctx}/resources/admin/assets/js/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="${ctx}/resources/admin/assets/js/bootstrap-table/bootstrap-table-locale-all.min.js"></script>
    <script src="${ctx}/resources/admin/assets/js/common.js"></script>
    <script src="${ctx}/resources/admin/assets/js/echarts.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/admin/assets/js/china.js" ></script>
    <link href="${ctx}/resources/admin/assets/css/datepicker.css" rel="stylesheet">
    <script src="${ctx}/resources/admin/assets/js/date-time/bootstrap-datepicker.min.js"></script>
    <link rel="stylesheet" href="${ctx}/resources/admin/assets/css/bootstrap-multiselect.css" type="text/css"/>
    <script type="text/javascript" src="${ctx}/resources/admin/assets/js/bootstrap-multiselect.js"></script>
</head>
<body>
</br>
<div id="toolbar">
    <form class="form-inline" role="queryOrderForm">
        <div class="form-group">
            <label class="col-sm-4 control-label" for="createdBy">信息来源:</label>
            <div class="col-sm-5">
                <select class="selectthis" id="example-getting-started" multiple="multiple">
                    <#--multiple="multiple"-->
                    <option value="graph_chain_100">100域名网络图</option>
                    <option value="graph_chain_1000">1K域名网络图</option>
                    <option value="graph_chain_1w">1W域名网络图</option>
                    <option value="graph_chain_10w">10W域名网络图</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            &nbsp;
            <input type="button" id="searchTraceBtn" class="btn btn-danger icon-search" value="查询"/>
        </div>
    </form>
</div>
<div class="alert alert-success" role="alert">
    介数：定义为网络中所有最短路径中经过该节点的路径的数目占最短路径总数的比例
    </br>
    聚集数：描述了一个点的外部连接聚集程度
    </br>
    核数k-core：删除一幅图中所有度小于 的顶点。一些余下的顶点仍然具有小于 条边。接着删除这些顶点，如此直到不再有删除的可能
</div>
<div class="panel panel-default">
     <div class="panel-body">
        <table id="orderDataTable"></table>
    </div>
</div>
</body>
</html>

<script>
    $('#searchTraceBtn').click(function () {
        //console.log($("#example-getting-started").val().join(';'));
        var params = {
            chartName: $("#example-getting-started").val().join(';')
        };
       $("#orderDataTable").bootstrapTable('refresh', params);
    });
    $("#orderDataTable").bootstrapTable({
        url: $("#base").attr('href') + '/admin/network/api/list',
        cache: false,
        striped: true,
        pagination: true,
        pageNumber: 1,
        pageSize: 20,
        locales: 'zh-CN',
        sidePagination: 'server',
        showColumns: true,                  //是否显示所有的列（选择显示的列）
        showRefresh: true,                  //是否显示刷新按钮
        detailView: false,
        queryParams: function (params) {
            var canshu = '';
            if($("#example-getting-started").val() == null){
                canshu = $("#example-getting-started").val();
            }
            else{
                canshu = $("#example-getting-started").val().join(';');
            }
            return {
                chartName:canshu
            }
        },
        responseHandler: function (res) {
            return {
                "rows": res.pos,
                "total": res.totalCount
            }
        },
        columns: [
            {
                field: 'vertex',
                title: '域名'
            },
            {
                field: 'inDegree',
                title: '入度'

            },
            {
                field: 'betweenCentrality',
                title: '介数'
            },
            {
                field: 'outDegree',
                title: '出度'
            },
            {
                field: 'coreNum',
                title: '核心数'
            },
            {
                field: 'clustering',
                title: '聚集数'
            },
            {
              field:'pagerank',
              title:'pagerank计算结果'
            },
            {
                field: 'clustering',
                title: '聚集数'
            },
            {
                field: 'kind2',
                title: '图类型'
            }
        ]
    });
</script>
<script type="text/javascript">
    $(document).ready(function() {
        $('.selectthis').multiselect({
            buttonWidth: '200px'
        });
    });
</script>