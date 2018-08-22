/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var app = {
    template:
            '<div class="row search-box">\n\
                <div class="col s3">\n\
                    <label>员工名称：<input type="text" class="field ename" name="ename" placeholder="员工名称"/></label>\n\
                </div>\n\
                <div class="col s3">\n\
                    <label>\n\
                        部门编号：\n\
                        <select class="field deptno" name="deptno">\n\
                            <option value="" selected>请选择</option>\n\
                            <option value="10">10</option>\n\
                            <option value="20">20</option>\n\
                            <option value="30">30</option>\n\
                        </select>\n\
                    </label>\n\
                </div>\n\
                <div class="col s3">\n\
                    <label>\n\
                        部门编号：\n\
                        <select class="field job" name="job">\n\
                            <option value="" selected>请选择</option>\n\
                            <option value="CLERK">职员</option>\n\
                            <option value="SALESMAN">销售员</option>\n\
                            <option value="ANALYST">分析师</option>\n\
                            <option value="MANAGER">经理</option>\n\
                            <option value="PRESIDENT">董事长</option>\n\
                        </select>\n\
                    </label>\n\
                </div>\n\
                <div class="col s3 align-center">\n\
                    <div class="waves-effect waves-light green lighten-2 btn search-button">\n\
                        <i class="material-icons">search</i>\n\
                    </div>\n\
                </div>\n\
            </div>\n\
            <div class="row operation-box">\n\
                <div class="col s4 align-center">\n\
                    <div class="waves-effect waves-light green lighten-2 btn operator add">\n\
                        <i class="material-icons">add</i>\n\
                        <span class="operation-text">新增</span>\n\
                    </div>\n\
                    <div class="waves-effect waves-light green lighten-2 btn operator edit">\n\
                        <i class="material-icons">edit</i>\n\
                        <span class="operation-text">编辑</span>\n\
                    </div>\n\
                    <div class="waves-effect waves-light green lighten-2 btn operator delete">\n\
                        <i class="material-icons">delete</i>\n\
                        <span class="operation-text">删除</span>\n\
                    </div>\n\
                </div>\n\
            </div>\n\
            <div class="row data-box">\n\
                <table class="highlight centered responsive-table">\n\
                    <thead class="fields">\n\
                        <tr>\n\
                            <th><label><input type="checkbox" class="selected"/><span>选中</span></label></th>\n\
                            <th class="empno">编号</th>\n\
                            <th class="ename">姓名</th>\n\
                            <th class="job">职务</th>\n\
                            <th class="mgr">上级</th>\n\
                            <th class="hiredate">时间</th>\n\
                            <th class="sal">工资</th>\n\
                            <th class="comm">补助</th>\n\
                            <th class="deptno">部门</th>\n\
                        </tr>\n\
                    </thead>\n\
                    <tbody class="records"></tbody>\n\
                </table>\n\
            </div>\n\
            <div class="row controller-box">\n\
                <span class="info">\n\
                    第<span class="pageNumber"></span>页\n\
                    /\n\
                    共<span class="pageCount"></span>页\n\
                </span>\n\
                <div class="controllers">\n\
                    <a class="waves-effect waves-light light-blue lighten-2 btn" name="first">首页</a>\n\
                    <a class="waves-effect waves-light light-blue lighten-2 btn" name="prev">上一页</a>\n\
                    <a class="waves-effect waves-light light-blue lighten-2 btn" name="next">下一页</a>\n\
                    <a class="waves-effect waves-light light-blue lighten-2 btn" name="last">尾页</a>\n\
                </div>\n\
                <input type="number" class="validate input-num" pattern="^\d*$" autocomplete="off"/>\n\
                <input type="button" class="waves-effect waves-light light-blue lighten-2 btn go" value="Go"/>\n\
                <select name="pageSize" class="set-size">\n\
                    <option value="5">5</option>\n\
                    <option value="8" selected="selected">8</option>\n\
                    <option value="10">10</option>\n\
                    <option value="20">20</option>\n\
                    <option value="30">30</option>\n\
                </select>\n\
            </div>',
    distTemplate:'<div class="mdui-table-fluid mdui-theme-accent-blue"></div>',
    orgTemplate :'<div class="mdui-table-fluid mdui-theme-accent-blue"></div>',
    getPaneContent: function (name) {
        var paneContent = '';
        switch (name) {
            /*
             * 系统管理：区域管理 机构管理 用户管理 角色管理 权限管理 字典管理 日志管理 公告管理
             */
            case 'dist':
                paneContent = this.distTemplate;
                break;
            case 'org':
                paneContent = this.orgTemplate;
                break;
            case 'user':
                paneContent = this.template;
                break;
            case 'role':
                paneContent = this.template;
                break;
            case 'permission':
                paneContent = this.template;
                break;
            case 'dictionary':
                break;
            case 'log':
                break;
            case 'announcement':
                break;
                /*
                 * 账户管理：表具入库 用户建档 挂表 开户 账户变更
                 */
            case 'inbound':
                break;
            case 'file':
                break;
            case 'install':
                break;
            case 'account':
                break;
            case 'alter':
                break;
                /*
                 * 充值缴费管理：预付费充值 补卡充值 后付费充值 发票管理
                 */
            case 'prePayment':
                break;
            case 'replaceCard':
                break;
            case 'postPayment':
                break;
            case 'invoice':
                break;
                /*
                 * 维修补气管理: 维修单录入 维修补气 补缴结算 IC卡初始化
                 */
            case 'input':
                break;
            case 'fillGas':
                break;
            case 'balance':
                break;
            case 'initCard':
                break;
                /*
                 * 账务处理：预冲账 冲账
                 */
            case 'preStrike':
                break;
            case 'strike':
                break;
                /*
                 * 表具运行管理：抄表 阀门控制 异常情况
                 */
            case 'record':
                break;
            case 'control':
                break;
            case 'exception':
                break;
                /*
                 * 查询统计：IC卡查询 开户信息查询 用户信息查询 异常用户查询 营业数据查询 营业报表查询
                 */
            case 'cardQuery':
                break;
            case 'accountQuery':
                break;
            case 'userQuery':
                break;
            case 'exceptionQuery':
                break;
            case 'businessDataQuery':
                break;
            case 'businessReportQuery':
                break;
        }
        return paneContent;
    },
    // 分页页面渲染
    render:function(context){
        var pane = context.pane;
        var theadElement = pane.getElementsByClassName('fields')[0];
        var fieldsElement = theadElement.children[0];
        var tbodyElement = pane.getElementsByClassName('records')[0];
        var controllers=pane.getElementsByClassName('controllers')[0];
        $.ajax({
            type: 'POST',
            url: context.url,
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify({
                pageNumber: context.pageNumber,
                pageSize: context.pageSize,
                fields:context.fields
            }),
            beforeSend: function (xhr) {
                xhr.withCredentials = true;
            },
            success:function(response){
                tbodyElement.innerHTML = '';
                var data = response.data;
                for (var i = 0, index = 0, limit = data.length; i < limit; i++) {
                    var tr = tbodyElement.insertRow(i);
                    for (var cell, j = 0, counter = fieldsElement.children.length; j < counter; j++) {
                        cell = tr.insertCell(j);
                        cell.className = fieldsElement.children[j].className;
                        cell.title = j===0 ? '选中':fieldsElement.children[j].innerHTML.trim();
                        cell.innerHTML = j === 0 ? '<label><input type="checkbox" class="selected"/><span></span></label>' : data[i][fieldsElement.children[j].className];
                    }
                }
                var pageCount = Math.ceil(response.rowCount/context.pageSize);
                pane.getElementsByClassName('pageNumber')[0].innerHTML = context.pageNumber<pageCount?context.pageNumber:pageCount;
                pane.getElementsByClassName('pageCount')[0].innerHTML = pageCount;
                var inputNumElement=pane.getElementsByClassName('input-num')[0];
                inputNumElement.min=pageCount<1?pageCount:1;
                inputNumElement.max=pageCount;
                if(context.pageNumber <= 1){
                    !controllers.children[0].classList.contains('disabled')&&controllers.children[0].classList.add('disabled');
                    !controllers.children[1].classList.contains('disabled')&&controllers.children[1].classList.add('disabled');
                    controllers.children[2].classList.contains('disabled')&&controllers.children[2].classList.remove('disabled');
                    controllers.children[3].classList.contains('disabled')&&controllers.children[3].classList.remove('disabled');
                }else if(context.pageNumber < pageCount){
                    for(var i=0,limit=controllers.children.length;i<limit;i++){
                        controllers.children[i].classList.remove('disabled');
                    }
                }else{
                    controllers.children[0].classList.contains('disabled')&&controllers.children[0].classList.remove('disabled');
                    controllers.children[1].classList.contains('disabled')&&controllers.children[1].classList.remove('disabled');
                    !controllers.children[2].classList.contains('disabled')&&controllers.children[2].classList.add('disabled');
                    !controllers.children[3].classList.contains('disabled')&&controllers.children[3].classList.add('disabled');
                }
                pageCount<2&&(function(){
                    for(var i=0,limit=controllers.children.length;i<limit;i++){
                        !controllers.children[i].classList.contains('disabled')&&controllers.children[i].classList.add('disabled');
                    }
                })();
                fieldsElement.children[0].children[0].children[0].checked=false;
                fieldsElement.children[0].children[0].onclick=function(){
                    for(var i=0,limit=tbodyElement.children.length;i<limit;i++){
                        var row=tbodyElement.children[i];
                        row.children[0].children[0].children[0].checked=this.children[0].checked;
                    }
                }
                tbodyElement.onclick=function(evt){
                    var self=this, srcEle=evt.target;
                    if(srcEle.className==='selected'){
                        srcEle.parentNode.onchange=function(){
                            var allChecked=true;
                            for(var i=0,limit=self.children.length;i<limit;i++){
                                var row=self.children[i];
                                if(!row.children[0].children[0].children[0].checked){
                                    allChecked=false;
                                }
                            }
                            fieldsElement.children[0].children[0].children[0].checked=allChecked;
                        }
                    }
                }
            }
        });
    },
    // 无分页页面渲染
    renderWithoutPage: function (context) {
        $.ajax({
            async : false,
            type: 'GET',
            url: context.url,
            contentType: 'application/json;charset=utf-8',
            beforeSend: function (xhr) {
                xhr.withCredentials = true;
            },
            success: function (response) {
                var data = response.data;
                context.tableData = data;
                if (app.table) {
                    app.table.refresh(data);
                } else {
                    contextTable=  context.table;
                    /*
                     *使用数据模板Getfields中可定义对应数据模板内容
                     */
                    var names = app.currentPageName;
                    app.table = context.table = app.createTable({
                        parent: '.mdui-table-fluid',
                        fields:app.getFieldNames(names),

                        //[

                         //   {
                        //     name: 'distName',
                        //     caption: '区域名称'
                        // }, {
                        //     name: 'distCode',
                        //     caption: '区域编码'
                        // }, {
                        //     name: 'distCategory',
                        //     caption: '区域类别'
                        // }, {
                        //     name: 'distAddress',
                        //     caption: '区域地址'
                        // }, {
                        //     name: 'distParentId',
                        //     caption: '父级区域'
                      //   }
                     //    ],
                        fields: app.Getfields(names),
                        data: data
                    });
                }
            }
        });
    },
    initPane: function (context) {
        var self=this;
        app.toolbar = app.createToolbar({
            parent: '.container-main',
             fields:app.headScreening(app.currentPageName)
            // [{
            //     name: 'add',
            //     caption: '新增'
            // }, {
            //     name: 'edit',
            //     caption: '编辑'
            // }, {
            //     name: 'delete',
            //     caption: '删除'
            // }, {
            //     name: 'distName',
            //     caption: '区域名称',
            //     type: 'input'
            // }, {
            //     name: 'distCode',
            //     caption: '区域编码',
            //     type: 'input'
            // }, {
            //     name: 'search',
            //     caption: '搜索'
            // }]
        });

        var pageSizeElement = context.pane.getElementsByClassName('set-size')[0];
        if (pageSizeElement) {
            self.render({
                url:context.url,
                pane:context.pane,
                pageNumber: context.pageNumber,
                pageSize:Number(pageSizeElement.options[pageSizeElement.selectedIndex].value),
                fields:fields
            });
            pageSizeElement.onchange = function () {
                var fields={};
                for(var i=0,limit=fieldElements.length;i<limit;i++){
                    var fieldElement=fieldElements[i];
                    switch(fieldElement.nodeName){
                        case 'INPUT':
                            fields[fieldElement.name]=fieldElement.value;
                            break;
                        case 'SELECT':
                            fields[fieldElement.name]=fieldElement.options[fieldElement.selectedIndex].value
                            break;
                    }
                }
                var parameterMap = {
                    url:context.url,
                    pane:context.pane,
                    pageNumber: 1,
                    pageSize:Number(this.options[this.selectedIndex].value),
                    fields:fields
                };
                self.render(parameterMap);
            };
            var inputNumberElement = context.pane.getElementsByClassName('input-num')[0];
            inputNumberElement.oninput = function () {
                if (isNaN(this.value)) {
                    this.value = '';
                } else {
                    this.value = Number(this.value);
                }
            };
            context.pane.getElementsByClassName('go')[0].onclick = function (){
                var fields={};
                for(var i=0,limit=fieldElements.length;i<limit;i++){
                    var fieldElement=fieldElements[i];
                    switch(fieldElement.nodeName){
                        case 'INPUT':
                            fields[fieldElement.name]=fieldElement.value;
                            break;
                        case 'SELECT':
                            fields[fieldElement.name]=fieldElement.options[fieldElement.selectedIndex].value
                            break;
                    }
                }
                var parameterMap={
                    url:context.url,
                    pane:context.pane,
                    pageSize:Number(pageSizeElement.options[pageSizeElement.selectedIndex].value),
                    fields:fields
                },pane=context.pane;
                if(inputNumberElement.value<inputNumberElement.min){
                    parameterMap.pageNumber = inputNumberElement.min;
                    self.render(parameterMap);
                    inputNumberElement.value='';
                }else if(inputNumberElement.value<=inputNumberElement.max){
                    parameterMap.pageNumber=inputNumberElement.value;
                    self.render(parameterMap);
                    inputNumberElement.value='';
                }else{
                    parameterMap.pageNumber=inputNumberElement.max;
                    self.render(parameterMap);
                    inputNumberElement.value='';
                }
            };
            var controllerElement = context.pane.getElementsByClassName('controller-box')[0];
            controllerElement.onclick = function (evt){
                var fields={};
                for(var i=0,limit=fieldElements.length;i<limit;i++){
                    var fieldElement=fieldElements[i];
                    switch(fieldElement.nodeName){
                        case 'INPUT':
                            fields[fieldElement.name]=fieldElement.value;
                            break;
                        case 'SELECT':
                            fields[fieldElement.name]=fieldElement.options[fieldElement.selectedIndex].value
                            break;
                    }
                }
                var pane=context.pane;
                var pageNumber = pane.getElementsByClassName('pageNumber')[0].innerHTML === '' ? 0 : Number(pane.getElementsByClassName('pageNumber')[0].innerHTML);
                var pageCount = pane.getElementsByClassName('pageCount')[0].innerHTML === '' ? 0 : Number(pane.getElementsByClassName('pageCount')[0].innerHTML);
                var parameterMap = {
                    url:context.url,
                    pane:context.pane,
                    pageSize:Number(pageSizeElement.options[pageSizeElement.selectedIndex].value),
                    fields:fields
                };
                var srcElement = evt.target;
                switch (srcElement.name){
                    case 'first':
                        (function(){
                            if(srcElement.classList.contains('disabled')){
                                return false;
                            }else{
                                parameterMap.pageNumber = pageCount > 0 ? 1 : 0;
                                self.render(parameterMap);
                            }
                        })();
                        break;
                    case 'prev':
                        (function(){
                            if(srcElement.classList.contains('disabled')||--pageNumber < 1){
                                return false;
                            }
                            else{
                                parameterMap.pageNumber = pageNumber;
                                self.render(parameterMap);
                            }
                        })();
                        break;
                    case 'next':
                        (function(){
                            if(srcElement.classList.contains('disabled')||++pageNumber>pageCount){
                                return false;
                            }else{
                                parameterMap.pageNumber = pageNumber;
                                self.render(parameterMap);
                            }
                        })();
                        break;
                    case 'last':
                        (function(){
                            if(srcElement.classList.contains('disabled')){
                                return false;
                            }else{
                                parameterMap.pageNumber = pageCount;
                                self.render(parameterMap);
                            }
                        })();
                        break;
                }
            }
        } else {
            self.renderWithoutPage({
                url:context.url,
                pane:context.pane
            });
        }
    }
};

