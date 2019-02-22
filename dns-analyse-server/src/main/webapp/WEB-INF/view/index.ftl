<#import "spring.ftl" as spring/>
<#assign ctx=request.contextPath />
<!DOCTYPE html>
<html lang="en">
<head>
    <base id="base" href="${ctx}"/>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>域名解析安全性分析系统</title>
    <link href="${ctx}/resources/admin/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctx}/resources/admin/assets/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${ctx}/resources/admin/assets/css/ace.min.css"/>
    <link rel="stylesheet" href="${ctx}/resources/admin/assets/css/ace-rtl.min.css"/>
    <link rel="stylesheet" href="${ctx}/resources/admin/assets/css/ace-skins.min.css"/>
    <script src="${ctx}/resources/admin/assets/js/ace-extra.min.js"></script>
    <link href="${ctx}/resources/admin/assets/css/bootstrap-table.min.css" rel="stylesheet">
    <link href="${ctx}/resources/admin/assets/css/daterangepicker.css" rel="stylesheet">
</head>

<body style="position:fixed;width:100%;height:100%">

<div class="navbar navbar-default" id="navbar">
    <script type="text/javascript">
        try {
            ace.settings.check('navbar', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left">
            <a href="#" class="navbar-brand">
                <small>
                    <i class="icon-shopping-cart"></i>
                    域名解析安全性分析系统
                </small>
            </a>
        </div>

        <div class="navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <li class="light-blue">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <span>环境:<font size="+2" color="blue"> <strong>${envs}</strong> </font></span>
                        <i class="icon-caret-down"></i>
                    </a>

                    <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <a href="#">
                                <i class="icon-cog"></i>
                                测试${(envs == 'test') ? string('(当前)' , '') }
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <i class="icon-moon"></i>
                                staging ${(envs == 'staging') ? string('(当前)' ,'') }
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>
        <div class="sidebar" id="sidebar">
            <#include 'menu.ftl'/>
            <div class="sidebar-collapse" id="sidebar-collapse">
                <i class="icon-double-angle-left" data-icon1="icon-double-angle-left"
                   data-icon2="icon-double-angle-right"></i>
            </div>
            <script type="text/javascript">
                try {
                    ace.settings.check('sidebar', 'collapsed')
                } catch (e) {
                }
            </script>
        </div>

        <div class="main-content">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {
                    }
                </script>

                <ul class="breadcrumb">
                    <li>
                        <i class="icon-home home-icon"></i>
                    </li>
                    <li class="active"><a href="#" id="boom">域名解析安全性分析系统</a></li>
                </ul>
            </div>

            <#--<iframe src="${ctx}/admin/view/orderbusiness/list" id="sqtFrame" width="100%" height="100%"-->
                    <#--frameborder="0"  class="page-content"-->
                    <#--style="overflow-x: hidden;overflow-y: hidden;padding: 8px,10px,10px"></iframe>-->
        </div>
    </div>
</div>

</body>
</html>
<#include 'common.ftl'/>

