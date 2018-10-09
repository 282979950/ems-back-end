/* global app, M */
var app = {
    DEFAULT_TEMPLATE: '<div class="mdui-table-fluid mdui-theme-accent-blue"></div>',
    dataCache: {}
};

app.getDataCache = function (name) {
    return this.dataCache[name] ? JSON.parse(JSON.stringify(this.dataCache[name])) : null;
};
app.setDataCache = function (name, value) {
    this.dataCache[name] = JSON.parse(JSON.stringify(value));
};

app.getPanelContent = function (name) {
    var panelContent = '';
    switch (name) {
        /*
         * 系统管理：区域管理 机构管理 用户管理 角色管理 权限管理 字典管理 日志管理 公告管理
         */
        case 'dist':
        case 'org':
        case 'emp':
        case 'role':
        case 'permission':
        case 'dic':
            panelContent = this.DEFAULT_TEMPLATE;
            break;
        case 'log':
        case 'announcement':
            break;
        /*
         * 账户管理：表具入库 用户建档 挂表 开户 账户锁定 账户变更
         */
        case 'entry':
        case 'createArchive':
        case 'installMeter':
        case 'account':
        case 'lockAccount':
            panelContent = this.DEFAULT_TEMPLATE;
            break;
        case 'alter':
            break;
        /*
         * 充值缴费管理：预付费充值 补卡充值 后付费充值 发票管理
         */
        case 'prePayment':
        case 'replaceCard':
        case 'postPayment':
        case 'invoice':
            break;
        /*
         * 维修补气管理: 维修单录入 维修补气 补缴结算 IC卡初始化
         */
        case 'input':
        case 'fillGas':
        case 'balance':
        case 'initCard':
            break;
        /*
         * 账务处理：预冲账 冲账
         */
        case 'preStrike':
        case 'strike':
            break;
        /*
         * 表具运行管理：抄表 阀门控制 异常情况
         */
        case 'record':
        case 'control':
        case 'exception':
            break;
        /*
         * 查询统计：IC卡查询 开户信息查询 用户信息查询 异常用户查询 营业数据查询 营业报表查询
         */
        case 'cardQuery':
        case 'accountQuery':
        case 'userQuery':
        case 'exceptionQuery':
        case 'businessDataQuery':
        case 'businessReportQuery':
            break;
    }
    return panelContent;
};

app.initIndex = function () {
    $(document).ready(function () {
        var $menuList = $('.mdui-drawer .mdui-list');
        var $container = $('.mdui-container');
        $menuList.on('click', function (event) {
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
                    var titleElement = $container.children()[0];
                    var mainElement = $container.children()[1];
                    titleElement.innerHTML = text;
                    mainElement.innerHTML = app.getPanelContent(name);
                    app.initPane({
                        pane: mainElement,
                        url: name + '/listData.do'
                    });
                    app.initEvent();
                }
            }
        });
        $(document).click(function () {
            $(".tree-combobox-panel").hide();
            app.isTreeComboboxPanelShow = false;
        });
        $('body').on('keyup', '[name="orderGas"]', function (res) {
            var val = $(this).val();
            if (/^\d+(\.\d+)?$/.test(val)) {
                $.ajax({
                    async: true,
                    type: 'POST',
                    url: 'account/calAmount.do',
                    contentType: 'application/x-www-form-urlencoded',
                    data: {
                        "orderGas": val
                    },
                    beforeSend: function (xhr) {
                        xhr.withCredentials = true;
                    },
                    success: function (response) {
                        console.log(response);
                        response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                        if (response.status) {
                            app.editForm.setValue('orderPayment', response.data);
                        }
                    }
                });
            } else {
                app.editForm.setValue('orderPayment', null);
            }
        });
    });
};

app.lockScreen = function() {
    // todo
};

app.refresh= function () {
    if(app.currentPageName){
        app.toolbar.clearInputsData();
        app.render({
            url: app.currentPageName + '/listData.do'
        });
    }
};

app.logout = function () {
    $.ajax({
        async: true,
        type: 'POST',
        url: 'logout',
        contentType: 'application/x-www-form-urlencoded',
        beforeSend: function (xhr) {
            xhr.withCredentials = true;
        },
        success: function (response) {
            setTimeout(function () {
                window.location.href = 'login.html';
            }, 2000);
        }
    });
};

app.render = function (context) {
    // 从缓存中读取数据
    var data = app.getDataCache(context.url);
    if (data) {
        console.log("依据缓存渲染页面");
        _render(data);
    } else {
        console.log("通过ajax获取数据");
        $.ajax({
            async: false,
            type: 'GET',
            url: context.url,
            contentType: 'application/json;charset=utf-8',
            beforeSend: function (xhr) {
                xhr.withCredentials = true;
            },
            success: function (response) {
                var data = response.data;
                app.setDataCache(context.url, data);
                console.log("更新" + context.url + "缓存");
                _render(data);
            }
        });
    }
    function _render(data) {
        if (app.table) {
            app.table.refresh(data);
        } else {
            var names = app.currentPageName;
            app.table = context.table = app.createTable({
                parent: '.mdui-table-fluid',
                fields: app.tableFields[names],
                data: data
            });
        }
    }
};

app.initPane = function (context) {
    var self = this;
    app.toolbar = app.createToolbar({
        parent: '.container-main',
        fields: app.getToolbarFields(app.currentPageName)
    });
    self.render({
        url: context.url,
        pane: context.pane
    });
};

app.initEvent = function () {
    var formNames = app.currentPageName;
    var main = $('.container-main');
    var table = app.table;
    var fields = table.getFields();
    main.on('add', function () {
        var dialog = mdui.dialog({
            title: '新增',
            modal: true,
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
                                var url = app.currentPageName + '/listData.do';
                                app.setDataCache(url, null);
                                console.log("清理" + url + "缓存");
                                app.render({
                                    url: url
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
        var form = app.addForm = app.createForm({
            parent: '.mdui-dialog-content',
            fields: app.getAddFormFields(formNames)
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
            modal: true,
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
                            response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                            if (response.status) {
                                var url = app.currentPageName + '/listData.do';
                                app.setDataCache(url, null);
                                console.log("清理" + url + "缓存");
                                app.render({
                                    url: url
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
        if (app.currentPageName == 'account') {
            if (table.getSelectedDatas()[0]['meterCategory'] == 'IC卡表')
                formNames = app.currentPageName + 'IC';
            else
                formNames = app.currentPageName + 'MessAndUnion';
        }
        if (app.currentPageName == 'lockAccount') {
            if (table.getSelectedDatas()[0]['isLock'] == 'true')
                formNames = app.currentPageName + 'UnLock';
            else
                formNames = app.currentPageName + 'Lock';
        }
        var form = app.editForm = app.createForm({
            parent: '.mdui-dialog-content',
            fields: app.getEditFormFields(formNames),
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
            modal: true,
            content: '确认删除选中数据？',
            buttons: [{
                text: '确认',
                onClick: function () {
                    var names = app.currentPageName;
                    var deleteName = app.deleteNames[names];
                    var data = app.table.getSelectedIds(deleteName);
                    $.ajax({
                        type: 'POST',
                        url: app.currentPageName + '/delete.do',
                        data: {ids: data},
                        beforeSend: function (xhr) {
                            xhr.withCredentials = true;
                        },
                        success: function (response) {
                            response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                            if (response.status) {
                                var url = app.currentPageName + '/listData.do';
                                app.setDataCache(url, null);
                                console.log("清理" + url + "缓存");
                                app.render({
                                    url: url
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
    main.on('clear', function () {
        app.toolbar.clearInputsData();

    });
    main.on('lock', function () {
        var data = table.getSelectedDatas();
        if (data.length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (data.length > 1) {
            app.message('只能选择一条数据');
            return;
        }
        var isLock = data[0].isLock;
        if (isLock === true || isLock === false) {
            var dialog = mdui.dialog({
                title: '锁定解锁',
                content: ' ',
                buttons: [{
                    text: isLock ? '解锁' : '锁定',
                    onClick: function () {
                        var data = form.getData();
                        $.ajax({
                            type: 'POST',
                            url: app.currentPageName + '/lock.do',
                            contentType: 'application/x-www-form-urlencoded',
                            data: data,
                            beforeSend: function (xhr) {
                                xhr.withCredentials = true;
                            },
                            success: function (response) {
                                response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                                if (response.status) {
                                    var url = app.currentPageName + '/listData.do';
                                    app.setDataCache(url, null);
                                    console.log("清理" + url + "缓存");
                                    app.render({
                                        url: url
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
                fields: app.getEditFormFields('lockAccount'),
                data: data[0]
            });
            dialog.handleUpdate();
        }
    });
    main.on('history', function () {
        var data = table.getSelectedDatas();
        if (data.length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (data.length > 1) {
            app.message('只能选择一条数据');
            return;
        }
        var userId = data[0].userId;
        $.ajax({
            async: true,
            type: 'Post',
            url: app.currentPageName + '/lockList.do',
            data: {
                "userId": userId
            },
            contentType: 'application/x-www-form-urlencoded',
            beforeSend: function (xhr) {
                xhr.withCredentials = true;
            },
            success: function (response) {
                var data = response.data;
                var dialog = mdui.dialog({
                    title: '历史记录',
                    content: ' ',
                    buttons: [{text: '关闭'}]
                });
                var table = app.createTable({
                    parent: '.mdui-dialog-content',
                    fields: app.tableFields['lockHistory'],
                    data: data
                });
                dialog.handleUpdate();
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
        name: 'distCategoryName',
        caption: '区域类别'
    }, {
        name: 'distAddress',
        caption: '区域地址'
    }, {
        name: 'distParentName',
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
        name: 'orgCategoryName',
        caption: '机构类别'
    }, {
        name: 'orgParentName',
        caption: '父级机构名称'
    }],
    emp: [{
        name: 'empNumber',
        caption: '员工工号'
    }, {
        name: 'empName',
        caption: '员工名称'
    }, {
        name: 'orgName',
        caption: '所属机构'
    }, {
        name: 'distName',
        caption: '所属区域'
    }, {
        name: 'empLoginName',
        caption: '登录名'
    }, {
        name: 'empEmail',
        caption: '邮箱'
    }, {
        name: 'empPhone',
        caption: '电话'
    }, {
        name: 'empMobile',
        caption: '手机'
    }, {
        name: 'empAddress',
        caption: '地址'
    }, {
        name: 'empTypeName',
        caption: '员工类型'
    }, {
        name: 'empManagementDistId',
        caption: '负责片区'
    }, {
        name: 'empLoginFlagName',
        caption: '员工登录标记'
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
        name: 'permHref',
        caption: '权限路径'
    }, {
        name: 'permParentCaption',
        caption: '菜单名称'
    }, {
        name: 'remarks',
        caption: '备注'
    }],
    role: [{
        name: 'roleName',
        caption: '角色名称'
    }, {
        name: 'createTime',
        caption: '创建时间'
    }, {
        name: 'remarks',
        caption: '备注'
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
        name: 'meterDirectionName',
        caption: '表向'
    }, {
        name: 'meterProdDate',
        caption: '表具生产日期'
    }, {
        name: 'meterEntryDate',
        caption: '表具入库时间'
    }],
    createArchive: [{
        name: 'userId',
        caption: '用户编号'
    }, {
        name: 'userDistName',
        caption: '用户区域'
    }, {
        name: 'userAddress',
        caption: '用户地址'
    }, {
        name: 'userTypeName',
        caption: '用户类型'
    }, {
        name: 'userGasTypeName',
        caption: '用气类型'
    }, {
        name: 'userStatusName',
        caption: '用户状态'
    }],
    installMeter: [{
        name: 'userId',
        caption: '用户编号'
    }, {
        name: 'userDistName',
        caption: '用户区域'
    }, {
        name: 'userAddress',
        caption: '用户地址'
    }, {
        name: 'userStatusName',
        caption: '用户状态'
    }, {
        name: 'meterCode',
        caption: '表具编号'
    }],
    account: [{
        name: 'userId',
        caption: '用户编号'
    }, {
        name: 'userDistName',
        caption: '用户区域'
    }, {
        name: 'userAddress',
        caption: '用户地址'
    }, {
        name: 'userTypeName',
        caption: '用户类型'
    }, {
        name: 'userGasTypeName',
        caption: '用气类型'
    }, {
        name: 'userStatusName',
        caption: '用户状态'
    }],
    lockAccount: [{
        name: 'userId',
        caption: '用户编号'
    }, {
        name: 'userName',
        caption: '用户名称'
    }, {
        name: 'distName',
        caption: '用户区域'
    }, {
        name: 'userAddress',
        caption: '用户地址'
    }, {
        name: 'iccardId',
        caption: 'IC卡编号'
    }, {
        name: 'isLock',
        caption: '锁定状态'
    }, {
        name: 'lastLockReason',
        caption: '解锁/锁定原因'
    }],
    lockHistory: [{
        name: 'userId',
        caption: '用户编号'
    }, {
        name: 'isLock',
        caption: '锁定状态'
    }, {
        name: 'lockReason',
        caption: '解锁/锁定原因'
    }, {
        name: 'createTime',
        caption: '解锁/锁定时间'
    }]
};
/*
 *数据字典
 */
app.getDictionaryByCategory = function (category) {
    var dataArray = [];
    $.ajax({
        async: false,
        type: 'POST',
        url: 'dic/dictByType.do',
        contentType: 'application/x-www-form-urlencoded',
        data: {
            category: category
        },
        success: function (response) {
            if (response.data != null) {
                var datas = response.data;
                for (var i = 0; i < datas.length; i++) {
                    dataArray.push({
                        key: datas[i].dictKey,
                        value: datas[i].dictValue
                    });
                }
            }
        }
    });
    return dataArray;
};

/*
 *新增时弹出框,列显示
 */
app.getAddFormFields  = function (name) {
    switch (name) {
        case 'dist':
            return [{
                name: 'distName',
                caption: '区域名称',
                required: true,
                maxlength: 20
            }, {
                name: 'distCode',
                caption: '区域编码',
                maxlength: 20
            }, {
                name: 'distCategory',
                caption: '区域类别',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("dist_type")
            }, {
                name: 'distAddress',
                caption: '区域地址',
                maxlength: 50
            }, {
                name: 'distParentId',
                caption: '父级区域',
                type: 'treecombobox',
                options: {
                    idKey: 'distId',
                    pIdKey: 'distParentId',
                    name: 'distName',
                    chkStyle: 'radio',
                    radioType: 'all',
                    N: '',
                    Y: '',
                    nodes: app.getTreeComboboxNodes('dist/listData.do')
                }
            }];
        case 'org':
            return [{
                name: 'orgName',
                caption: '机构名称'
            }, {
                name: 'orgCode',
                caption: '机构编码'
            }, {
                name: 'orgCategory',
                caption: '机构类别',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("org_type")
            }, {
                name: 'orgParentId',
                caption: '父级机构',
                type: 'treecombobox',
                options: {
                    idKey: 'orgId',
                    pIdKey: 'orgParentId',
                    name: 'orgName',
                    N: '',
                    Y: '',
                    chkStyle: 'radio',
                    radioType: "all",
                    nodes: app.getTreeComboboxNodes('org/listData.do')
                }
            }, {
                name: 'remarks',
                caption: '备注信息'
            }];
        case 'emp':
            return [{
                name: 'empNumber',
                caption: '员工工号',
                required: true,
                maxlength: 50
            }, {
                name: 'empName',
                caption: '员工名称',
                required: true,
                maxlength: 50
            }, {
                name: 'empOrgId',
                caption: '所属机构',
                type:'treecombobox' ,
                options: {
                    idKey: 'orgId',
                    pIdKey: 'orgParentId',
                    name: 'orgName',
                    N : '',
                    Y : '',
                    chkStyle: 'radio',
                    radioType: "all",
                    nodes : app.getTreeComboboxNodes('org/listData.do')
                }
            }, {
                name: 'empDistrictId',
                caption: '所属区域',
                type:'treecombobox' ,
                options: {
                    idKey: 'distId',
                    pIdKey: 'distParentId',
                    name: 'distName',
                    N : '',
                    Y : '',
                    chkStyle: 'radio',
                    radioType: "all",
                    nodes : app.getTreeComboboxNodes('dist/listData.do')
                }
            }, {
                name: 'empLoginName',
                caption: '登录名',
                required: true,
                maxlength: 20
            }, {
                name: 'empPassword',
                caption: '登录密码',
                maxlength: 12,
                inputType: 'password'
            }, {
                name: 'empEmail',
                caption: '邮箱',
                inputType: 'email'
            }, {
                name: 'empPhone',
                caption: '电话'
            }, {
                name: 'empMobile',
                caption: '手机',
                inputType: 'mobile'
            }, {
                name: 'empAddress',
                caption: '地址',
                maxlength: 50
            }, {
                name: 'empType',
                caption: '员工类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("emp_type")
            }, {
                name: 'empManagementDistId',
                caption: '负责片区',
                type: 'treecombobox',
                options: {
                    idKey: 'distName',
                    pIdKey: 'distParentName',
                    name: 'distName',
                    N: 's',
                    Y: 'p',
                    nodes: app.getTreeComboboxNodes('dist/listData.do')
                }
            }, {
                name: 'empLoginFlag',
                caption: '员工登录标记',
                type: 'listcombobox',
                options: [{
                    key: '是',
                    value: 'true'
                }, {
                    key: '否',
                    value: 'false'
                }]
            }];
        case 'dic':
            return [{
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
            }];
        case 'permission':
            return [{
                name: 'permName',
                caption: '权限名称',
                required: true,
                maxlength: 50
            }, {
                name: 'permCaption',
                caption: '权限标题',
                required: true
            }, {
                name: 'permHref',
                caption: '权限路径'
            }, {
                name: 'permParentId',
                caption: '菜单名称',
                type: 'treecombobox',
                options: {
                    idKey: 'permId',
                    pIdKey: 'permParentId',
                    name: 'permCaption',
                    N: '',
                    Y: '',
                    chkStyle: 'radio',
                    radioType: "all",
                    nodes: app.getTreeComboboxNodes('permission/listAllMenus.do')
                }
            }, {
                name: 'isButton',
                caption: '是否按钮',
                type: 'listcombobox',
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
            }];
        case 'role':
            return [{
                name: 'roleName',
                caption: '角色名称',
                required: true,
                maxlength: 20
            }, {
                name: 'distIds', caption: '角色所属地区',
                type: 'treecombobox',
                options: {
                    idKey: 'distId',
                    pIdKey: 'distParentId',
                    name: 'distName',
                    N: 's',
                    Y: 'p',
                    nodes: app.getTreeComboboxNodes('dist/listData.do')
                }
            }, {
                name: 'orgIds', caption: '角色所属机构',
                type: 'treecombobox',
                options: {
                    idKey: 'orgId',
                    pIdKey: 'orgParentId',
                    name: 'orgId',
                    N: 's',
                    Y: 'p',
                    nodes: app.getTreeComboboxNodes('org/listData.do')
                }
            }, {
                name: 'permIds', caption: '角色拥有权限',
                type: 'treecombobox',
                options: {
                    idKey: 'permId',
                    pIdKey: 'permParentId',
                    name: 'permCaption',
                    N: 's',
                    Y: 'p',
                    nodes: app.getTreeComboboxNodes('permission/listAllMenusAndPerms.do')
                }
            }, {
                name: 'remarks',
                caption: '备注'
            }];
        case 'entry':
            return [{
                name: 'meterCode',
                caption: '表具编号',
                required: true
            }, {
                name: 'meterStopCode',
                caption: '表止码'
            }, {
                name: 'meterCategory',
                caption: '表具类别',
                type: 'listcombobox',
                options: [{
                    key: 'IC卡表',
                    value: 'IC卡表'
                }]
            }, {
                name: 'meterType',
                caption: '表具型号',
                type: 'listcombobox',
                options: app.getListComboboxOptions('entry/getAllMeterTypes.do', 'meterType', 'meterType')
            }, {
                name: 'meterDirection',
                caption: '表向',
                type: 'listcombobox',
                options: [{
                    key: '左',
                    value: true
                },{
                    key: '右',
                    value: false
                }]
            }, {
                name: 'meterProdDate',
                caption: '表具生产日期',
                type: 'date',
                formatter: 'yyyy-mm',
                minView: 3
            }, {
                name: 'meterEntryDate',
                caption: '表具入库时间',
                type: 'date',
                formatter: 'yyyy-mm-dd',
                minView: 2
            }];
        case 'createArchive':
            return [{
                name: 'userDistId',
                caption: '用户区域',
                type: 'treecombobox',
                options: {
                    idKey: 'distId',
                    pIdKey: 'distParentId',
                    name: 'distName',
                    chkStyle: 'radio',
                    radioType: 'all',
                    N: 's',
                    Y: 'p',
                    nodes: app.getTreeComboboxNodes('dist/listData.do')
                }
            }, {
                name: 'userAddress',
                caption: '用户地址',
                required: true,
                maxlength: 100
            }, {
                name: 'userType',
                caption: '用户类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("user_type")
            }, {
                name: 'userGasType',
                caption: '用气类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("user_gas_type")
            }];
    }
};

app.getTreeComboboxNodes = function (url) {
    var data = this.getDataCache(url);
    if (data) {
        console.log("从缓存中获取数据");
        return data;
    } else {
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
                app.setDataCache(url, data);
                console.log("更新"+ url+ "缓存");
            }
        });
        return data;
    }
};

app.getListComboboxOptions = function(url, k, v) {
    var result = [];
    var data = this.getDataCache(url);
    if (data) {
        console.log("从缓存中获取数据");
        for (var i = 0; i < data.length; i++) {
            result.push({
                key: data[i][k],
                value: data[i][v]
            });
        }
    } else {
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
                app.setDataCache(url, data);
                console.log("更新"+ url+ "缓存");
                if (data) {
                    for (var i = 0; i < data.length; i++) {
                        result.push({
                            key: data[i][k],
                            value: data[i][v]
                        });
                    }
                }
            }
        });
    }
    return result;
};

/*
 *修改时弹出框,列显示
 */

app.getEditFormFields = function (name) {
    switch (name) {
        case 'dist':
            return [{
                name: 'distName',
                caption: '区域名称',
                required: true,
                maxlength: 20
            }, {
                name: 'distCode',
                caption: '区域编码',
                maxlength: 20
            }, {
                name: 'distCategory',
                caption: '区域类别',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("dist_type")
            }, {
                name: 'distAddress',
                caption: '区域地址',
                maxlength: 50
            }, {
                name: 'distParentId',
                caption: '父级区域',
                type: 'treecombobox',
                options: {
                    idKey: 'distId',
                    pIdKey: 'distParentId',
                    name: 'distName',
                    chkStyle: 'radio',
                    radioType: 'all',
                    N: '',
                    Y: '',
                    nodes: app.getTreeComboboxNodes('dist/listData.do')
                }
            }];
        case 'org':
            return [{
                name: 'orgName',
                caption: '机构名称'
            }, {
                name: 'orgCode',
                caption: '机构编码'
            }, {
                name: 'orgCategory',
                caption: '机构类别',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("org_type")
            }, {
                name: 'orgParentId',
                caption: '父级机构',
                type: 'treecombobox',
                options: {
                    idKey: 'orgId',
                    pIdKey: 'orgParentId',
                    name: 'orgName',
                    N: '',
                    Y: '',
                    chkStyle: 'radio',
                    radioType: "all",
                    nodes: app.getTreeComboboxNodes('org/listData.do')
                }
            }];
        case 'emp':
            return [{
                name: 'empNumber',
                caption: '员工工号',
                required: true,
                maxlength: 50
            }, {
                name: 'empName',
                caption: '员工名称',
                required: true,
                maxlength: 50
            }, {
                name: 'empOrgId',
                caption: '所属机构',
                type:'treecombobox' ,
                options: {
                    idKey: 'orgId',
                    pIdKey: 'orgParentId',
                    name: 'orgName',
                    N : '',
                    Y : '',
                    chkStyle: 'radio',
                    radioType: "all",
                    nodes : app.getTreeComboboxNodes('org/listData.do')
                }
            }, {
                name: 'empDistrictId',
                caption: '所属区域',
                type:'treecombobox' ,
                options: {
                    idKey: 'distId',
                    pIdKey: 'distParentId',
                    name: 'distName',
                    N : '',
                    Y : '',
                    chkStyle: 'radio',
                    radioType: "all",
                    nodes : app.getTreeComboboxNodes('dist/listData.do')
                }
            }, {
                name: 'empLoginName',
                caption: '登录名',
                required: true,
                maxlength: 20
            }, {
                name: 'empPassword',
                caption: '登录密码',
                maxlength: 12,
                inputType: 'password'
            }, {
                name: 'empEmail',
                caption: '邮箱',
                inputType: 'email'
            }, {
                name: 'empPhone',
                caption: '电话'
            }, {
                name: 'empMobile',
                caption: '手机',
                inputType: 'mobile'
            }, {
                name: 'empAddress',
                caption: '地址',
                maxlength: 50
            }, {
                name: 'empType',
                caption: '员工类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("emp_type")
            }, {
                name: 'empManagementDistId',
                caption: '负责片区',
                type: 'treecombobox',
                options: {
                    idKey: 'distName',
                    pIdKey: 'distParentName',
                    name: 'distName',
                    N: 's',
                    Y: 'p',
                    nodes: app.getTreeComboboxNodes('dist/listData.do')
                }
            }, {
                name: 'empLoginFlag',
                caption: '员工登录标记',
                type: 'listcombobox',
                options: [{
                    key: '是',
                    value: 'true'
                }, {
                    key: '否',
                    value: 'false'
                }]
            }];
        case 'dic':
            return [{
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
            }];
        case 'permission':
            return [{
                name: 'permName',
                caption: '权限名称',
                required: true,
                maxlength: 50
            }, {
                name: 'permCaption',
                caption: '权限标题'
            }, {
                name: 'permHref',
                caption: '权限路径'
            }, {
                name: 'permParentId',
                caption: '菜单名称',
                type: 'treecombobox',
                options: {
                    idKey: 'permId',
                    pIdKey: 'permParentId',
                    name: 'permCaption',
                    N: '',
                    Y: '',
                    chkStyle: 'radio',
                    radioType: "all",
                    nodes: app.getTreeComboboxNodes('permission/listAllMenus.do')
                }
            }, {
                name: 'isButton',
                caption: '是否按钮',
                type: 'listcombobox',
                options: [{
                    key: '是',
                    value: 'true'
                }, {
                    key: '否',
                    value: 'false'
                }],
                required: true
            }, {
                name: 'remarks',
                caption: '备注'
            }];
        case 'role':
            return [{
                name: 'roleName',
                caption: '角色名称',
                required: true,
                maxlength: 20
            }, {
                name: 'distIds',
                caption: '角色所属地区',
                type: 'treecombobox',
                options: {
                    idKey: 'distId',
                    pIdKey: 'distParentId',
                    name: 'distName',
                    N: 's',
                    Y: 'p',
                    nodes: app.getTreeComboboxNodes('dist/listData.do')
                }
            }, {
                name: 'orgIds',
                caption: '角色所属机构',
                type: 'treecombobox',
                options: {
                    idKey: 'orgId',
                    pIdKey: 'orgParentId',
                    name: 'orgName',
                    N: 's',
                    Y: 'p',
                    nodes: app.getTreeComboboxNodes('org/listData.do')
                }
            }, {
                name: 'permIds',
                caption: '角色拥有权限',
                type: 'treecombobox',
                options: {
                    idKey: 'permId',
                    pIdKey: 'permParentId',
                    name: 'permCaption',
                    N: 's',
                    Y: 'p',
                    nodes: app.getTreeComboboxNodes('permission/listAllMenusAndPerms.do')
                }
            }, {
                name: 'remarks',
                caption: '备注'
            }];
        case 'entry':
            return [{
                name: 'meterCode',
                caption: '表具编号',
                required: true
            }, {
                name: 'meterStopCode',
                caption: '表止码'
            }, {
                name: 'meterCategory',
                caption: '表具类别',
                type: 'listcombobox',
                options: [{
                    key: 'IC卡表',
                    value: 'IC卡表'
                }]
            }, {
                name: 'meterType',
                caption: '表具型号',
                type: 'listcombobox',
                options: app.getListComboboxOptions('entry/getAllMeterTypes.do', 'meterType', 'meterType')
            }, {
                name: 'meterDirection',
                caption: '表向',
                type: 'listcombobox',
                options: [{
                    key: '左',
                    value: true
                },{
                    key: '右',
                    value: false
                }]
            }, {
                name: 'meterProdDate',
                caption: '表具生产日期',
                type: 'date',
                formatter: 'yyyy-mm',
                minView: 3
            }, {
                name: 'meterEntryDate',
                caption: '表具入库时间',
                type: 'date',
                formatter: 'yyyy-mm-dd',
                minView: 2
            }];
        case 'createArchive':
            return [{
                name: 'userDistId',
                caption: '用户区域',
                type: 'treecombobox',
                options: {
                    idKey: 'distId',
                    pIdKey: 'distParentId',
                    name: 'distName',
                    chkStyle: 'radio',
                    radioType: 'all',
                    N: 's',
                    Y: 'p',
                    nodes: app.getTreeComboboxNodes('dist/listData.do')
                }
            }, {
                name: 'userAddress',
                caption: '用户地址',
                required: true,
                maxlength: 100
            }, {
                name: 'userType',
                caption: '用户类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("user_type")
            }, {
                name: 'userGasType',
                caption: '用气类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("user_gas_type")
            }];
        case 'installMeter':
            return [{
                name: 'userId',
                caption: '用户编号',
                disabled: true
            }, {
                name: 'userDistName',
                caption: '用户区域',
                disabled: true
            }, {
                name: 'userAddress',
                caption: '用户地址',
                disabled: true
            }, {
                name: 'userStatus',
                caption: '用户状态',
                disabled: true
            }, {
                name: 'meterCode',
                caption: '表具编号',
                required: true
            }];
        case 'accountIC':
            return [{
                name: 'userName',
                caption: '客户姓名',
                required: true,
                maxlength: 20
            }, {
                name: 'userPhone',
                caption: '电话',
                inputType: 'mobile',
                required: true
            }, {
                name: 'iccardIdentifier',
                caption: 'IC卡识别号',
                required: true,
                maxlength: 12
            }, {
                name: 'userIdcard',
                caption: '身份证号',
                inputType: 'IdCard',
                required: true
            }, {
                name: 'userDeed',
                caption: '房产证号'
            }, {
                name: 'orderGas',
                caption: '充值气量',
                inputType: 'num',
                required: true
            }, {
                name: 'orderPayment',
                caption: '充值金额',
                disabled: true
            }];
        case 'accountMessAndUnion':
            return [{
                name: 'userName',
                caption: '客户姓名',
                required: true,
                maxlength: 20
            }, {
                name: 'userPhone',
                caption: '电话',
                inputType: 'mobile',
                required: true
            }, {
                name: 'iccardIdentifier',
                caption: 'IC卡识别号',
                required: true,
                maxlength: 12
            }, {
                name: 'userIdcard',
                caption: '身份证号',
                inputType: 'IdCard',
                required: true
            }, {
                name: 'userDeed',
                caption: '房产证号'
            }, {
                name: 'orderPayment',
                caption: '充值金额',
                inputType: 'num',
                required: true
            }];
        case 'lockAccount':
            return [{
                name: 'userName',
                caption: '客户姓名',
                disabled: true,
                maxlength: 20
            }, {
                name: 'iccardId',
                caption: 'IC卡号',
                disabled: true
            }, {
                name: 'distName',
                caption: '区域名称',
                disabled: true
            }, {
                name: 'userAddress',
                caption: '用户地址',
                disabled: true
            }, {
                name: 'isLock',
                caption: '是否锁定',
                disabled: true
            }, {
                name: 'lastLockReason',
                caption: '上次操作原因',
                disabled: true
            }, {
                name: 'lockReason',
                caption: '本次操作原因'
            }];
    }
};

/*
 *头部筛选查询列
 */
app.getToolbarFields = function (name) {
    switch (name) {
        case 'dist':
            return [{
                name: 'add',
                caption: '新增',
                perm:'sys:dist:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm:'sys:dist:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm:'sys:dist:delete'
            }, {
                name: 'distName',
                caption: '区域名称',
                type: 'input'
            }, {
                name: 'distCode',
                caption: '区域编码',
                type: 'input'
            }];
        case 'org':
            return [{
                name: 'add',
                caption: '新增',
                perm:'sys:org:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm:'sys:org:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm:'sys:org:delete'
            }, {
                name: 'orgCode',
                caption: '机构编码',
                type: 'input'
            }, {
                name: 'orgName',
                caption: '机构名称',
                type: 'input'
            }];
        case 'emp':
            return [{
                name: 'add',
                caption: '新增',
                perm:'sys:emp:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm:'sys:emp:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm:'sys:emp:delete'
            }, {
                name: 'empNumber',
                caption: '员工工号',
                type: 'input'
            }, {
                name: 'empName',
                caption: '员工名称',
                type: 'input'
            }, {
                name: 'empOrgId',
                caption: '所属机构',
                type:'treecombobox' ,
                options: {
                    idKey: 'orgId',
                    pIdKey: 'orgParentId',
                    name: 'orgName',
                    N : '',
                    Y : '',
                    chkStyle: 'radio',
                    radioType: "all",
                    nodes : app.getTreeComboboxNodes('org/listData.do')
                }
            }, {
                name: 'empDistrictId',
                caption: '所属区域',
                type:'treecombobox' ,
                options: {
                    idKey: 'distId',
                    pIdKey: 'distParentId',
                    name: 'distName',
                    N : '',
                    Y : '',
                    chkStyle: 'radio',
                    radioType: "all",
                    nodes : app.getTreeComboboxNodes('dist/listData.do')
                }
            }, {
                name: 'empLoginName',
                caption: '登录名',
                type: 'input'
            }, {
                name: 'empPhone',
                caption: '电话',
                type: 'input'
            }, {
                name: 'empMobile',
                caption: '手机',
                type: 'input'
            }, {
                name: 'empType',
                caption: '员工类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("emp_type")
            }];
        case 'dic':
            return [{
                name: 'add',
                caption: '新增',
                perm:'sys:dic:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm:'sys:dic:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm:'sys:dic:delete'
            }, {
                name: 'dictCategory',
                caption: '字典类型',
                type: 'input'
            }];
        case 'permission':
            return [{
                name: 'add',
                caption: '新增',
                perm:'sys:perm:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm:'sys:perm:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm:'sys:perm:delete'
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
            }];
        case 'role':
            return [{
                name: 'add',
                caption: '新增',
                perm:'sys:role:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm:'sys:role:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm:'sys:role:delete'
            }, {
                name: 'roleName',
                caption: '角色名称',
                type: 'input'
            }];
        case 'entry':
            return [{
                name: 'add',
                caption: '新增',
                perm:'account:entryMeter:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm:'account:entryMeter:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm:'account:entryMeter:delete'
            }, {
                name: 'meterCode',
                caption: '表具编码',
                type: 'input'
            }, {
                name: 'meterCategory',
                caption: '表具类别',
                type: 'listcombobox',
                options: [{
                    key: 'IC卡表',
                    value: 'IC卡表'
                }]
            }, {
                name: 'meterType',
                caption: '表具型号',
                type: 'listcombobox',
                options: app.getListComboboxOptions('entry/getAllMeterTypes.do', 'meterType', 'meterType')
            }, {
                name: 'meterDirection',
                caption: '表向',
                type: 'listcombobox',
                options: [{
                    key: '左',
                    value: true
                },{
                    key: '右',
                    value: false
                }]
            }, {
                name: 'meterProdDate',
                caption: '表具生产日期',
                type: 'date',
                formatter: 'yyyy-mm',
                minView: 3
            }];
        case 'createArchive':
            return [{
                name: 'add',
                caption: '新增',
                perm:'account:createArchive:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm:'account:createArchive:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm:'account:createArchive:delete'
            }, {
                name: 'userId',
                caption: '用户编号',
                type: 'input'
            }, {
                name: 'userDistId',
                caption: '用户区域',
                type: 'treecombobox',
                options: {
                    idKey: 'distId',
                    pIdKey: 'distParentId',
                    name: 'distName',
                    chkStyle: 'radio',
                    radioType: 'all',
                    N: 's',
                    Y: 'p',
                    nodes: app.getTreeComboboxNodes('dist/listData.do')
                }
            }, {
                name: 'userAddress',
                caption: '用户地址',
                type: 'input'
            }, {
                name: 'userType',
                caption: '用户类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory('user_type')
            }, {
                name: 'userGasType',
                caption: '用气类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory('user_gas_type')
            }, {
                name: 'userStatus',
                caption: '用户状态',
                type: 'listcombobox',
                options: app.getDictionaryByCategory('user_status')
            }];
        case 'installMeter':
            return [{
                name: 'edit',
                caption: '编辑',
                perm:'account:installation:update'
            }, {
                name: 'userId',
                caption: '用户编号',
                type: 'input'
            }, {
                name: 'userDistId',
                caption: '用户区域',
                type: 'treecombobox',
                options: {
                    idKey: 'distId',
                    pIdKey: 'distParentId',
                    name: 'distName',
                    chkStyle: 'radio',
                    radioType: 'all',
                    N: 's',
                    Y: 'p',
                    nodes: app.getTreeComboboxNodes('dist/listData.do')
                }
            }, {
                name: 'userAddress',
                caption: '用户地址',
                type: 'input'
            }];
        case 'account':
            return [{
                name: 'edit',
                caption: '开户',
                perm:'account:createAccount:update'
            }, {
                name: 'userDistId',
                caption: '用户区域',
                type: 'input',
                type: 'treecombobox',
                options: {
                    idKey: 'distId',
                    pIdKey: 'distParentId',
                    name: 'distName',
                    chkStyle: 'radio',
                    radioType: 'all',
                    N: 's',
                    Y: 'p',
                    nodes: app.getTreeComboboxNodes('dist/listData.do')
                }
            }, {
                name: 'userAddress',
                caption: '用户地址',
                type: 'input'
            }, {
                name: 'userType',
                caption: '用户类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory('user_type')
            }, {
                name: 'userGasType',
                caption: '用气类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory('user_gas_type')
            }];
        case 'lockAccount':
            return [{
                name: 'lock',
                caption: '锁定/解锁',
                perm:'account:lockAccount:lock'
            }, {
                name: 'history',
                caption: '历史锁定记录',
                perm:'account:lockAccount:lockList'
            }, {
                name: 'userName',
                caption: '用户名称',
                type: 'input'
            }, {
                name: 'iccardId',
                caption: 'IC卡号',
                type: 'input'
            }];
    }
};

app.deleteNames = {
    'permission': 'permId',
    'org': 'orgId',
    'role': 'roleId',
    'dic': 'dictId',
    'dist': 'distId',
    'entry': 'meterId',
    'createArchive': 'userId',
    'emp': 'empId'
};