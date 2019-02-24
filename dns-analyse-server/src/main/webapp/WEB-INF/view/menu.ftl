<#--左侧菜单-->
<script type="text/javascript">
    try {
        ace.settings.check('sidebar', 'fixed')
    } catch (e) {
    }
</script>

<#--菜单-->
<ul class="nav nav-list">
    <li class="active" onclick="loadPage('/admin/view/main/mainView');">
        <a href="javascript:void(0);">
            <i class="icon-link"></i>
            <span class="menu-text"> 大盘概况 </span>
        </a>
    </li>
    <li>
        <a href="#" class="dropdown-toggle">
            <i class="icon-list"></i>
            <span class="menu-text">解析依赖</span>
            <b class="arrow icon-angle-down"></b>
        </a>
        <ul class="submenu">
            <li onclick="loadPage('/admin/view/dependence/list');">
                <a href="javascript:void(0);">
                    <i class="icon-arrow-right"></i>
                    <span class="menu-text"> 依赖原始信息 </span>
                </a>
            </li>
            <li onclick="loadPage('/admin/view/dependence/cdnDependence');">
                <a href="javascript:void(0);">
                    <i class="icon-arrow-right"></i>
                    <span class="menu-text"> cdn依赖 </span>
                </a>
            </li>
            <li onclick="loadPage('/admin/view/dependence/ipInfo');">
                <a href="javascript:void(0);">
                    <i class="icon-arrow-right"></i>
                    <span class="menu-text"> ip信息管理 </span>
                </a>
            </li>
        </ul>
    </li>
    <li>
        <#--故障解析-->
        <a href="#" class="dropdown-toggle">
            <i class="icon-desktop"></i>
            <span class="menu-text">故障解析</span>
            <b class="arrow icon-angle-down"></b>
        </a>
        <ul class="submenu">
            <li onclick="loadPage('/admin/view/analyse/list');">
                <a href="javascript:void(0);">
                    <i class="icon-arrow-right"></i>
                    <span class="menu-text"> 故障原始模型 </span>
                </a>
            </li>
            <li onclick="loadPage('/admin/view/analyse/list');">
                <a href="javascript:void(0);">
                    <i class="icon-arrow-right"></i>
                    <span class="menu-text"> 服务器重要度查询 </span>
                </a>
            </li>
            <li onclick="loadPage('/admin/view/analyse/list');">
                <a href="javascript:void(0);">
                    <i class="icon-arrow-right"></i>
                    <span class="menu-text"> 故障模型分析 </span>
                </a>
            </li>
        </ul>
    </li>
    <li>
        <#--网络拓扑信息-->
        <a href="#" class="dropdown-toggle">
            <i class="icon-list"></i>
            <span class="menu-text">网络拓扑信息</span>
            <b class="arrow icon-angle-down"></b>
        </a>
        <ul class="submenu">
            <li onclick="loadPage('/admin/view/orderbusiness/list');">
                <a href="javascript:void(0);">
                    <i class="icon-arrow-right"></i>
                    <span class="menu-text"> 核心域展示 </span>
                </a>
            </li>
            <li onclick="loadPage('/admin/view/orderinfo/list');">
                <a href="javascript:void(0);">
                    <i class="icon-arrow-right"></i>
                    <span class="menu-text"> 拓扑特征 </span>
                </a>
            </li>

            <li onclick="loadPage('/admin/view/orderinfo/list');">
                <a href="javascript:void(0);">
                    <i class="icon-arrow-right"></i>
                    <span class="menu-text"> 模拟节点攻击 </span>
                </a>
            </li>
        </ul>
    </li>
</ul>

<script type="text/javascript">
    try {
        ace.settings.check('sidebar', 'collapsed')
    } catch (e) {
    }
</script>

