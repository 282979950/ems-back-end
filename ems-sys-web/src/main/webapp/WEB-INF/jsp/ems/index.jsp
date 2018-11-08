<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/6/29
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>表具管理系统</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/custom/img/lanyan-log.ico" type="image/x-icon"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/materialize/fonts/material-icons.min.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/materialize/css/materialize.min.css" media="screen"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/fonts/ionicons.min.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/custom/css/common.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/custom/css/index.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/mdui-v0.4.1/css/mdui.min.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/spop/spop.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/zTree-3.5.16/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap-datetimepicker.min.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/materialize/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/materialize/js/materialize.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/custom/js/index.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/custom/js/component.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/mdui-v0.4.1/js/mdui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/zTree-3.5.16/js/jquery.ztree.all-3.5.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/spop/spop.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
</head>
<body class="mdui-drawer-body-left mdui-appbar-with-toolbar mdui-theme-primary-indigo mdui-theme-accent-blue mdui-loaded">
<header class="mdui-appbar mdui-appbar-fixed">
    <div class="mdui-toolbar mdui-color-theme">
        <span class="mdui-btn mdui-btn-icon mdui-ripple mdui-ripple-white"
              mdui-drawer="{target: '.mdui-drawer', swipe: true}" mdui-tooltip="{content: '展开/隐藏'}">
            <i class="mdui-icon material-icons">menu</i>
        </span>
        <img class="no-margin" src="${pageContext.request.contextPath}/static/custom/img/lanyan-logo.jpg">
        <span class="mdui-typo-headline no-select no-margin">蓝焰表具管理系统</span>
        <div class="mdui-toolbar-spacer"></div>
        <span onclick="app.lockScreen()" class="mdui-btn mdui-btn-icon mdui-ripple mdui-ripple-white"
              mdui-tooltip="{content: '锁屏'}">
            <i class="mdui-icon material-icons">https</i>
        </span>
        <span onclick="app.refresh()" class="mdui-btn mdui-btn-icon mdui-ripple mdui-ripple-white"
              mdui-tooltip="{content: '刷新'}">
            <i class="mdui-icon material-icons">cached</i>
        </span>
        <span onclick="app.logout()" class="mdui-btn mdui-btn-icon mdui-ripple mdui-ripple-white"
              mdui-tooltip="{content: '退出'}">
            <i class="mdui-icon material-icons">power_settings_new</i>
        </span>
    </div>
</header>
<div class="mdui-drawer">
    <div class="mdui-list" mdui-collapse="{accordion: true}">
        <shiro:hasPermission name="sys:visit">
            <div class="mdui-collapse-item mdui-collapse-item-open">
                <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                    <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">home</i>
                    <div class="mdui-list-item-content mdui-text-color-blue">系统管理</div>
                    <i class="mdui-collapse-item-arrow mdui-icon material-icons mdui-text-color-blue">keyboard_arrow_down</i>
                </div>
                <div class="mdui-collapse-item-body mdui-list" style="">
                    <shiro:hasPermission name="sys:dist:visit">
                        <div class="mdui-list-item mdui-ripple nav-item dist">区域管理</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:org:visit">
                        <div class="mdui-list-item mdui-ripple nav-item org">机构管理</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:emp:visit">
                        <div class="mdui-list-item mdui-ripple nav-item emp">用户管理</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:role:visit">
                        <div class="mdui-list-item mdui-ripple nav-item role">角色管理</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:perm:visit">
                        <div class="mdui-list-item mdui-ripple nav-item permission">权限管理</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:dic:visit">
                        <div class="mdui-list-item mdui-ripple nav-item dic">字典管理</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:gasPrice:visit">
                    <div class="mdui-list-item mdui-ripple nav-item gasPrice">气价管理</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:log:visit">
                        <div class="mdui-list-item mdui-ripple nav-item log">日志管理</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:notice:visit">
                        <div class="mdui-list-item mdui-ripple nav-item announcement">公告管理</div>
                    </shiro:hasPermission>
                </div>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="account:visit">
            <div class="mdui-collapse-item">
                <div class="mdui-collapse-item-header mdui-list-item mdui-ripple mdui-text-color-blue">
                    <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">account_box</i>
                    <div class="mdui-list-item-content mdui-text-color-blue">账户管理</div>
                    <i class="mdui-collapse-item-arrow mdui-icon material-icons mdui-text-color-blue">keyboard_arrow_down</i>
                </div>
                <div class="mdui-collapse-item-body mdui-list" style="">
                    <shiro:hasPermission name="account:entryMeter:visit">
                        <div class="mdui-list-item mdui-ripple nav-item entry">表具入库</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="account:createArchive:visit">
                        <div class="mdui-list-item mdui-ripple nav-item createArchive">用户建档</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="account:installation:visit">
                        <div class="mdui-list-item mdui-ripple nav-item installMeter">挂表</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="account:createAccount:visit">
                        <div class="mdui-list-item mdui-ripple nav-item account">开户</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="account:lockAccount:visit">
                        <div class="mdui-list-item mdui-ripple nav-item lockAccount">账户锁定</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="account:alter:visit">
                        <div class="mdui-list-item mdui-ripple nav-item alter">账户变更</div>
                    </shiro:hasPermission>
                </div>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="recharge:visit">
        <div class="mdui-collapse-item">
            <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">payment</i>
                <div class="mdui-list-item-content mdui-text-color-blue">充值缴费管理</div>
                <i class="mdui-collapse-item-arrow mdui-icon material-icons mdui-text-color-blue">keyboard_arrow_down</i>
            </div>
            <div class="mdui-collapse-item-body mdui-list" style="">
                <shiro:hasPermission name="recharge:pre:visit">
                    <div class="mdui-list-item mdui-ripple nav-item prePayment">预付费充值</div>
                </shiro:hasPermission>
                <shiro:hasPermission name="recharge:supplement:visit">
                    <div class="mdui-list-item mdui-ripple nav-item replaceCard">补卡充值</div>
                </shiro:hasPermission>
                <shiro:hasPermission name="recharge:suff:visit">
                <div class="mdui-list-item mdui-ripple nav-item postPayment">后付费充值</div>
                </shiro:hasPermission>
                <shiro:hasPermission name="recharge:order:visit">
                    <div class="mdui-list-item mdui-ripple nav-item order">订单管理</div>
                </shiro:hasPermission>
            </div>
        </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="invoice:visit">
            <div class="mdui-collapse-item">
                <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                    <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">print</i>
                    <div class="mdui-list-item-content mdui-text-color-blue">发票管理</div>
                    <i class="mdui-collapse-item-arrow mdui-icon material-icons mdui-text-color-blue">keyboard_arrow_down</i>
                </div>
                <div class="mdui-collapse-item-body mdui-list" style="">
                    <shiro:hasPermission name="invoice:assign:visit">
                        <div class="mdui-list-item mdui-ripple nav-item assign">发票分配</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="invoice:printCancel:visit">
                        <div class="mdui-list-item mdui-ripple nav-item printCancel">发票查询</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="invoice:einvoice:visit">
                        <div class="mdui-list-item mdui-ripple nav-item eInvoice">电子发票管理</div>
                    </shiro:hasPermission>
                </div>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="repairorder:visit">
        <div class="mdui-collapse-item">
            <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">local_gas_station</i>
                <div class="mdui-list-item-content mdui-text-color-blue">维修补气管理</div>
                <i class="mdui-collapse-item-arrow mdui-icon material-icons mdui-text-color-blue">keyboard_arrow_down</i>
            </div>
            <div class="mdui-collapse-item-body mdui-list" style="">
                <shiro:hasPermission name="repairorder:entry:visit">
                <div class="mdui-list-item mdui-ripple nav-item input">维修单录入</div>
                </shiro:hasPermission>
                <shiro:hasPermission name="repairorder:fillGas:visit">
                <div class="mdui-list-item mdui-ripple nav-item fillGas">补气补缴结算</div>
                </shiro:hasPermission>
                <shiro:hasPermission name="repairorder:iccardinit:visit">
                <div class="mdui-list-item mdui-ripple nav-item initCard">IC卡初始化</div>
                </shiro:hasPermission>
            </div>
        </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="financial:visit">
        <div class="mdui-collapse-item">
            <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">monetization_on</i>
                <div class="mdui-list-item-content mdui-text-color-blue">账务处理</div>
                <i class="mdui-collapse-item-arrow mdui-icon material-icons mdui-text-color-blue">keyboard_arrow_down</i>
            </div>
            <div class="mdui-collapse-item-body mdui-list" style="">
                <shiro:hasPermission name="financial:prestrike:visit">
                <div class="mdui-list-item mdui-ripple nav-item preStrike">预冲账</div>
                </shiro:hasPermission>
                <shiro:hasPermission name="financial:strike:visit">
                <div class="mdui-list-item mdui-ripple nav-item strike">冲账</div>
                </shiro:hasPermission>
            </div>
        </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="meter:visit">
        <div class="mdui-collapse-item">
            <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">network_check</i>
                <div class="mdui-list-item-content mdui-text-color-blue">表具运行管理</div>
                <i class="mdui-collapse-item-arrow mdui-icon material-icons mdui-text-color-blue">keyboard_arrow_down</i>
            </div>
            <div class="mdui-collapse-item-body mdui-list" style="">
                <shiro:hasPermission name="meter:readmeter:visit">
                <div class="mdui-list-item mdui-ripple nav-item record">抄表</div>
                </shiro:hasPermission>
                <shiro:hasPermission name="meter:valve:visit">
                <div class="mdui-list-item mdui-ripple nav-item control">阀门控制</div>
                </shiro:hasPermission>
                <shiro:hasPermission name="meter:exception:visit">
                <div class="mdui-list-item mdui-ripple nav-item exception">异常情况</div>
                </shiro:hasPermission>
            </div>
        </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="querystats:visit">
        <div class="mdui-collapse-item">
            <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">assessment</i>
                <div class="mdui-list-item-content mdui-text-color-blue">查询统计</div>
                <i class="mdui-collapse-item-arrow mdui-icon material-icons mdui-text-color-blue">keyboard_arrow_down</i>
            </div>
            <div class="mdui-collapse-item-body mdui-list" style="">
                <shiro:hasPermission name="querystats:ardQuery:visit">
                <div class="mdui-list-item mdui-ripple nav-item ardQuery">IC卡查询</div>
                </shiro:hasPermission>
                <shiro:hasPermission name="querystats:account:visit">
                <div class="mdui-list-item mdui-ripple nav-item accountQuery">开户信息查询</div>
                </shiro:hasPermission>
                <shiro:hasPermission name="querystats:accountdetail:visit">
                <div class="mdui-list-item mdui-ripple nav-item userQuery">用户信息查询</div>
                </shiro:hasPermission>
                <shiro:hasPermission name="querystats:abnormaluser:visit">
                <div class="mdui-list-item mdui-ripple nav-item exceptionQuery">异常用户查询</div>
                </shiro:hasPermission>
                <shiro:hasPermission name="businessDataQuery:data:visit">
                <div class="mdui-list-item mdui-ripple nav-item businessDataQuery">营业数据查询</div>
                </shiro:hasPermission>
                <shiro:hasPermission name="querystats:report:visit">
                <div class="mdui-list-item mdui-ripple nav-item businessReportQuery">营业报表查询</div>
                </shiro:hasPermission>
            </div>
        </div>
        </shiro:hasPermission>
    </div>
</div>
<div class="mdui-container">
    <h1 class="mdui-text-color-blue container-title">公司简介</h1>
    <div class="mdui-typo mdui-typo-body-1 container-main">
        武汉蓝焰自动化应用技术有限责任公司系由武汉市天然气有限公司、武汉市燃气热力集团有限公司、港华投资有限公司、湖北震新机电设备技术开发有限公司投资组建的中港合资企业。公司以促进我国燃气事业现代化发展为宗旨，以高新技术为手段，致力于为燃气经营企业提供全方位、多层面的高品质产品，是燃气行业智能化方案及产品集成供应商。
        公司坐落于武汉市硚口区高新技术开发区，公司建立了完善的质量管理与评测体系，于2002年通过ISO9001质量管理体系认证，质量管理体系在2005年进行复评换证期间又一并通过了ISO14001环境管理体系认证，GB/T28001职业健康安全管理体系认证。从2005年至今，上述三个管理体系均一次通过了每年的第三方监督审核和每三年的到期再认证工作，且管理体系至今均持续有效运行。在经营管理上，根据公司“立足市场需求﹑稳定产品质量﹑推广应用经验﹑拓展国内市场”的经营方针，以丰富的产品链涵盖不同的细分市场，满足不同用户的多重需求，并将科学的营销概念和体贴入微的服务理念融于产品服务中。全新的竞争环境和不断变化的消费者需求激励我公司锐意求新，不断进取，以坚忍不拔的创业精神和精益求精的严格要求不断完善自我，从而形成了“追求卓越品质
        创造至上服务”的企业经营管理理念。
        公司现建有电脑自控装配流水线、自动包装线，整机自动综合检测台、标牌自动嵌装机、自动写卡机、锁螺丝机等自动化生产设备；建有完备的质量检测、控制体系和先进的气密性及计量检测设备；有自主研发的条码管理系统、 仓储管理系统
        、固定资产管理系统、IC卡表售卡管理系统、短信智能表售卡管理系统。目前已形成年产30万台智能燃气表的生产规模。产品行销天津、河北、河南、湖南、湖北、广东、山东等省市，是国内唯一一家在单一城市使用时间最长用户最多的智能表生产企业。公司采取“交钥匙”方式为用户提供健全优质的售后服务。设立了专门的客户服务部，对用户有关专业人员进行全方位系统的培训，包括了公司产品系统工作原理﹑安装﹑使用﹑售卡维修﹑管理等各项内容，指导维修服务人员熟悉掌握有关用户的报修﹑投诉等事项的技术处理。同时负责用户产品使用情况的随访跟踪。公司技术开发部﹑技术研究实验室，为技术开发研究建立良好的设备条件。在围绕公司核心产品完善改进的基础上，跟踪国内外相关产品的研制进展及应用情况等信息的研究，根据市场需求，全力投入力量进行相关产品的开发﹑研制工作。还与华中科技大学、武汉理工大学及其它院校﹑科研院所及湖北电信等建立了紧密的协作关系，为新项目产品的开发研制提供了必要的科研技术环境支持。公司荣获多项技术专利，并被授予湖北省高新技术企业。以市场为先导、以质量为保障、以技术为支撑，蓝焰人一定能在中国燃气行业成就了一段美丽的风景。
    </div>
    <object class="rw-comp" type="application/x-itst-activex" clsid="{6C8FD9AF-5668-45C8-8D9A-EBE02A7A2F39}"></object>
</div>

<script type="text/javascript">
    app.initIndex();
    app.getShiro = function (pname) {
        <c:forEach var="perm" items="${sessionScope.permissions}">
        if (pname === "${perm}") {
            return true;
        }
        </c:forEach>
    }

</script>
</body>
</html>