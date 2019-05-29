<#import "spring.ftl" as spring/>
<#assign ctx=request.contextPath />
<!DOCTYPE html>
<html lang="en">
<head>
    <base id="base" href="${ctx}"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>拓扑结构图展示</title>
    <link href="${ctx}/resources/admin/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctx}/resources/admin/assets/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${ctx}/resources/admin/assets/css/jquery.jsonview.css"/>
    <script src='${ctx}/resources/admin/assets/js/jquery-1.10.2.min.js'></script>
    <script src='${ctx}/resources/admin/assets/js/bootstrap.js'></script>
    <#--<script src="${ctx}/resources/js/framework.js"></script>-->
    <script src="${ctx}/resources/admin/assets/js/clipboard.min.js"></script>
    <script src="${ctx}/resources/admin/assets/js/jquery.jsonview.js"></script>
    <link href="${ctx}/resources/admin/assets/css/datepicker.css" rel="stylesheet">
    <script src="${ctx}/resources/admin/assets/js/date-time/bootstrap-datepicker.min.js"></script>
    <link rel="stylesheet" href="${ctx}/resources/admin/assets/css/bootstrap-multiselect.css" type="text/css"/>
    <script type="text/javascript" src="${ctx}/resources/admin/assets/js/bootstrap-multiselect.js"></script>

</head>
<body>
    <div class="col-xs-6">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">添加</h3>
            </div>
            <div class="panel-body">
                <form class="form-horizontal" role="form" id="formid">
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="name">名称:</label>
                        <div class="col-sm-9">
                                <textarea class="form-control" id="name" name="name" rows="4"
                                          placeholder="请输入名称"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="des">描述:</label>
                        <div class="col-sm-9">
                                <textarea class="form-control" id="des" name="des" rows="4"
                                          placeholder="请输入描述"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-9">

                            <span style="color:red;" id="nowTs"></span>
                            <button type="button" class="btn btn-success pull-right"
                                    onclick="framework.add('TaskData/add','formid','${request.contextPath}/TaskData/toManage');"> 提交 <i class="icon-arrow-right icon-on-right bigger-110"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="col-xs-6">
         <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">构图展示</h3>
            </div>
            <div class="panel-body">
                <div id="myCarousel" class="carousel slide">
                    <!-- 轮播（Carousel）指标 -->
                    <ol class="carousel-indicators">
                        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                        <li data-target="#myCarousel" data-slide-to="1"></li>
                        <li data-target="#myCarousel" data-slide-to="2"></li>
                    </ol>
                    <!-- 轮播（Carousel）项目 -->
                    <div class="carousel-inner">
                        <div class="item active">
                            <img src="${ctx}/resources/image/activity.png" alt="First slide">
                        </div>
                        <div class="item">
                            <img src="${ctx}/resources/image/activity.png" alt="Second slide">
                        </div>
                        <div class="item">
                            <img src="${ctx}/resources/image/activity.png" alt="Third slide">
                        </div>
                    </div>
                    <!-- 轮播（Carousel）导航 -->
                    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
<script>
    $(function () {
        var PATTERN = 'YYYY-MM-DD';
        $('.datetime').datepicker({
            dateFormat: PATTERN,
            language: 'cn',
            autoclose: true,
            startDate: '-0d',
            todayHighlight: true,
            clearBtn: true, //清除按钮
            todayBtn: true //今日按钮
        });

    });
</script>