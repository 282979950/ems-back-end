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
            fields:app.formfields(formNames)
        });
        dialog.handleUpdate();
    });
    main.on('edit', function () {
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
        var form = app.form =  app.createForm({
            parent: '.mdui-dialog-content',
            fields: [{
                name: 'distName',
                caption: '区域名称'
            }, {
                name: 'distCode',
                caption: '区域编码'
            }, {
                name: 'distCategory',
                caption: '区域类别',
                type : 'listcombobox',
                options: [{
                    key: '市',
                    value: '市'
                }, {
                    key: '镇',
                    value: '镇'
                }, {
                    key: '户',
                    value: '户'
                }, {
                    key: '村',
                    value: '村'
                }]
            }, {
                name: 'distAddress',
                caption: '区域地址'
            }, {
                name: 'distParentId',
                caption: '父级区域',
                type: 'treecombobox',
                options: {
                    idKey: 'distId',
                    pIdKey: 'distParentId',
                    name: 'distName',
                    nodes: table.data
                }
            }],
            data: table.getSelectedDatas()[0]
        });
        dialog.handleUpdate();
    });
    main.on('delete', function () {
        mdui.dialog({
            title: 'title',
            content: '确认删除选中的节点及其子节点？',
            buttons: [{
                text: '确认',
                onClick: function () {
                    var data = app.table.getSelectedDatas()[0];
                    $.ajax({
                        type: 'POST',
                        url: app.currentPageName + '/delete.do',
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
                    html: response.message,
                    classes: 'rounded repaint-toast'
                });
                app.table.refresh(response.data)
            }
        });
    });
};
/*
*选中导航菜单时
*/
app.Getfields = function(names){

 if(names){

     if(names=='dist'){

         return [{name: 'distName', caption: '区域名称'}, {name: 'distCode', caption: '区域编码'}, {name: 'distCategory', caption: '区域类别'}, {name: 'distAddress', caption: '区域地址'}, {name: 'distParentId', caption: '父级区域'}]
     }
     if(names=='org'){

         return [{name: 'orgId', caption: '机构ID'},{name: 'orgName', caption: '机构名称'}, {name: 'orgCode', caption: '机构编码'}, {name: 'orgCategory', caption: '机构类别'}, {name: 'orgParentId', caption: '父级机构ID'}, {name: 'remarks', caption: '备注信息'}]
     }

 }else{
     alert("数据加载出错，请检查该列导航栏数据");
 }

}
/*
 *新增，修改时弹出框,列显示
 */
app.formfields = function(names){

    if(names){
        /*
         *选中区域管理时
         */
        if(names=='dist'){

            return [{name: 'distName', caption: '区域名称'}, {name: 'distCode', caption: '区域编码'}, {name: 'distCategory', caption: '区域类别'}, {name: 'distAddress', caption: '区域地址'}, {name: 'distParentId', caption: '父级区域'}]
        }
        if(names=='org'){

            return [{name: 'orgName', caption: '机构名称'}, {name: 'orgCode', caption: '机构编码'}, {name: 'orgCategory', caption: '机构类别'}, {name: 'orgParentId', caption: '父级机构ID'}, {name: 'remarks', caption: '备注信息'}]
        }

    }else{
        alert("数据加载出错");
    }

}