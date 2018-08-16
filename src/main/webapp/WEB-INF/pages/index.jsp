<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/6/29
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>表具管理系统</title>
    <link type="text/css" rel="stylesheet" href="../assets/materialize/fonts/material-icons.min.css"/>
    <link type="text/css" rel="stylesheet" href="../assets/materialize/css/materialize.min.css" media="screen"/>
    <link type="text/css" rel="stylesheet" href="../assets/fonts/ionicons.min.css"/>
    <link type="text/css" rel="stylesheet" href="../assets/custom/css/common.css"/>
    <link type="text/css" rel="stylesheet" href="../assets/custom/css/index.css"/>
    <link type="text/css" rel="stylesheet" href="../assets/mdui-v0.4.1/css/mdui.min.css"/>
    <script type="text/javascript" src="../assets/materialize/js/jquery.min.js"></script>
    <script type="text/javascript" src="../assets/materialize/js/materialize.min.js"></script>
    <script type="text/javascript" src="../assets/custom/js/common.js"></script>
    <script type="text/javascript" src="../assets/custom/js/index.js"></script>
    <script type="text/javascript" src="../assets/mdui-v0.4.1/js/mdui.min.js"></script>

</head>
<body class="mdui-drawer-body-left mdui-appbar-with-toolbar mdui-theme-primary-indigo mdui-theme-accent-pink mdui-loaded">
<header class="mdui-appbar mdui-appbar-fixed">
    <div class="mdui-toolbar mdui-color-theme">
        <span class="mdui-btn mdui-btn-icon mdui-ripple mdui-ripple-white" mdui-drawer="{target: '#main-drawer', swipe: true}"mdui-tooltip="{content: '展开/隐藏'}"><i class="mdui-icon material-icons">menu</i></span>
        <img class="no-margin" src="../assets/custom/img/lanyan-logo.jpg">
        <span class="mdui-typo-headline no-select no-margin">蓝焰表具管理系统</span>
        <div class="mdui-toolbar-spacer"></div>
        <span class="mdui-btn mdui-btn-icon mdui-ripple mdui-ripple-white" mdui-tooltip="{content: '锁屏'}"><i class="mdui-icon material-icons">https</i></span>
        <span class="mdui-btn mdui-btn-icon mdui-ripple mdui-ripple-white" mdui-tooltip="{content: '刷新'}"><i class="mdui-icon material-icons">cached</i></span>
        <span class="mdui-btn mdui-btn-icon mdui-ripple mdui-ripple-white" mdui-tooltip="{content: '退出'}"><i class="mdui-icon material-icons">power_settings_new</i></span>
    </div>
</header>
<div class="mdui-drawer NavigationBars" id="main-drawer">
    <div class="mdui-list" mdui-collapse="{accordion: true}">
        <div class="mdui-collapse-item">
            <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">home</i>
                <div class="mdui-list-item-content mdui-text-color-blue">系统管理</div>
                <i class="mdui-collapse-item-arrow mdui-icon material-icons mdui-text-color-blue">keyboard_arrow_down</i>
            </div>
            <div class="mdui-collapse-item-body mdui-list" style="">
                <div class="mdui-list-item mdui-ripple ">区域管理</div>
                <div class="mdui-list-item mdui-ripple ">机构管理</div>
                <div class="mdui-list-item mdui-ripple ">用户管理</div>
                <div class="mdui-list-item mdui-ripple ">角色管理</div>
                <div class="mdui-list-item mdui-ripple ">权限管理</div>
                <div class="mdui-list-item mdui-ripple ">字典管理</div>
                <div class="mdui-list-item mdui-ripple ">日志管理</div>
                <div class="mdui-list-item mdui-ripple ">公告管理</div>
            </div>
        </div>

        <div class="mdui-collapse-item">
            <div class="mdui-collapse-item-header mdui-list-item mdui-ripple mdui-text-color-blue">
                <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">account_box</i>
                <div class="mdui-list-item-content mdui-text-color-blue">账户管理</div>
                <i class="mdui-collapse-item-arrow mdui-icon material-icons mdui-text-color-blue">keyboard_arrow_down</i>
            </div>
            <div class="mdui-collapse-item-body mdui-list" style="">
                <div class="mdui-list-item mdui-ripple">表具入库</div>
                <div class="mdui-list-item mdui-ripple">用户建档</div>
                <div class="mdui-list-item mdui-ripple">挂表</div>
                <div class="mdui-list-item mdui-ripple">开户</div>
                <div class="mdui-list-item mdui-ripple">账户变更</div>
            </div>
        </div>

        <div class="mdui-collapse-item">
            <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">payment</i>
                <div class="mdui-list-item-content mdui-text-color-blue">充值缴费管理</div>
                <i class="mdui-collapse-item-arrow mdui-icon material-icons mdui-text-color-blue">keyboard_arrow_down</i>
            </div>
            <div class="mdui-collapse-item-body mdui-list" style="">
                <div class="mdui-list-item mdui-ripple">预付费充值</div>
                <div class="mdui-list-item mdui-ripple">补卡充值</div>
                <div class="mdui-list-item mdui-ripple">后付费充值</div>
                <div class="mdui-list-item mdui-ripple">发票管理</div>
            </div>
        </div>

        <div class="mdui-collapse-item">
            <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">local_gas_station</i>
                <div class="mdui-list-item-content mdui-text-color-blue">维修补气管理</div>
                <i class="mdui-collapse-item-arrow mdui-icon material-icons mdui-text-color-blue">keyboard_arrow_down</i>
            </div>
            <div class="mdui-collapse-item-body mdui-list" style="">
                <div class="mdui-list-item mdui-ripple">维修单录入</div>
                <div class="mdui-list-item mdui-ripple">维修补气</div>
                <div class="mdui-list-item mdui-ripple">补缴结算</div>
                <div class="mdui-list-item mdui-ripple">IC卡初始化</div>
            </div>
        </div>

        <div class="mdui-collapse-item">
            <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">monetization_on</i>
                <div class="mdui-list-item-content mdui-text-color-blue">账务处理</div>
                <i class="mdui-collapse-item-arrow mdui-icon material-icons mdui-text-color-blue">keyboard_arrow_down</i>
            </div>
            <div class="mdui-collapse-item-body mdui-list" style="">
                <div class="mdui-list-item mdui-ripple">预冲账</div>
                <div class="mdui-list-item mdui-ripple">冲账</div>
            </div>
        </div>
        <div class="mdui-collapse-item">
            <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">network_check</i>
                <div class="mdui-list-item-content mdui-text-color-blue">表具运行管理</div>
                <i class="mdui-collapse-item-arrow mdui-icon material-icons mdui-text-color-blue">keyboard_arrow_down</i>
            </div>
            <div class="mdui-collapse-item-body mdui-list" style="">
                <div class="mdui-list-item mdui-ripple">抄表</div>
                <div class="mdui-list-item mdui-ripple">阀门控制</div>
                <div class="mdui-list-item mdui-ripple">异常情况</div>
            </div>
        </div>
        <div class="mdui-collapse-item">
            <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">assessment</i>
                <div class="mdui-list-item-content mdui-text-color-blue">查询统计</div>
                <i class="mdui-collapse-item-arrow mdui-icon material-icons mdui-text-color-blue">keyboard_arrow_down</i>
            </div>
            <div class="mdui-collapse-item-body mdui-list" style="">
                <div class="mdui-list-item mdui-ripple">IC卡查询</div>
                <div class="mdui-list-item mdui-ripple">开户信息查询</div>
                <div class="mdui-list-item mdui-ripple">用户信息查询</div>
                <div class="mdui-list-item mdui-ripple">异常用户查询</div>
                <div class="mdui-list-item mdui-ripple">营业数据查询</div>
                <div class="mdui-list-item mdui-ripple">营业报表查询</div>
            </div>
        </div>
    </div>
</div>

<%--<body>--%>
<%--<div class="slider">--%>
    <%--<ul class="slides">--%>
        <%--<li>--%>
            <%--<img src="assets/custom/img/07.jpg" alt="07"/>--%>
            <%--<div class="caption center-align">--%>
                <%--<h3>This is our big Tagline!</h3>--%>
                <%--<h5 class="light grey-text text-lighten-3">Here's our small slogan.</h5>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<img src="assets/custom/img/08.jpg" alt="08"/>--%>
            <%--<div class="caption center-align">--%>
                <%--<h3>This is our big Tagline!</h3>--%>
                <%--<h5 class="light grey-text text-lighten-3">Here's our small slogan.</h5>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<img src="assets/custom/img/09.jpg" alt="09"/>--%>
            <%--<div class="caption center-align">--%>
                <%--<h3>This is our big Tagline!</h3>--%>
                <%--<h5 class="light grey-text text-lighten-3">Here's our small slogan.</h5>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<img src="assets/custom/img/10.jpg" alt="10"/>--%>
            <%--<div class="caption center-align">--%>
                <%--<h3>This is our big Tagline!</h3>--%>
                <%--<h5 class="light grey-text text-lighten-3">Here's our small slogan.</h5>--%>
            <%--</div>--%>
        <%--</li>--%>
    <%--</ul>--%>
<%--</div>--%>
<%--<div class="row">--%>

   <%--<div class="col s2">--%>

    <%--<ul class="collapsible collapsible-accordion "style=" overflow-y:auto; height:450px;"onmousemove="selectin(this)" onmouseout="leave(this)">--%>
        <%--<li>--%>
            <%--<div class="collapsible-header waves-effect waves-light green  color-white"><i class="material-icons">home</i>系统管理<i class="material-icons" style="position: absolute;right: 0px;">keyboard_arrow_down</i></div>--%>
            <%--<div class="collapsible-body no-padding children-width-full">--%>
                <%--<div class="waves-effect   btn-large nav-item dist">区域管理</div>--%>
                <%--<div class="waves-effect   btn-large nav-item organization">机构管理</div>--%>
                <%--<div class="waves-effect   btn-large nav-item user">用户管理</div>--%>
                <%--<div class="waves-effect   btn-large nav-item role">角色管理</div>--%>
                <%--<div class="waves-effect   btn-large nav-item permission">权限管理</div>--%>
                <%--<div class="waves-effect   btn-large nav-item dictionary">字典管理</div>--%>
                <%--<div class="waves-effect   btn-large nav-item log">日志管理</div>--%>
                <%--<div class="waves-effect   btn-large nav-item announcement">公告管理</div>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<div class="collapsible-header waves-effect waves-light green  color-white"><i class="material-icons">account_box</i>账户管理</div>--%>
            <%--<div class="collapsible-body no-padding children-width-full">--%>
                <%--<div class="waves-effect  btn-large nav-item inbound">表具入库</div>--%>
                <%--<div class="waves-effect  btn-large nav-item file">用户建档</div>--%>
                <%--<div class="waves-effect  btn-large nav-item install">挂表</div>--%>
                <%--<div class="waves-effect  btn-large nav-item account">开户</div>--%>
                <%--<div class="waves-effect  btn-large nav-item alter">账户变更</div>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<div class="collapsible-header waves-effect waves-light green color-white"><i class="material-icons">payment</i>充值缴费管理</div>--%>
            <%--<div class="collapsible-body no-padding children-width-full">--%>
                <%--<div class="waves-effect  btn-large nav-item prePayment">预付费充值</div>--%>
                <%--<div class="waves-effect  btn-large nav-item replaceCard">补卡充值</div>--%>
                <%--<div class="waves-effect  btn-large nav-item postPayment">后付费充值</div>--%>
                <%--<div class="waves-effect  btn-large nav-item invoice">发票管理</div>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<div class="collapsible-header waves-effect waves-light green  color-white"><i class="material-icons">local_gas_station</i>维修补气管理</div>--%>
            <%--<div class="collapsible-body no-padding children-width-full">--%>
                <%--<div class="waves-effect  btn-large nav-item input">维修单录入</div>--%>
                <%--<div class="waves-effect  btn-large nav-item fillGas">维修补气</div>--%>
                <%--<div class="waves-effect  btn-large nav-item balance">补缴结算</div>--%>
                <%--<div class="waves-effect  btn-large nav-item initCard">IC卡初始化</div>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<div class="collapsible-header waves-effect waves-light green  color-white"><i class="material-icons">library_books</i>账务处理</div>--%>
            <%--<div class="collapsible-body no-padding children-width-full">--%>
                <%--<div class="waves-effect  btn-large nav-item preStrike">预冲账</div>--%>
                <%--<div class="waves-effect  btn-large nav-item strike">冲账</div>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<div class="collapsible-header waves-effect waves-light green  color-white"><i class="material-icons">network_check</i>表具运行管理</div>--%>
            <%--<div class="collapsible-body no-padding children-width-full">--%>
                <%--<div class="waves-effect  btn-large nav-item record">抄表</div>--%>
                <%--<div class="waves-effect  btn-large nav-item control">阀门控制</div>--%>
                <%--<div class="waves-effect  btn-large nav-item exception">异常情况</div>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<div class="collapsible-header waves-effect waves-light green  color-white"><i class="material-icons">query_builder</i>查询统计</div>--%>
            <%--<div class="collapsible-body no-padding children-width-full">--%>
                <%--<div class="waves-effect  btn-large nav-item cardQuery">IC卡查询</div>--%>
                <%--<div class="waves-effect  btn-large nav-item accountQuery">开户信息查询</div>--%>
                <%--<div class="waves-effect  btn-large nav-item userQuery">用户信息查询</div>--%>
                <%--<div class="waves-effect  btn-large nav-item exceptionQuery">异常用户查询</div>--%>
                <%--<div class="waves-effect  btn-large nav-item businessDataQuery">营业数据查询</div>--%>
                <%--<div class="waves-effect  btn-large nav-item businessReportQuery">营业报表查询</div>--%>
            <%--</div>--%>
        <%--</li>--%>
    <%--</ul>--%>
    <%--</div>--%>

    <div class="col s10">
        <ul class="tabs z-depth-3 tabs-fixed-width tabs-extra">
            <li class="tab col s2"><a href="#home-pane">主页</a></li>
        </ul>
        <div class="panes z-depth-3 panes-extra">
            <div id="home-pane" class="pane col s12">
                <div class="row search-box">
                    <div class="col s3">
                        <label>员工名称：<input type="text" class="emp-name" placeholder="员工名称"/></label>
                    </div>
                    <div class="col s3">
                        <label>
                            部门编号：
                            <select class="emp-deptno">
                                <option value="" disabled selected>请选择</option>
                                <option value="10">10</option>
                                <option value="20">20</option>
                                <option value="30">30</option>
                            </select>
                        </label>
                    </div>
                    <div class="col s3">
                        <label>
                            部门编号：
                            <select class="emp-job">
                                <option value="" disabled selected>请选择</option>
                                <option value="CLERK">职员</option>
                                <option value="SALESMAN">销售员</option>
                                <option value="ANALYST">分析师</option>
                                <option value="MANAGER">经理</option>
                                <option value="PRESIDENT">董事长</option>
                            </select>
                        </label>
                    </div>
                    <div class="col s3 align-center">
                        <div class="waves-effect waves-light green lighten-4 btn">
                            <i class="material-icons">search</i>
                        </div>
                    </div>

                </div>
                <div class="row data-box">
                    <table>
                        <thead class="fields">
                        <tr>
                            <th class="empno">编号</th>
                            <th class="ename">姓名</th>
                            <th class="job">职务</th>
                            <th class="mgr">上级</th>
                            <th class="hiredate">时间</th>
                            <th class="sal">工资</th>
                            <th class="comm">补助</th>
                            <th class="deptno">部门</th>
                        </tr>
                        </thead>
                        <tbody class="records"></tbody>
                    </table>
                </div>
                <div class="row controller-box">
                    <a class="waves-effect waves-light light-blue lighten-4 btn">首页</a>
                    <a class="waves-effect waves-light light-blue lighten-4 btn">上一页</a>
                    <a class="waves-effect waves-light light-blue lighten-4 btn">下一页</a>
                    <a class="waves-effect waves-light light-blue lighten-4 btn">尾页</a>
                </div>
            </div>
        </div>

    </div>
</div>
<div class="modal modal-fixed-footer">
    <div class="modal-content align-center">
        <h4 class="title">Title</h4>
    </div>
    <div class="modal-footer align-center">
        <button class="modal-close waves-effect waves-green btn">DisAgree</button>
        <button class="modal-close waves-effect waves-green btn">Agree</button>
    </div>
</div>

<div class="modal tip">
    <div class="modal-content align-center">
        <h6>提示信息</h6>
    </div>
    <div class="modal-footer align-center">
        <button class="modal-close waves-effect waves-green btn">确定</button>
        <button class="modal-close waves-effect waves-green btn">取消</button>
    </div>
</div>
<script type="text/javascript">
     app.initIndex();
</script>
</body>
</html>
