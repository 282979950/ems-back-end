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
            var name = app.currentPageName = eventSrc.classList[eventSrc.classList.length - 1];
            var text = eventSrc.innerText;
            if (eventSrc.classList.contains('nav-item')) {
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
        };
    });
};

app.initEvent = function () {
    var main = $('.container-main');
    var table = app.table;
    var fields = table.getFields();
    main.on('add', function () {
        var dialog = mdui.dialog({
            title: 'title',
            content: '<div class="mdui-dialog-content"></div>',
            buttons: [{
                text: '确认',
                onClick: function () {
                    var data = form.getData();
                    $.ajax({
                        type: 'POST',
                        url: app.currentPageName + '/createDistrict.do',
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
            fields: [{
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
            }]
        });
        dialog.handleUpdate();
    });
    main.on('edit', function () {
        var dialog = mdui.dialog({
            title: 'title',
            content: '<div class="mdui-dialog-content"></div>',
            buttons: [{
                text: '确认',
                onClick: function () {
                    var data = form.getData();
                    $.ajax({
                        type: 'POST',
                        url: app.currentPageName + '/createDistrict.do',
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
            fields: [{
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
            }]
        });
        dialog.handleUpdate();
    });
    main.on('delete', function () {

    });
    main.on('search', function () {

    });
};

