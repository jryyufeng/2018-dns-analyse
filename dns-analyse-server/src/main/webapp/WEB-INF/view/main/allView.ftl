<#import "spring.ftl" as spring/>
<#assign ctx=request.contextPath />
<!DOCTYPE html>
<html lang="en">
<head>
    <base id="base" href="${ctx}"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>原始故障模型</title>
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

    <div class="form-group">
        <div>
            <div class="div-inline" id="main" style="width: 500px;height:400px; float:left;"></div>
            <div class="div-inline" id="main1" style="width: 500px;height:400px; float:left;"></div>
        </div>
        <div>
            <div class="div-inline" id="main2" style="width: 500px;height:400px; float:left;"></div>
            <label>&nbsp;&nbsp;故障解析比例</label>
            <label>&nbsp;&nbsp;ip地址占比</label>
            <label>&nbsp;&nbsp;使用cdn域名占比</label>
            <label>&nbsp;&nbsp;cdn ip占比</label>
            <label>&nbsp;&nbsp;域名类型占比</label>
        </div>
    </div>
    <script>
        var myChart = echarts.init(document.getElementById('main'));
        var myChart1 = echarts.init(document.getElementById('main1'));
        var myChart2 = echarts.init(document.getElementById('main2'));
        genServerTypeAjax();
        genCdnAjax();
        getdomainNumType();
        function genServerTypeAjax() {
            var legendData = [];
            var seriesData = [];
            var selected = {};
            $.ajax({
                type : 'get',
                async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url : $("#base").attr('href') + '/LocalOperate/serverTypeData',
                data : {},
                dataType : "json", //返回数据形式为json
                success : function(result) {
                    for (var res in result){
                        legendData.push(res);
                        seriesData.push({
                            name: res,
                            value: result[res]
                        });
                        selected[res] = true
                    }
                    myChart.setOption({
                        title: {
                            text: '域名类型占比',
                            subtext: 'top100w数据',
                            x: 'center'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            type: 'scroll',
                            orient: 'vertical',
                            right: 10,
                            top: 20,
                            bottom: 20,
                            data: legendData,

                            selected: selected
                        },
                        series: [
                            {
                                name: '域名类型',
                                type: 'pie',
                                radius: '55%',
                                center: ['40%', '50%'],
                                data: seriesData,
                                itemStyle: {
                                    emphasis: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
                            }
                        ]
                    });
                    myChart.hideLoading();
                },
                error : function(msg) {
                    alert("图表请求Ip分类数据失败!"+msg);
                    myChart.hideLoading();
                }
            });
        }
        function genCdnAjax() {
            var legendData = [];
            var seriesData = [];
            var selected = {};
            $.ajax({
                type : 'get',
                async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url : $("#base").attr('href') + '/LocalOperate/cdnData',
                data : {},
                dataType : "json", //返回数据形式为json
                success : function(result) {
                    for (var res in result){
                        legendData.push(res);
                        seriesData.push({
                            name: res,
                            value: result[res]
                        });
                        selected[res] = true
                    }
                    myChart1.setOption({
                        title: {
                            text: '使用CDN域名占比',
                            subtext: 'top100w数据',
                            x: 'center'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            type: 'scroll',
                            orient: 'vertical',
                            right: 10,
                            top: 20,
                            bottom: 20,
                            data: legendData,

                            selected: selected
                        },
                        series: [
                            {
                                name: '域名类型',
                                type: 'pie',
                                radius: '55%',
                                center: ['40%', '50%'],
                                data: seriesData,
                                itemStyle: {
                                    emphasis: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
                            }
                        ],
                        color: ['rgb(254,67,101)','rgb(249,205,173)','rgb(200,200,169)','rgb(131,175,155)']
                    });

                    myChart1.hideLoading();
                },
                error : function(msg) {
                    alert("图表请求Ip分类数据失败!"+msg);
                    myChart1.hideLoading();
                }
            });
        }
        function getdomainNumType() {
            $.ajax({
                type : 'get',
                async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url : $("#base").attr('href') + '/LocalOperate/domainNumType',
                data : {},
                dataType : "json", //返回数据形式为json
                success : function(result) {
                    var dataRes=[];
                    var dataName=[];
                    for(var res in result){
                        dataRes.push(result[res]);
                        dataName.push(res);
                    }

                    myChart2.setOption({
                        title: {
                            text: '域名子域数量分类统计',
                            subtext: 'top100w数据',
                            x: 'center'
                        },
                        color: ['#3398DB'],
                        tooltip : {
                            trigger: 'axis',
                            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                            }
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        xAxis : [
                            {
                                type : 'category',
                                data : dataName,
                                axisTick: {
                                    alignWithLabel: true
                                }
                            }
                        ],
                        yAxis : [
                            {
                                type : 'value'
                            }
                        ],
                        series : [
                            {
                                name:'域名数量',
                                type:'bar',
                                barWidth: '60%',
                                data:dataRes
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
    </script>

</div>

</body>
</html>