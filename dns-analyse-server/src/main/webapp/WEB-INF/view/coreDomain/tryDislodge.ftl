<!DOCTYPE html>
<html lang="en">
<head>
    <base id="base" href="${ctx}"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>提交攻击节点</title>
    <link href="${ctx}/resources/admin/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctx}/resources/admin/assets/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${ctx}/resources/css/jquery.jsonview.css"/>
    <script src='${ctx}/resources/admin/assets/js/jquery-1.10.2.min.js'></script>
    <script src='${ctx}/resources/js/bootstrap.js'></script>
    <#--<script src="${ctx}/resources/js/framework.js"></script>-->
    <script src="${ctx}/resources/js/clipboard.min.js"></script>
    <script src="${ctx}/resources/js/jquery.jsonview.js"></script>
    <link href="${ctx}/resources/admin/assets/css/datepicker.css" rel="stylesheet">
    <script src="${ctx}/resources/admin/assets/js/date-time/bootstrap-datepicker.min.js"></script>
    <link rel="stylesheet" href="${ctx}/resources/css/bootstrap-multiselect.css" type="text/css"/>
    <script type="text/javascript" src="${ctx}/resources/js/bootstrap-multiselect.js"></script>

</head>
<body>
<div class="col-xs-12"  >
    <div class="col-xs-6"  >
        <div class="panel panel-primary" style="height: 350px">
            <div class="panel-heading">
                <h3 class="panel-title">提交攻击节点</h3>
            </div>
            <div class="panel-body">
                <br></br>
                <form class="form-horizontal" role="form" id="formid">
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="createdBy">方式:</label>
                        <div class="col-sm-9">
                            <select class="selectthis" id="dislodgeType" >
                                <option value="1" is="automatic">自动去除</option>
                                <option value="2" id="manual">手动去除</option>
                            </select>
                        </div>
                    </div>
                    <div id="showOne">
                        <div class="form-group">
                            <label class="col-sm-2 control-label" for="createdBy">比例:</label>
                            <div class="col-sm-9">
                                <select class="selectthis" id="example-getting-started" >
                                    <option value="1">1%</option>
                                    <option value="5">5%</option>
                                    <option value="10">10%</option>
                                    <option value="20">20%</option>
                                    <option value="30">30%</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" for="createdBy">模式:</label>
                            <div class="col-sm-9">
                                <#--multiple="multiple"-->
                                <select  class="selectthis" id="example-getting-started1">
                                    <option value="1" id="suiji">随机模式</option>
                                    <option value="2" id="core">核心模式</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" for="iterationName">依据:</label>
                            <div class="col-sm-9">
                                <div id="example">
                                    <select class="selectthis" id="example-getting-started2">
                                        <option value="1">1%</option>
                                        <option value="5">5%</option>
                                        <option value="10">10%</option>
                                        <option value="20">20%</option>
                                        <option value="30">30%</option>
                                    </select>
                                </div>
                                <div id="example1">
                                    <select class="selectthis" id="example-getting-started3">
                                        <option value="1">合数</option>
                                        <option value="5">介数</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="showTwo">
                        <div class="form-group">
                            <label class="col-sm-2 control-label" for="name">名称:</label>
                            <div class="col-sm-9">
                            <textarea class="form-control" id="name" name="name" rows="4"
                                      placeholder="请输入名称,以空格分隔"></textarea>
                            </div>
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
        </div >
    </div>
    <div class="col-xs-6">
        <div class="panel panel-primary" style="height: 350px">
            <div class="panel-heading">
                <h3 class="panel-title">节点攻击结果</h3>
            </div>
            <div class="panel-body">
            <form class="form-horizontal" role="form" id="formid1">
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="createdBy">失效域名数量:</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="invalidNum" name="invalidNum"
                               placeholder="invalidNum"  readonly="readonly"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="createdBy">域名总数量:</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="domainNum" name="domainNum"
                               placeholder="domainNum"  readonly="readonly"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="createdBy">结果失效比例:</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="failureRatio" name="failureRatio"
                               placeholder="failureRatio"  readonly="readonly"/>
                    </div>
                </div>
            </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function() {
        $('.selectthis').multiselect({
            buttonWidth: '200px'
        });
        $("#example1").hide();
        $('#showTwo').hide();
    });
</script>
<script>
    $("#example-getting-started1").change(function(){
        if(document.getElementById("core").selected == true){
            $("#example").hide();
            $("#example1").show();
        }else{
            $("#example").show();
            $("#example1").hide();
        }
    });
    $("#dislodgeType").change(function(){
        if(document.getElementById("manual").selected == true){
            $("#showOne").hide();
            $("#showTwo").show();
        }else{
            $("#showOne").show();
            $("#showTwo").hide();
        }
    });
</script>
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