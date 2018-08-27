/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global app, M */

app.initIndex = function () {
    document.addEventListener('DOMContentLoaded', function () {
        var elems = document.querySelectorAll('.mdui-drawer .mdui-list');
        M.Collapsible.init(elems, {});
        var containerElement = document.querySelector('.mdui-container');
        elems[0].onclick = function (event) {
            var eventSrc = event.target;
            if (eventSrc.classList.contains('nav-item')) {
                var name = eventSrc.classList[eventSrc.classList.length - 1];
                if (!app.currentPageName || app.currentPageName !== name) {
                    // 切换页面将table和toolbar信息清除
                    app.removeEvent();
                    app.table = null;
                    app.toolbar = null;
                    app.currentPageName = name;
                    var text = eventSrc.innerText;
                    var titleElement = containerElement.children[0];
                    var mainElement = containerElement.children[1];
                    titleElement.innerHTML = text;
                    mainElement.innerHTML = app.getPaneContent(name);
                    app.initPane({
                        pane: mainElement,
                        url: name + '/listData.do'
                    });
                    app.initEvent();
                }
            }
        };
        $(document).click(function(){
            $(".tree-combobox-panel").hide();
        });
        // mdui.snackbar("系统异常", {
        //     timeout: 4000,
        //     buttonColor: 'blue',
        //     position: 'top'
        // })
    });
};

app.initEvent = function () {
    var formNames =app.currentPageName
    var main = $('.container-main');
    var table = app.table;
    var fields = table.getFields();
    main.on('add', function () {
        var dialog = mdui.dialog({
            title: '新增',
            content: ' ',
            buttons: [{
                text: '确认',
                onClick: function () {
                    var data = form.getData();
                    $.ajax({
                        type: 'POST',
                        url: app.currentPageName + '/add.do',
                        contentType: 'application/x-www-form-urlencoded',
                        data: data,
                        beforeSend: function (xhr) {
                            xhr.withCredentials = true;
                        },
                        success: function (response) {
                            response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                            if (response.status) {
                                app.renderWithoutPage({
                                    url: app.currentPageName + '/listData.do'
                                });
                            }
                        }
                    });
                }
            }, {
                text: '取消'
            }]
        });
        $(".tree-combobox-panel").remove();
        var form = app.createForm({
            parent: '.mdui-dialog-content',
            fields:app.addFormfields[formNames]
        });
        dialog.handleUpdate();
    });
    main.on('edit', function () {
        if (table.getSelectedDatas().length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (table.getSelectedDatas().length > 1) {
            app.message('只能选择一条数据');
            return;
        }
        var dialog = mdui.dialog({
            title: '编辑',
            content: ' ',
            buttons: [{
                text: '确认',
                onClick: function () {
                    var data = form.getData();
                    $.ajax({
                        type: 'POST',
                        url: app.currentPageName + '/edit.do',
                        contentType: 'application/x-www-form-urlencoded',
                        data: data,
                        beforeSend: function (xhr) {
                            xhr.withCredentials = true;
                        },
                        success: function (response) {
                            console.log(response);
                            response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                            if (response.status) {
                                app.renderWithoutPage({
                                    url: app.currentPageName + '/listData.do'
                                });
                            }
                        }
                    });
                }
            }, {
                text: '取消'
            }]
        });
        $(".tree-combobox-panel").remove();
        var form = app.createForm({
            parent: '.mdui-dialog-content',
            fields: app.editFormFields[formNames],
            data: table.getSelectedDatas()[0]
        });
        dialog.handleUpdate();
    });
    main.on('delete', function () {
        if (table.getSelectedDatas().length === 0) {
            M.toast({
                html: '请至少选择一条数据',
                classes: 'rounded repaint-toast'
            });
            return;
        }
        mdui.dialog({
            title: '删除',
            content: '确认删除选中数据？',
            buttons: [{
                text: '确认',
                onClick: function () {
                    var names= app.currentPageName;
                    var deleteName = app.deleteNames[names];
                    var data = app.table.getSelectedIds(deleteName);
                    $.ajax({
                        type: 'POST',
                        url: app.currentPageName + '/delete.do',
                        data:  {ids: data},
                        beforeSend: function (xhr) {
                            xhr.withCredentials = true;
                        },
                        success: function (response) {
                            console.log(response);
                            response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                            if (response.status) {
                                app.renderWithoutPage({
                                    url: app.currentPageName + '/listData.do'
                                });
                            }
                        }
                    });
                }
            }, {
                text: '取消'
            }]
        });
    });
    main.on('search', function () {
        var data = app.toolbar.getInputsData();
        $.ajax({
            type: 'POST',
            url: app.currentPageName + '/search.do',
            contentType: 'application/x-www-form-urlencoded',
            data: data,
            beforeSend: function (xhr) {
                xhr.withCredentials = true;
            },
            success: function (response) {
                console.log(response);
                response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                app.table.refresh(response.data)
            }
        });
    });
};

app.removeEvent = function () {
    $('.container-main').off();
};

/*
* table fields
*/
app.tableFields = {
    dist: [{
        name: 'distName',
        caption: '区域名称'
    }, {
        name: 'distCode',
        caption: '区域编码'
    }, {
        name: 'distCategory',
        caption: '区域类别'
    }, {
        name: 'distAddress',
        caption: '区域地址'
    }, {
        name: 'distParentId',
        caption: '父级区域'
    }],
    org: [{
        name: 'orgId',
        caption: '机构ID'
    }, {
        name: 'orgName',
        caption: '机构名称'
    }, {
        name: 'orgCode',
        caption: '机构编码'
    }, {
        name: 'orgCategory',
        caption: '机构类别'
    }, {
        name: 'orgParentId',
        caption: '父级机构ID'
    }],
    dic: [{
        name: 'dictId',
        caption: 'ID'
    }, {
        name: 'dictKey',
        caption: '字典键'
    }, {
        name: 'dictValue',
        caption: '字典值'
    }, {
        name: 'dictCategory',
        caption: '字典类型'
    }, {
        name: 'dictSort',
        caption: '序号'
    }],
    permission: [{
        name: 'permName',
        caption: '权限名称'
    }, {
        name: 'permCaption',
        caption: '权限标题'
    }, {
        name: 'permParentCaption',
        caption: '菜单名称'
    }],
    role: [{
        name: 'roleName',
        caption: '角色名称'
    }],
    entry: [{
        name: 'meterCode',
        caption: '表具编码'
    }, {
        name: 'meterStopCode',
        caption: '表止码'
    }, {
        name: 'meterCategory',
        caption: '表具类别'
    }, {
        name: 'meterType',
        caption: '表具型号'
    }, {
        name: 'meterDirection',
        caption: '表向'
    }, {
        name: 'meterProdDate',
        caption: '表具生产日期'
    }, {
        name: 'meterEntryDate',
        caption: '表具入库时间'
    }]
};
/*
 *数据字典
 */
dictionary = function(formNames){
    var datas;
    if(formNames=='org'){

        $.ajax({
            async:false,
            type: 'POST',
            url: 'dic/dictByType.do',
            contentType: 'application/x-www-form-urlencoded',
            data: { orgCategory: "org_type"},
            success: function (list) {
                console.log(list);
                if(list.data!= null){
                     datas = list.data;
                    for(var i=0;i<datas.length;i++){
                       // dictionaryList= dictionaryList+'{'+"key"+":"+list.data[i].dictKey+","+"value"+":"+list.data[i].dictValue+'},';
                        datas[i].key = datas[i].dictKey;
                        datas[i].value = datas[i].dictValue;

                    }
                }
            }
        });

    }
    return datas;
};
/*
 *数据字典页面编辑时调用
 */
editFormDictionary = function(formNames){
    var datas;
    if(formNames=='org'){

        $.ajax({
            async:false,
            type: 'POST',
            url: 'dic/dictByType.do',
            contentType: 'application/x-www-form-urlencoded',
            data: { orgCategory: "org_type"},
            success: function (list) {
                console.log(list);
                if(list.data!= null){
                    datas = list.data;
                    for(var i=0;i<datas.length;i++){
                        // dictionaryList= dictionaryList+'{'+"key"+":"+list.data[i].dictKey+","+"value"+":"+list.data[i].dictValue+'},';
                        datas[i].key = datas[i].dictKey;
                        datas[i].value = datas[i].dictKey;

                    }
                }
            }
        });

    }
    return datas;
};
/*
 *数据字典
 */
dictionaryTable = function(tableData){
console.log("加载table时数据字典请求中...")
        $.ajax({
            async:false,
            type: 'POST',
            url: 'dic/dictByType.do',
            contentType: 'application/x-www-form-urlencoded',
            data: { orgCategory: "org_type"},
            success: function (list) {

                if(list.data!= null){
                    datas = list.data;
                   for(var i=0;i<tableData.length;i++){
                       for(var j=0;j<datas.length;j++){
                           if((tableData && datas) && tableData[i].orgCategory == datas[j].dictValue){

                               tableData[i].orgCategory =  datas[j].dictKey

                           }
                       }
                   }
                }
            }
        });
    console.log("数据请求完毕...")
    return tableData;
};
/*
 *新增时弹出框,列显示
 */

app.addFormfields = {
    dist: [{
        name: 'distName',
        caption: '区域名称'
    }, {
        name: 'distCode',
        caption: '区域编码'
    }, {
        name: 'distCategory',
        caption: '区域类别'
    }, {
        name: 'distAddress',
        caption: '区域地址'
    }, {
        name: 'distParentId',
        caption: '父级区域'
    }],
    org: [{
        name: 'orgName',
        caption: '机构名称'
    }, {
        name: 'orgCode',
        caption: '机构编码'
    }, {
        name: 'orgCategory',
        caption: '机构类别',
        type : 'listcombobox',
        options: dictionary("org")
    }, {
        name: 'orgParentId',
        caption: '父级机构ID'
    }, {
        name: 'remarks',
        caption: '备注信息'
    }],
    dic: [{
        name: 'dictKey',
        caption: '字典键'
    }, {
        name: 'dictValue',
        caption: '字典值'
    }, {
        name: 'dictCategory',
        caption: '字典类型'
    }, {
        name: 'dictSort',
        caption: '序号'
    }],
    permission: [{
        name: 'permName',
        caption: '权限名称'
    }, {
        name: 'permCaption',
        caption: '权限标题'
    }, {
        name: 'permParentId',
        caption: '菜单名称' ,
        type:'treecombobox' ,
        options: {
            idKey: 'permId',
            pIdKey: 'permParentId',
            name: 'permId',
            N : '',
            Y : '',
            chkStyle: 'radio',
            radioType: "all",
            nodes : ajaxTreeCombobox('permission/listAllMenus.do')
        }
     }, {
        name: 'isButton',
        caption: '是否按钮',
        type : 'listcombobox',
        options: [{
            key: '是',
            value: 'true'
        }, {
            key: '否',
            value: 'false'
        }]
    }, {
        name: 'remarks',
        caption: '备注'
    }],
    role: [{
        name: 'roleName', caption: '角色名称'
    }, {
        name: 'distIdList', caption: '角色所属地区',
        type:'treecombobox' ,
        options: {
            idKey: 'distId',
            pIdKey: 'distParentId',
            name: 'distId',
            N : 's',
            Y : 'p',
            nodes : ajaxTreeCombobox('dist/listData.do')
        }
    }, {
        name: 'orgIdList', caption: '角色所属机构',
        type:'treecombobox' ,
        options: {
            idKey: 'orgId',
            pIdKey: 'orgParentId',
            name: 'orgId',
            N : 's',
            Y : 'p',
            nodes : ajaxTreeCombobox('org/listData.do')
        }
    }, {
        name: 'permIdList', caption: '角色拥有权限',
        type:'treecombobox' ,
        options: {
            idKey: 'permId',
            pIdKey: 'permParentId',
            name: 'permId',
            N : 's',
            Y : 'p',
            nodes : ajaxTreeCombobox('permission/listAllMenusAndPerms.do')
        }
    }],
    entry: [{
        name: 'meterCode',
        caption: '表具编号'
    }, {
        name: 'meterStopCode',
        caption: '表止码'
    }, {
        name: 'meterCategory',
        caption: '表具类别'
    }, {
        name: 'meterType',
        caption: '表具型号'
    }, {
        name: 'meterDirection',
        caption: '表向'
    }, {
        name: 'meterProdDate',
        caption: '表具生产日期'
    }, {
        name: 'meterEntryDate',
        caption: '表具入库时间'
    }]
};

function ajaxTreeCombobox(url){
    var data = null;
    $.ajax({
        async: false,
        type: 'POST',
                url: url,
                contentType: 'application/json;charset=utf-8',
                beforeSend: function (xhr) {
                    xhr.withCredentials = true;
                },
                success: function (response) {
                     data = response.data;
                }
            });
    return data;
}
/*
 *修改时弹出框,列显示
 */

app.editFormFields = {
    dist: [{
        name: 'distName',
        caption: '区域名称'
    }, {
        name: 'distCode',
        caption: '区域编码'
    }, {
        name: 'distCategory',
        caption: '区域类别'
    }, {
        name: 'distAddress',
        caption: '区域地址'
    }, {
        name: 'distParentId',
        caption: '父级区域'
    }],
    org: [{
        name: 'orgName',
        caption: '机构名称'
    }, {
        name: 'orgCode',
        caption: '机构编码'
    }, {
        name: 'orgCategory',
        caption: '机构类别',
        type : 'listcombobox',
        options: editFormDictionary("org")
    }, {
        name: 'orgParentId',
        caption: '父级机构ID'
    }],
    dic: [{
        name: 'dictKey',
        caption: '字典键'
    }, {
        name: 'dictValue',
        caption: '字典值'
    }, {
        name: 'dictCategory',
        caption: '字典类型'
    }, {
        name: 'dictSort',
        caption: '序号'
    }],
    permission: [{
        name: 'permName',
        caption: '权限名称'
    }, {
        name: 'permCaption',
        caption: '权限标题'
    }, {
        name: 'permParentId',
        caption: '菜单名称',
        type:'treecombobox' ,
        options: {
            idKey: 'permId',
            pIdKey: 'permParentId',
            name: 'permId',
            N : '',
            Y : '',
            chkStyle: 'radio',
            radioType: "all",
            nodes : ajaxTreeCombobox('permission/listAllMenus.do')
        }
    }, {
        name: 'isButton',
        caption: '是否按钮',
        type : 'listcombobox',
        options: [{
            key: '是',
            value: 'true'
        }, {
            key: '否',
            value: 'false'
        }]
    }, {
        name: 'remarks',
        caption: '备注'
    }],
    role: [{
        name: 'roleName',
        caption: '角色名称'
    }, {
        name: 'distIdList',
        caption: '角色所属地区',
        type:'treecombobox' ,
        options: {
            idKey: 'distId',
            pIdKey: 'distParentId',
            name: 'distId',
            N : 's',
            Y : 'p',
            nodes : ajaxTreeCombobox('dist/listData.do')
        }
    }, {
        name: 'orgIdList',
        caption: '角色所属机构',
        type:'treecombobox' ,
        options: {
            idKey: 'orgId',
            pIdKey: 'orgParentId',
            name: 'orgId',
            N : 's',
            Y : 'p',
            nodes : ajaxTreeCombobox('org/listData.do')
        }
    }, {
        name: 'permIdList',
        caption: '角色拥有权限',
        type:'treecombobox' ,
        options: {
            idKey: 'permId',
            pIdKey: 'permParentId',
            name: 'permId',
            N : 's',
            Y : 'p',
            nodes : ajaxTreeCombobox('permission/listAllMenusAndPerms.do')
        }
    }],
    entry: [{
        name: 'meterCode',
        caption: '表具编码'
    }, {
        name: 'meterStopCode',
        caption: '表止码'
    }, {
        name: 'meterCategory',
        caption: '表具类别'
    }, {
        name: 'meterType',
        caption: '表具型号'
    }, {
        name: 'meterDirection',
        caption: '表向'
    }, {
        name: 'meterProdDate',
        caption: '表具生产日期'
    }, {
        name: 'meterEntryDate',
        caption: '表具入库时间'
    }]
};

/*
 *头部筛选查询列
 */
app.headScreening = {
    dist: [{
        name: 'add',
        caption: '新增'
    }, {
        name: 'edit',
        caption: '编辑'
    }, {
        name: 'delete',
        caption: '删除'
    }, {
        name: 'distName',
        caption: '区域名称',
        type: 'input'
    }, {
        name: 'distCode',
        caption: '区域编码',
        type: 'input'
    }, {
        name: 'search',
        caption: '搜索'
    }],
    org: [{
        name: 'add',
        caption: '新增'
    }, {
        name: 'edit',
        caption: '编辑'
    }, {
        name: 'delete',
        caption: '删除'
    }, {
        name: 'orgId',
        caption: '机构ID',
        type: 'input'
    }, {
        name: 'orgCode',
        caption: '机构编码',
        type: 'input'
    }, {
        name: 'orgName',
        caption: '机构名称',
        type: 'input'
    }, {
        name: 'search',
        caption: '搜索'
    }],
    dic: [{
        name: 'add',
        caption: '新增'
    }, {
        name: 'edit',
        caption: '编辑'
    }, {
        name: 'delete',
        caption: '删除'
    }, {
        name: 'dictId',
        caption: '字典ID',
        type: 'input'
    }, {
        name: 'dictCategory',
        caption: '字典类型',
        type: 'input'
    }, {
        name: 'search',
        caption: '搜索'
    }],
    permission: [{
        name: 'add',
        caption: '新增'
    }, {
        name: 'edit',
        caption: '编辑'
    }, {
        name: 'delete',
        caption: '删除'
    }, {
        name: 'permName',
        caption: '权限名称',
        type: 'input'
    }, {
        name: 'permCaption',
        caption: '权限标题',
        type: 'input'
    }, {
        name: 'menuName',
        caption: '菜单名称',
        type: 'input'
    }, {
        name: 'search',
        caption: '搜索'
    }],
    role: [{
        name: 'add',
        caption: '新增'
    }, {
        name: 'edit',
        caption: '编辑'
    }, {
        name: 'delete',
        caption: '删除'
    }, {
        name: 'roleName',
        caption: '角色名称',
        type: 'input'
    }, {
        name: 'search',
        caption: '搜索'
    }],
    entry: [{
        name: 'add',
        caption: '新增'
    }, {
        name: 'edit',
        caption: '编辑'
    }, {
        name: 'delete',
        caption: '删除'
    }, {
        name: 'meterCode',
        caption: '表具编码',
        type: 'input'
    }, {
        name: 'meterCategory',
        caption: '表具类别',
        type: 'input'
    }, {
        name: 'meterType',
        caption: '表具型号',
        type: 'input'
    }, {
        name: 'meterDirection',
        caption: '表向',
        type: 'input'
    }, {
        name: 'meterProdDate',
        caption: '表具生产日期',
        type: 'input'
    }, {
        name: 'search',
        caption: '搜索'
    }]
};

app.deleteNames = {
    'permission': 'permId',
    'org': 'orgId',
    'role': 'roleId',
    'dic': 'dictId',
    'dist': 'distId',
    'entry': 'meterId'
};