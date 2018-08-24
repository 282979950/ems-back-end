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
            title: 'title',
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
                            M.toast({
                                html: response.message,
                                classes: 'rounded repaint-toast'
                            });
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
        $('.mdui-dialog-content').html("");
        var form = app.createForm({
            parent: '.mdui-dialog-content',
            fields:app.addFormfields[formNames]

        });
        dialog.handleUpdate();
    });
    main.on('edit', function () {
        if(table.getSelectedDatas().length==0){
            M.toast({
                html: '请选择一条数据',
                classes: 'rounded repaint-toast'
            });
            return;
        }
        if(table.getSelectedDatas().length>1){
            M.toast({
                html: '只能选择一条数据',
                classes: 'rounded repaint-toast'
            });
            return;
        }
        var dialog = mdui.dialog({
            title: 'title',
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
                            M.toast({
                                html: response.message,
                                classes: 'rounded repaint-toast'
                            });
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
        var form = app.createForm({
            parent: '.mdui-dialog-content',
            fields:app.editFormFields[formNames],
            data: table.getSelectedDatas()[0]
        });
        dialog.handleUpdate();
    });
    main.on('delete', function () {
        if(table.getSelectedDatas().length==0){
            M.toast({
                html: '请至少选择一条数据',
                classes: 'rounded repaint-toast'
            });
            return;
        }
        mdui.dialog({
            title: 'title',
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
                            M.toast({
                                html: response.message,
                                classes: 'rounded repaint-toast'
                            });
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
                M.toast({
                    html: response.message==null?'':response.message,
                    classes: 'rounded repaint-toast'
                });
                app.table.refresh(response.data)
            }
        });
    });
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
        name: 'menuName',
        caption: '菜单名称'
    }],
    role: [{
        name: 'roleName',
        caption: '角色名称'
    }]
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
        caption: '机构类别'
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
        name: 'menuId',
        caption: '菜单名称'
        /*,type:'selectTree',url:'permission/listAllMenus.do', id:'menuId', text:'menuName', parentId:'menuParentId'*/
    }],
    role: [{
        name: 'roleName', caption: '角色名称'
    }, {
        name: 'distIdList', caption: '角色所属地区'
    }, {
        name: 'orgIdList', caption: '角色所属机构'
    }, {
        name: 'permIdList', caption: '角色拥有权限'
    }]
};
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
        caption: '机构类别'
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
        name: 'menuId',
        caption: '菜单名称'
        /*,type:'selectTree',url:'permission/listAllMenus.do', id:'menuId', text:'menuName', parentId:'menuParentId'*/
    }],
    role: [{
        name: 'roleName',
        caption: '角色名称'
    }, {
        name: 'distIdList',
        caption: '角色所属地区'
    }, {
        name: 'orgIdList',
        caption: '角色所属机构'
        /*,type:'selectTree',url:'permission/listAllMenus.do', id:'menuId', text:'menuName', parentId:'menuParentId'*/
    }, {
        name: 'permIdList',
        caption: '角色拥有权限'
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
    }]
};

app.deleteNames = {
    'permission': 'permId',
    'org': 'orgId',
    'role': 'roleId',
    'dic': 'dictId',
    'dist': 'distId'
};
