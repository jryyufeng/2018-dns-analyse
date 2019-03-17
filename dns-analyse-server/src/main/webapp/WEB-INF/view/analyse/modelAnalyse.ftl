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
    <style>
        .test{
            position: absolute;      /*绝对定位*/
            top: 50%;                  /* 距顶部50%*/
            left: 50%;                  /* 距左边50%*/
            height: 200px;  margin-top: -100px;   /*margin-top为height一半的负值*/
            width: 400px;  margin-left: -200px;    /*margin-left为width一半的负值*/
        }
    </style>
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
            服务器列表</a>
        </li>
        <li><a href="#ios" data-toggle="tab">故障模型</a></li>
        <li><a href="#observation" data-toggle="tab">选择观察</a></li>
        <li><a href="#speculation" data-toggle="tab">故障推测</a></li>
    </ul>
    <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade in active" id="home">
            <a id="title">共获取0个可解析依赖服务器</a>
            <div class="panel-body">
                <table class="table  table-hover table-striped" style="table-layout:fixed;">
                    <thead>
                        <tr>
                            <th data-field="name">Ip对应</th>
                            <th data-field="set">结构重要度对应</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td> <table id="orderDataTable"></table></td>
                            <td><table id="orderDataTable1" ></table></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="tab-pane fade" id="ios">
            <div class="panel-body">
                <table class="table  table-hover table-striped" style="table-layout:fixed;">
                    <thead>
                    <tr>
                        <th data-field="name">最小割集</th>
                        <th data-field="set">最小路集</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td> <table id="orderDataTable2"></table></td>
                        <td><table id="orderDataTable3" ></table></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="tab-pane fade" id="observation" >
            <div class="panel-body">
                <div class="div-inline" id="main" style="width: 600px;height:400px; float:left;"></div>
                <div class="div-inline" id="main2" style="width: 600px;height:400px; float:left;"></div>
                <div style="width: 600px;"><table id="orderDataTable6" ></table></div>
            </div>
        </div>
        <div class="tab-pane fade" id="speculation" style="overflow-x: auto; overflow-y: auto; height: 500px; width:1175px;">
            <div class="panel-body">
                <div id="test">
                    <table>
                        <tr>
                            <th>
                                输入故障服务器:
                            </th>
                            <th>

                                <input type="text" class="form-control" id="url_get" name="url_get" placeholder="故障服务器"/>
                            </th>
                            <th>
                                <button class="btn btn-default" type="submit" onclick="test()" >
                                    <span class="glyphicon glyphicon-hand-right" aria-hidden="true"></span>获取最佳匹配模型!
                                </button>
                            </th>
                        </tr>
                    </table>
                    <table class="table  table-hover table-striped" style="table-layout:fixed;">
                        <thead>
                        <tr>
                            <th data-field="name">受影响割集
                                <a id="mcsTitle">(本次共影响{0}个割集，解析失败率为{0%})</a>
                            </th>
                            <th data-field="set">受影响路集
                                <a id="mpsTitle">(本次共影响{0}个路集，解析成功率为{0%})</a>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td> <table id="orderDataTable4"></table></td>
                            <td><table id="orderDataTable5" ></table></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
<script>
    var domain = '';
    var mps =[];
    var mcs = [];
    var serverImportance = [];
    $('#searchTraceBtn').click(function () {
        var params = {
            pageNumber: 1
        };
        $("#orderDataTable").bootstrapTable('refresh', params);
        $("#orderDataTable1").bootstrapTable('refresh', params);
        $("#orderDataTable2").bootstrapTable('refresh', params);
        $("#orderDataTable3").bootstrapTable('refresh', params);
        getdomainNumType();
        getdomainNumTypeB();
    });

    $("#orderDataTable").bootstrapTable({
        url: $("#base").attr('href') + '/admin/treeAnalyse/api/queryServerInfo',
        cache: false,
        method: 'get',
        //ajaxOptions: {businessSerialNum: serialNum},
        queryParams: function (params) {
            return {
                domain:$('#domain').val()
            }
        },
        columns: [
            {
                field: 'serverName',
                title: '服务器'
            },
            {
                field: 'ip',
                title: 'ip'
            }
        ],
        onLoadSuccess:function(res){
            var text = $('#title').text().replace('0',res.length);
            $("#title").text(text);
            domain = $('#domain').val();
        }
    });

    $("#orderDataTable1").bootstrapTable({
        url: $("#base").attr('href') + '/admin/treeAnalyse/api/queryImportance',
        cache: false,
        method: 'get',
        //ajaxOptions: {businessSerialNum: serialNum},
        queryParams: function (params) {
            return {
                domain:$('#domain').val()
            }
        },
        columns: [
            {
                field: 'serverName',
                title: '服务器'
            },
            {
                field: 'importance',
                title: '结构重要度'
            }
        ],
        onLoadSuccess:function(res){
            serverImportance = res;
            getTable6();
        }
    });
    $("#orderDataTable2").bootstrapTable({
        url: $("#base").attr('href') + '/admin/treeAnalyse/api/queryMcs',
        cache: false,
        method: 'get',
        //ajaxOptions: {businessSerialNum: serialNum},
        queryParams: function (params) {
            return {
                domain:$('#domain').val()
            }
        },
        columns: [
            {
                title: 'Number',//标题  可不加
                formatter: function (value, row, index) {
                    return index+1;
                }
            },
            {
                title: '割集',
                formatter: function (value, row, index) {
                    return row;
                }
            }
        ],
        onLoadSuccess:function(res){
            mcs = res;
        }
    });

    $("#orderDataTable3").bootstrapTable({
        url: $("#base").attr('href') + '/admin/treeAnalyse/api/queryMps',
        cache: false,
        method: 'get',
        //ajaxOptions: {businessSerialNum: serialNum},
        queryParams: function (params) {
            return {
                domain:$('#domain').val()
            }
        },
        columns: [
            {
                title: 'Number',//标题  可不加
                formatter: function (value, row, index) {
                    return index+1;
                }
            },
            {
                title: '路集',
                formatter: function (value, row, index) {
                    return row;
                }
            }
        ],
        onLoadSuccess:function(res){
            mps = res;
        }
    });

</script>
<script>
    var myChart2 = echarts.init(document.getElementById('main2'));
    var myChart = echarts.init(document.getElementById('main'));
    function getdomainNumType() {
        $.ajax({
            type : 'get',
            async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url : $("#base").attr('href') + '/admin/treeAnalyse/api/queryImportance',
            data : {domain:$('#domain').val()},
            dataType : "json", //返回数据形式为json
            success : function(result) {
                var dataRes=[];
                var dataName=[];
                for(var res in result){
                    dataName.push(result[res].serverName);
                    dataRes.push(result[res].importance);
                }
                myChart2.setOption({
                    title: {
                        text: '服务器重要度',
                        subtext: '数据来自域名分析系统'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: ['结构重要度']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'value',
                        boundaryGap: [0, 0.01]
                    },
                    yAxis: {
                        type: 'category',
                        data: dataName
                    },
                    series: [
                        {
                            name: '2011年',
                            type: 'bar',
                            data: dataRes
                        }
                    ]
                });
            },
            error : function(msg) {
                alert("图表请求数据失败!"+msg);
                myChart2.hideLoading();
            }
        });

    }
    function getdomainNumTypeB() {
        $.ajax({
            type : 'get',
            async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url : $("#base").attr('href') + '/admin/treeAnalyse/api/queryImportance',
            data : {domain:$('#domain').val()},
            dataType : "json", //返回数据形式为json
            success : function(result) {
                var dataRes=[];
                var dataName=[];
                for(var res in result){
                    dataName.push(result[res].serverName);
                    dataRes.push({value:result[res].importance,name:result[res].serverName});
                }

                myChart.setOption({
                    title: {
                        text: '重要度占比',
                        subtext: domain+'的数据',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b}: {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        x: 'left',
                        data:dataName
                    },
                    series: [
                        {
                            name:'访问来源',
                            type:'pie',
                            radius: ['50%', '70%'],
                            avoidLabelOverlap: false,
                            label: {
                                normal: {
                                    show: false,
                                    position: 'center'
                                },
                                emphasis: {
                                    show: true,
                                    textStyle: {
                                        fontSize: '30',
                                        fontWeight: 'bold'
                                    }
                                }
                            },
                            labelLine: {
                                normal: {
                                    show: false
                                }
                            },
                            data:dataRes
                        }
                    ]
                });
            },
            error : function(msg) {
                alert("图表请求数据失败!"+msg);
                myChart.hideLoading();
            }
        });

    }
    function  getTable6() {

        var resultShow =[];
        serverImportance.forEach(function(item,index){
            if(item.importance>=0.5){
                var param = {type1:item.serverName,type2:'',type3:'',type4:''};
                resultShow.push(param);
            }
            if(item.importance< 0.5 && item.importance>=0.3){
                var param = {type1:'',type2:item.serverName,type3:'',type4:''};
                resultShow.push(param);
            }
            if(item.importance< 0.3 && item.importance>0){
                var param = {type1:'',type2:'',type3:item.serverName,type4:''};
                resultShow.push(param);
            }
            if(item.importance == 0){
                var param = {type1:'',type2:'',type3:'',type4:item.serverName};
                resultShow.push(param);
            }

        });

        $("#orderDataTable6").bootstrapTable({
            data:resultShow,
            //ajaxOptions: {businessSerialNum: serialNum},
            columns: [
                {
                    title: '一级重要度<a>(>=0.5)</a>',//标题  可不加
                    field: 'type1'

                },
                {
                    title: '二级重要度<a>(0.3--0.5)</a>',
                    field: 'type2'
                },
                {
                    title: '三级重要度<a>(0--0.3)</a>',
                    field: 'type3'
                },
                {
                    title: '四级重要度<a>(=0)</a>',
                    field: 'type4'
                }
            ]
        });

    }
</script>

<script>
    function test(){
        //console.log("mps",mps);
        var newMcs = [];
        var newMps = [];
        var server = $('#url_get').val();
        mcs.forEach(function(item,index){
            if(item.indexOf(server) != -1){
                newMcs.push(item);
            }
        });
        mps.forEach(function(item,index){
            if(item.indexOf(server) != -1){
                newMps.push(item);
            }
        });
        $("#orderDataTable4").bootstrapTable({
            data:newMcs,
            //ajaxOptions: {businessSerialNum: serialNum},
            columns: [
                {
                    title: 'Number',//标题  可不加
                    formatter: function (value, row, index) {
                        return index+1;
                    }
                },
                {
                    title: '割集',
                    formatter: function (value, row, index) {
                        return row;
                    }
                }
            ],
            onLoadSuccess:function(res){
                var text = $('#mcsTitle').text().replace('0',res.length);
                $("#mcsTitle").text(text);
            }
        });
        $("#orderDataTable5").bootstrapTable({
            data:newMps,
            //ajaxOptions: {businessSerialNum: serialNum},
            columns: [
                {
                    title: 'Number',//标题  可不加
                    formatter: function (value, row, index) {
                        return index+1;
                    }
                },
                {
                    title: '路集',
                    formatter: function (value, row, index) {
                        return row;
                    }
                }
            ]
        });
        var text = $('#mpsTitle').text().replace('{0}',newMps.length).replace('{0%}',newMps.length/mps.length);
        console.log(text);
        $("#mpsTitle").text(text);
        var textc = $('#mcsTitle').text().replace('{0}',newMcs.length).replace('{0%}',newMcs.length/mcs.length);
        console.log(textc);
        $("#mcsTitle").text(textc);
    }

</script>