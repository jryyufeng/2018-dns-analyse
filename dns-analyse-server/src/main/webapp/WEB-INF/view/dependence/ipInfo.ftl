<#import "spring.ftl" as spring/>
<#assign ctx=request.contextPath />
<!DOCTYPE html>
<html lang="en">
<head>
    <base id="base" href="${ctx}"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>域名Ip信息</title>
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
    <style>
        .div-inline{ display:inline}
    </style>
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
</br>
<div class="panel panel-default">
        <ul id="myTab" class="nav nav-tabs">
            <li class="active"><a href="#home" data-toggle="tab">
                ip列表</a>
            </li>
            <li><a href="#ios" data-toggle="tab">ip信息统计</a></li>
        </ul>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade in active" id="home">
                <div class="panel-body">
                    <table id="orderDataTable"></table>
                </div>
            </div>
            <div class="tab-pane fade" id="ios" style="height: 700px; width:1000px;">
                <div class="div-inline" id="main2" style="width: 800px;height:700px; float:left;"></div>
            </div>
    </div>

</div>

</body>
</html>

<script>
    var echartsData = [];
    $('#searchTraceBtn').click(function () {
        $("#orderDataTable").bootstrapTable('refresh');
        $.ajax({
            type : 'get',
            async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url : $("#base").attr('href') + '/admin/domainIp/api/getIpRegion',
            data : {domain:$('#domain').val()},
            dataType : "json", //返回数据形式为json
            success : function(result){
                echartsData = result;
                var optionMap = {
                    backgroundColor: '#FFFFFF',
                    title: {
                        text: '全国地图大数据',
                        subtext: '',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item'
                    },

                    //左侧小导航图标
                    visualMap: {
                        show : true,
                        x: 'left',
                        y: 'center',
                        splitList: [
                            {start: 40, end:50},{start: 30, end: 40},
                            {start: 20, end: 30},{start: 10, end: 20},
                            {start: 5, end: 10},{start: 0, end: 5}
                        ],
                        color: ['#5475f5', '#9feaa5', '#85daef','#74e2ca', '#e6ac53', '#9fb5ea']
                    },

                    //配置属性
                    series: [{
                        name: '数据',
                        type: 'map',
                        mapType: 'china',
                        roam: true,
                        label: {
                            normal: {
                                show: true  //省份名称
                            },
                            emphasis: {
                                show: false
                            }
                        },
                        data:echartsData  //数据
                    }]
                };
                //初始化echarts实例
                var myChart2 = echarts.init(document.getElementById('main2'));
                //使用制定的配置项和数据显示图表
                myChart2.setOption(optionMap);
            },
            error : function(msg) {
                alert("图表请求ip区域位置数据失败!"+msg);
                myChart1.hideLoading();
            }
        });
    });
    $("#orderDataTable").bootstrapTable({
        url: $("#base").attr('href') + '/admin/domainIp/api/list',
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
                field: 'ip',
                title: 'ip地址'
            },
            {
                field: 'detail',
                title: '详情信息'
            }
            // ,
            // {
            //     field: 'city',
            //     title: '城市'
            // }
        ]
    });
</script>

<script>
    function randomData() {
        return Math.round(Math.random()*500);
    }
    var mydata = [
        {name: '北京',value: '100' },{name: '天津',value: randomData() },
        {name: '上海',value: randomData() },{name: '重庆',value: randomData() },
        {name: '河北',value: randomData() },{name: '河南',value: randomData() },
        {name: '云南',value: randomData() },{name: '辽宁',value: randomData() },
        {name: '黑龙江',value: randomData() },{name: '湖南',value: randomData() },
        {name: '安徽',value: randomData() },{name: '山东',value: randomData() },
        {name: '新疆',value: randomData() },{name: '江苏',value: randomData() },
        {name: '浙江',value: randomData() },{name: '江西',value: randomData() },
        {name: '湖北',value: randomData() },{name: '广西',value: randomData() },
        {name: '甘肃',value: randomData() },{name: '山西',value: randomData() },
        {name: '内蒙古',value: randomData() },{name: '陕西',value: randomData() },
        {name: '吉林',value: randomData() },{name: '福建',value: randomData() },
        {name: '贵州',value: randomData() },{name: '广东',value: randomData() },
        {name: '青海',value: randomData() },{name: '西藏',value: randomData() },
        {name: '四川',value: randomData() },{name: '宁夏',value: randomData() },
        {name: '海南',value: randomData() },{name: '台湾',value: randomData() },
        {name: '香港',value: randomData() },{name: '澳门',value: randomData() }
    ];

</script>