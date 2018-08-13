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
                    <div class="waves-effect waves-light green lighten-4 btn search-button">\n\
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
                    <a class="waves-effect waves-light light-blue lighten-4 btn" name="first">首页</a>\n\
                    <a class="waves-effect waves-light light-blue lighten-4 btn" name="prev">上一页</a>\n\
                    <a class="waves-effect waves-light light-blue lighten-4 btn" name="next">下一页</a>\n\
                    <a class="waves-effect waves-light light-blue lighten-4 btn" name="last">尾页</a>\n\
                </div>\n\
                <input type="number" class="validate input-num" pattern="^\d*$" autocomplete="off"/>\n\
                <input type="button" class="waves-effect waves-light light-blue lighten-4 btn go" value="Go"/>\n\
                <select name="pageSize" class="set-size">\n\
                    <option value="5">5</option>\n\
                    <option value="8" selected="selected">8</option>\n\
                    <option value="10">10</option>\n\
                    <option value="20">20</option>\n\
                    <option value="30">30</option>\n\
                </select>\n\
            </div>',
    getPaneContent: function (name) {
        var paneContent = '';
        switch (name) {
            /*
             * 系统管理：区域管理 机构管理 用户管理 角色管理 权限管理 字典管理 日志管理 公告管理
             */
            case 'regional':
                paneContent = this.template;
                break;
            case 'organization':
                paneContent = this.template;
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
    initPane: function (context) {
        var self=this;
        var searchBoxElement=context.pane.getElementsByClassName('search-box')[0];
        var searchButtonElement=searchBoxElement.getElementsByClassName('search-button')[0];
        var fieldElements=searchBoxElement.getElementsByClassName('field'),fields={};
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
        searchButtonElement.onclick=function(){
            var fields={};
            for(var i=0,limit=fieldElements.length;i<limit;i++){
                var fieldElement=fieldElements[i];
                switch(fieldElement.nodeName){
                    case 'INPUT':
                        fields[fieldElement.name]=fieldElement.value;
                        break;
                    case 'SELECT':
                        fields[fieldElement.name]=fieldElement.options[fieldElement.selectedIndex].value;
                        break;
                }
            }
            var parameterMap={
                url:context.url,
                pane:context.pane,
                pageNumber:1,
                pageSize:Number(pageSizeElement.options[pageSizeElement.selectedIndex].value),
                fields: fields
            }
            self.render(parameterMap);
        }
        var operationBoxElement=context.pane.getElementsByClassName('operation-box')[0];
        //var operations=['add','edit','delete'];
        
        var operationElements=operationBoxElement.getElementsByClassName('operator');
        for(var i=0,limit=operationElements.length;i<limit;i++){
            var operationElement=operationElements[i];
            operationElement.onclick=function(){
                var theadElement = context.pane.getElementsByClassName('fields')[0];
                var tbodyElement = context.pane.getElementsByClassName('records')[0];
                this.classList.contains('add')&&(function(){
                    var elem = document.querySelector('.modal');
                    var modalContent=elem.getElementsByClassName('modal-content')[0];
                    modalContent.innerHTML='';
                    (function(){
                           var titleElement=document.createElement('h4');
                           titleElement.innerHTML='新增';
                           modalContent.appendChild(titleElement);
                           var getElementValue=function(elem){
                               switch(elem.nodeName){
                                   case 'INPUT':
                                       return elem[elem.name]=elem.value;
                                       break;
                                   case 'SELECT':
                                       return elem[elem.name]=elem.options[elem.selectedIndex].value;
                                       break;
                               }
                           }
                           var rowElem=document.createElement('div');
                           rowElem.className='row';
                           var formElement=document.createElement('form');
                           formElement.className='col s12';
                           var fieldsElement=theadElement.children[0];
                           for(var i=1,limit=fieldsElement.children.length;i<limit;i++){
                                var cell=fieldsElement.children[i];
                                var rowElement=document.createElement('div');
                                rowElement.className='row';
                                var fieldContainer=document.createElement('div');
                                fieldContainer.className='col s12';
                                var labelElement=document.createElement('div');
                                labelElement.innerHTML=cell.title;
                                var inputElement=document.createElement('input');
                                inputElement.type='text';
                                inputElement.style.textAlign='center';
                                inputElement.name=cell.className;
                                inputElement.className='field';
                                inputElement.placeholder=cell.innerHTML;
                                //inputElement.value=cell.innerHTML;
                                labelElement.appendChild(inputElement);
                                fieldContainer.appendChild(labelElement);
                                rowElement.appendChild(fieldContainer);
                                formElement.appendChild(rowElement);
                           }                           
                           rowElem.appendChild(formElement);
                           modalContent.appendChild(rowElem);
                    })();
                        var modalFooter=elem.getElementsByClassName('modal-footer')[0];
                        modalFooter.children[0].innerHTML='保存';
                        modalFooter.children[0].onclick=function(){
                            console.log('save');
                            var fields=modalContent.getElementsByClassName('field');
                            var record={};
                            for(var i=0,limit=fields.length;i<limit;i++){
                                var field=fields[i];
                                record[field.name]=field.value;
                            }
                            $.ajax({
                                type: 'POST',
                                url: ['add','EMP','.do'].join(''),
                                //url: ['edit',context.pane.id.replace('-pane','').toUpperCase(),'.do'].join(''),
                                contentType: 'application/json;charset=utf-8',
                                data:JSON.stringify(record),
                                beforeSend: function (xhr){
                                    xhr.withCredentials = true;
                                },
                                success: function (response){
                                    console.log(response);
                                    M.toast({
                                        html: response.message,
                                        classes: 'rounded repaint-toast'
                                    });
                                    if(response.done){
                                        var parameterMap = {
                                            url:context.url,
                                            pane:context.pane,
                                            pageNumber: 1,
                                            pageSize:Number(pageSizeElement.options[pageSizeElement.selectedIndex].value),
                                            fields:fields
                                        };
                                        self.render(parameterMap);
                                    }
                                }
                            });
                        }
                        modalFooter.children[1].innerHTML='取消';
                        var instance = M.Modal.init(elem, {});
                        instance.open();
                })();
                this.classList.contains('edit')&&(function(){
                    var rows=[];
                    for(var i=0,limit=tbodyElement.children.length;i<limit;i++){
                        var row=tbodyElement.children[i];
                        row.children[0].children[0].children[0].checked&&rows.push(row);
                    }
                    if(rows.length!==1){
                        M.toast({
                            html: '请选择一条记录进行编辑',
                            classes: 'rounded repaint-toast'
                        });
                    }else{
                        var elem = document.querySelector('.modal');
                        var modalContent=elem.getElementsByClassName('modal-content')[0];
                        modalContent.innerHTML='';
                        (function(){
                           var titleElement=document.createElement('h4');
                           titleElement.innerHTML='编辑';
                           modalContent.appendChild(titleElement);
                           var row=rows[0];console.log(row);
                           var rowElem=document.createElement('div');
                           rowElem.className='row';
                           var formElement=document.createElement('form');
                           formElement.className='col s12';
                           for(var i=1,limit=row.children.length;i<limit;i++){
                                var cell=row.children[i];
                                var rowElement=document.createElement('div');
                                rowElement.className='row';
                                var fieldContainer=document.createElement('div');
                                fieldContainer.className='col s12';
                                var labelElement=document.createElement('div');
                                labelElement.innerHTML=cell.title;
                                var inputElement=document.createElement('input');
                                inputElement.type='text';
                                inputElement.style.textAlign='center';
                                inputElement.name=cell.className;
                                inputElement.className='field';
                                inputElement.placeholder=cell.title;
                                inputElement.value=cell.innerHTML;
                                labelElement.appendChild(inputElement);
                                fieldContainer.appendChild(labelElement);
                                rowElement.appendChild(fieldContainer);
                                formElement.appendChild(rowElement);
                           }
                           rowElem.appendChild(formElement);
                           modalContent.appendChild(rowElem);
                        })();
                        var modalFooter=elem.getElementsByClassName('modal-footer')[0];
                        modalFooter.children[0].innerHTML='保存';
                        modalFooter.children[1].innerHTML='取消';
                        modalFooter.children[0].onclick=function(){
                            var fields=modalContent.getElementsByClassName('field');
                            var record={};
                            for(var i=0,limit=fields.length;i<limit;i++){
                                var field=fields[i];
                                record[field.name]=field.value;
                            }
                            $.ajax({
                                type: 'POST',
                                url: ['edit','EMP','.do'].join(''),
                                //url: ['edit',context.pane.id.replace('-pane','').toUpperCase(),'.do'].join(''),
                                contentType: 'application/json;charset=utf-8',
                                data:JSON.stringify(record),
                                beforeSend: function (xhr){
                                    xhr.withCredentials = true;
                                },
                                success: function (response){
                                    console.log(response);
                                    M.toast({
                                        html: response.message,
                                        classes: 'rounded repaint-toast'
                                    });
                                    if(response.done){
                                        var row=rows[0];
                                        for(var i=1,limit=row.children.length;i<limit;i++){
                                            var cell=row.children[i];
                                            cell.innerHTML=record[cell.className];
                                        }
                                    }
                                }
                            });
                        };
                        var instance = M.Modal.init(elem, {});
                        instance.open();
                        console.log(instance.el);
                        console.log(instance.options);
                    }
                })();
                this.classList.contains('delete')&&(function(){
                    var rows=[];
                    for(var i=0,limit=tbodyElement.children.length;i<limit;i++){
                        var row=tbodyElement.children[i];
                        row.children[0].children[0].children[0].checked&&rows.push(row);
                    }
                    if(rows.length===0){
                        M.toast({
                            html: '请至少选择一条记录删除',
                            classes: 'rounded repaint-toast'
                        });
                    }else{
                        var ele = document.querySelector('.tip');
                        var modalContent=ele.getElementsByClassName('modal-content')[0];
                        modalContent.innerHTML='';
                        var titleElement=document.createElement('h6');
                        titleElement.innerHTML='提示信息';
                        var cueElement=document.createElement('div');
                        cueElement.className='align-center';
                        cueElement.innerHTML='确定删除选中记录吗？';
                        modalContent.appendChild(titleElement);
                        modalContent.appendChild(cueElement);
                        var instance = M.Modal.init(ele, {
                            startingTop:'30%',
                            endingTop:'36%'
                        });
                        instance.open();
                        var modalFooter=ele.getElementsByClassName('modal-footer')[0];
                        modalFooter.children[0].onclick=function(){
                        var records=[];
                        var fieldsElement=theadElement.children[0];
                        for(var i=0,limit=rows.length;i<limit;i++){
                            var row=rows[i],record={};
                            for(var j=1,size=fieldsElement.children.length;j<size;j++){
                                var fieldElement=fieldsElement.children[j],cell=row.children[j];
                                record[fieldElement.className]=cell.innerHTML;
                            }
                            records.push(record);
                        }
                        $.ajax({
                                type: 'POST',
                                url: ['deleteBatch','EMP','.do'].join(''),
                                contentType: 'application/json;charset=utf-8',
                                data:JSON.stringify(records),
                                beforeSend: function (xhr){
                                    xhr.withCredentials = true;
                                },
                                success: function (response){
                                    console.log(response);
                                    M.toast({
                                        html: response.message,
                                        classes: 'rounded repaint-toast'
                                    });
                                    if(response.done){
                                        var parameterMap = {
                                            url:context.url,
                                            pane:context.pane,
                                            pageNumber: 1,
                                            pageSize:Number(pageSizeElement.options[pageSizeElement.selectedIndex].value),
                                            fields:fields
                                        };
                                        self.render(parameterMap);
                                    }
                                }
                            });
                        }
                    }
                })();
            };
        }
        
        var pageSizeElement = context.pane.getElementsByClassName('set-size')[0];
        //var pageSize = Number(pageSizeElement.options[pageSizeElement.selectedIndex].value);
        self.render({
            url:context.url,
            pane:context.pane,
            pageNumber: context.pageNumber,
            pageSize:Number(pageSizeElement.options[pageSizeElement.selectedIndex].value),
            fields:fields
        });
//        var shadeElement = document.getElementsByClassName('shade')[0];
//        shadeElement.hidden = false;

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
                    })()

                    break;
                case 'prev':
//                    --pageNumber > 0 && (function(){
//                        parameterMap.pageNumber = pageNumber;
//                        self.render(parameterMap);
//                    })();
                    (function(){
                       if(srcElement.classList.contains('disabled')||--pageNumber < 1){
                           return false;
                       } 
                       else{
//                           if(--pageNumber > 0){
                                parameterMap.pageNumber = pageNumber;
                                self.render(parameterMap);
//                           }
                       }
                    })()
                    break;
                case 'next':
//                    ++pageNumber <= pageCount && (function(){
//                        parameterMap.pageNumber = pageNumber;
//                        self.render(parameterMap);
//                    })();
                    (function(){
                        if(srcElement.classList.contains('disabled')||++pageNumber>pageCount){
                            return false;
                        }else{
//                              if(++pageNumber <= pageCount){
                                    parameterMap.pageNumber = pageNumber;
                                    self.render(parameterMap);
//                              }
                        }
                    })()
                    break;
                case 'last':
                    (function(){
                        if(srcElement.classList.contains('disabled')){
                            return false;
                        }else{
                            parameterMap.pageNumber = pageCount;
                            self.render(parameterMap); 
//                            srcElement.classList.add('disabled');
//                            srcElement.previousElementSibling.classList.add('disabled');
                        }
                    })();
                    break;
            }
        }
    }
};

