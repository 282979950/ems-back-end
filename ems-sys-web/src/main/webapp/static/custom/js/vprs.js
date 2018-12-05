app.getPanelContent = function (name) {
    var panelContent = '';
    this.hasPaginator = false;
    switch (name) {
        /*
         * 系统管理：区域管理 机构管理 用户管理 角色管理 权限管理 字典管理 气价管理 日志管理 公告管理
         */
        case 'dist':
        case 'org':
        case 'role':
        case 'evalItem':
            panelContent = this.DEFAULT_TEMPLATE;
            break;
        case 'log':
        case 'announcement':
            break;
        case 'emp':
        case 'permission':
        case 'dic':
        case 'entryApplyRepair':
        case 'solet':
        case 'wxNotice':
            this.hasPaginator = true;
            this.pageNum = this.DEFAULT_PAGE_NUM;
            this.pageSize = this.DEFAULT_PAGE_SIZE;
            panelContent = this.DEFAULT_PAGE_TEMPLATE;
            break;
        /*
        * 查询统计：订单查询
        */
        case 'eval':
            panelContent = this.DEFAULT_TEMPLATE;
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
        $(document).on('blur', '.queryField', function (e) {
            var queryFieldName = $(e.target).attr('name');
            var value = e.target.value;
            if (value) {
                switch (queryFieldName) {
                    case 'userId':
                        $.ajax({
                            async: true,
                            type: 'POST',
                            url: 'entryApplyRepair/getApplyRepairUserInfoById.do',
                            contentType: 'application/x-www-form-urlencoded',
                            data: {
                                "userId": value
                            },
                            beforeSend: function (xhr) {
                                xhr.withCredentials = true;
                            },
                            success: function (response) {
                                console.log(response);
                                if (response.status) {
                                    var data = response.data;
                                    if (app.addForm) {
                                        app.addForm.setValue('userName', data ? data.userName : null);
                                        app.addForm.setValue('userPhone', data ? data.userPhone : null);
                                        app.addForm.setValue('distName', data ? data.distName : null);
                                        app.addForm.setValue('distCode', data ? data.distCode : null);
                                        app.addForm.setValue('userAddress', data ? data.userAddress : null);
                                        app.addForm.getData().distId = data ? data.distId : null;
                                    }
                                    if (app.editForm) {
                                        app.editForm.setValue('userName', data ? data.userName : null);
                                        app.editForm.setValue('userPhone', data ? data.userPhone : null);
                                        app.editForm.setValue('distName', data ? data.distName : null);
                                        app.editForm.setValue('userAddress', data ? data.userAddress : null);
                                    }
                                }
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        });
        $(document).on('blur', 'input[type=text]', function () {
            this.value = $.trim(this.value);
        });
    });
};

app.initEvent = function () {
    var formNames = app.currentPageName;
    var main = $('.container-main');
    var table = app.table;
    if (table !== null) {
        var fields = table.getFields();
    }
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
                            response.status ? app.successMessage(response.message) : app.errorMessage(response.message.replace(/[{}a-zA-Z=]/g, ""));
                            if (response.status) {
                                var url = app.currentPageName + '/listData.do';
                                app.render({
                                    url: url
                                });
                            }
                        }
                    });
                    app.addForm = null;
                }
            }, {
                text: '取消',
                onClick: function () {
                    app.addForm = null;
                }
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
        var selectDatas = table.getSelectedDatas();
        if (selectDatas.length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (selectDatas.length > 1) {
            app.message('只能选择一条数据');
            return;
        }
        switch (app.currentPageName) {
            case 'dist':
                if (!selectDatas[0].distParentId) {
                    app.message("该节点为根节点不可编辑");
                    return;
                }
                break;
            case 'entryApplyRepair':
                if (selectDatas[0].applyRepairType === 1) {
                    app.message("微信报修单不能编辑");
                    return;
                }
                break;
        }
        if (app.currentPageName === 'dist') {
            if (!table.getSelectedDatas()[0].distParentId) {
                app.message("该节点为根节点不可编辑");
                return;
            }
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
                            response.status ? app.successMessage(response.message) : app.errorMessage(response.message.replace(/[{}a-zA-Z=]/g, ""));
                            if (response.status) {
                                var rdata = response.data;
                                if (app.currentPageName === "account" || app.currentPageName === 'replaceCard' || app.currentPageName === 'prePayment') {
                                    app.WriteCard(rdata);
                                }
                                var url = app.currentPageName + '/listData.do';
                                app.render({
                                    url: url
                                });
                            }
                        }
                    });
                    app.editForm = null;
                }
            }, {
                text: '取消',
                onClick: function () {
                    app.editForm = null;
                }
            }]
        });
        $(".tree-combobox-panel").remove();
        var form = app.editForm = app.createForm({
            parent: '.mdui-dialog-content',
            fields: app.getEditFormFields(formNames),
            data: table.getSelectedDatas()[0]
        });
        switch (app.currentPageName) {
            case 'entryApplyRepair':
                form.disableField('applyRepairType');
                // todo 订单被处理后不可编辑
                break;
            default:
                break;
        }
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
    main.on('search', function (e) {
        var data = app.toolbar.getInputsData();
        if(app.hasPaginator) {
            if (e.target.className !== 'custom-pagination') {
                app.pageNum = app.DEFAULT_PAGE_NUM;
            }
            data.push({
                name: 'pageSize',
                value: app.pageSize
            });
            data.push({
                name: 'pageNum',
                value: app.pageNum
            })
        }
        $.ajax({
            type: 'POST',
            url: app.currentPageName + '/search.do',
            contentType: 'application/x-www-form-urlencoded',
            data: data,
            beforeSend: function (xhr) {
                xhr.withCredentials = true;
            },
            success: function (response) {
                response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                if (app.hasPaginator) {
                    if (response.data) {
                        app.table.refresh(response.data.list);
                        app.pagination.setProperties({
                            currPage: app.pageNum,
                            totalPage: response.data.pages,
                            totalSize: response.data.total
                        });
                    } else {
                        app.table.refresh(response.data);
                        app.pagination.setProperties({
                            currPage: app.pageNum,
                            totalPage: 1,
                            totalSize: 0
                        });
                    }
                } else {
                    app.table.refresh(response.data);
                }
            }
        });
    });
    main.on('clear', function () {
        app.toolbar.clearInputsData();
    });
};

/**
 * 表格字段
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
        name: 'roleName',
        caption: '员工角色'
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
    entryApplyRepair: [{
        name: 'applyRepairFlowNumber',
        caption: '报修单编号'
    }, {
        name: 'applyRepairTypeName',
        caption: '报修类型'
    }, {
        name: 'applyRepairStatusName',
        caption: '报修状态'
    }, {
        name: 'userId',
        caption: '户号'
    }, {
        name: 'userName',
        caption: '用户姓名'
    }, {
        name: 'distName',
        caption: '用户区域'
    }, {
        name: 'distCode',
        caption: '用户区域编码'
    }, {
        name: 'userAddress',
        caption: '用户地址'
    }, {
        name: 'userPhone',
        caption: '用户电话'
    }, {
        name: 'userTelPhone',
        caption: '主叫号码'
    }, {
        name: 'applyRepairFaultDesc',
        caption: '故障说明'
    }, {
        name: 'applyRepairAppealContent',
        caption: '诉求内容'
    }, {
        name: 'startTime',
        caption: '预约开始时间'
    }, {
        name: 'endTime',
        caption: '预约截止时间'
    }, {
        name: 'remarks',
        caption: '备注'
    }],
    solet: [{
        name: 'serviceOutletName',
        caption: '网点名称'
    }, {
        name: 'serviceOutletAddress',
        caption: '网点地址'
    }, {
        name: 'serviceOutletOpenTime',
        caption: '网点营业时间'
    }, {
        name: 'serviceOutletPhone',
        caption: '网点联系方式'
    }, {
        name: 'serviceOutletContent',
        caption: '网点营业范围'
    }],
    evalItem:[{
        name: 'evalItemContent',
        caption: '评价项内容'
    },{
        name: 'createByName',
        caption: '创建人'
    },{
        name: 'createTime',
        caption: '创建时间'
    },{
        name: 'remarks',
        caption: '备注'
    }],
    eval:[{
        name: 'applyRepairId',
        caption:'报修单号'
    },{
        name: 'applyRepairFlowNumber',
        caption:'报修流水号'
    },{
        name: 'userId',
        caption:'报修用户ID'
    },{
        name: 'userName',
        caption:'报修用户名'
    },{
        name: 'fixedEvalSelect',
        caption:'是否满意本次服务'
    },{
        name: 'fixedEvalContent',
        caption:'评价内容'
    },{
        name: 'evalTime',
        caption:'评价时间'
    }],
    wxNotice: [{
        name: 'wxNoticeTitle',
        caption: '微信公告标题'
    }, {
        name: 'wxNoticeTypeName',
        caption: '微信公告类型'
    }, {
        name: 'wxNoticeContent',
        caption: '微信公告内容'
    }, {
        name: 'createTime',
        caption: '发布时间'
    }]
};

/**
 * 新增时弹出框,列显示
 */
app.getAddFormFields = function (name) {
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
                inputType: 'alphanumeric',
                maxlength: 50
            }, {
                name: 'empName',
                caption: '员工名称',
                required: true,
                maxlength: 50
            }, {
                name: 'empOrgId',
                caption: '所属机构',
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
                name: 'empDistrictId',
                caption: '所属区域',
                type: 'treecombobox',
                options: {
                    idKey: 'distId',
                    pIdKey: 'distParentId',
                    name: 'distName',
                    N: '',
                    Y: '',
                    chkStyle: 'radio',
                    radioType: "all",
                    nodes: app.getTreeComboboxNodes('dist/listData.do')
                }
            }, {
                name: 'empLoginName',
                caption: '登录名',
                inputType: 'alphanumeric',
                required: true,
                maxlength: 20
            }, {
                name: 'empPassword',
                caption: '登录密码',
                required: true,
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
                name: 'roleId',
                caption: '员工所属角色',
                type: 'listcombobox',
                options: app.getListComboboxOptions('role/listData.do', 'roleName', 'roleId')
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
                    name: 'orgName',
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
                name: 'isAdmin',
                caption: '是否是管理员',
                type: 'listcombobox',
                options: [{
                    key: '是',
                    value: true
                }, {
                    key: '否',
                    value: false
                }]
            }, {
                name: 'remarks',
                caption: '备注'
            }];
        case 'entryApplyRepair':
            return [{
                name: 'userId',
                caption: '户号',
                maxlength: 10,
                required: true,
                queryField: true
            }, {
                name: 'userName',
                caption: '用户姓名',
                disabled: true
            }, {
                name: 'userAddress',
                caption: '用户地址',
                disabled: true
            }, {
                name: 'distName',
                caption: '用户区域',
                disabled: true
            }, {
                name: 'distCode',
                caption: '用户区域编码',
                disabled: true
            }, {
                name: 'userPhone',
                caption: '用户电话',
                disabled: true
            }, {
                name: 'userTelPhone',
                caption: '主叫号码',
                inputType: 'mobile',
                required: true
            }, {
                name: 'applyRepairFaultDesc',
                caption: '故障说明',
                required: true,
                maxlength: 255
            }, {
                name: 'applyRepairAppealContent',
                caption: '诉求内容',
                maxlength: 255,
                required: true
            }, {
                name: 'startTime',
                caption: '预约开始时间',
                type: 'date',
                required: true,
                startDate: new Date(),
                endDate: new Date(new Date().getTime() + 7 * 24 * 3600 * 1000)
            }, {
                name: 'endTime',
                caption: '预约截止时间',
                type: 'date',
                required: true,
                startDate: new Date(),
                endDate: new Date(new Date().getTime() + 7 * 24 * 3600 * 1000)
            }, {
                name: 'remarks',
                caption: '备注',
                maxlength: 255
            }];
        case 'solet':
            return [{
                name: 'serviceOutletName',
                caption: '网点名称',
                required: true
            }, {
                name: 'serviceOutletAddress',
                caption: '网点地址',
                required: true
            }, {
                name: 'serviceOutletOpenTime',
                caption: '网点营业时间',
                required: true
            }, {
                name: 'serviceOutletPhone',
                caption: '网点联系方式',
                required: true
            }, {
                name: 'serviceOutletContent',
                caption: '网点营业范围'
            }];
        case 'evalItem':
            return [{
                name: 'evalItemContent',
                caption: '评价项内容',
                required: true
            },{
                name: 'remarks',
                caption: '备注'
            }];
        case 'wxNotice':
            return [{
                name: 'wxNoticeTitle',
                caption: '微信公告标题'
            }, {
                name: 'wxNoticeType',
                caption: '微信公告类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("wx_notice_type")
            }, {
                name: 'wxNoticeContent',
                caption: '微信公告内容'
            }];
    }
};

/**
 * 修改时弹出框,列显示
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
                inputType: 'alphanumeric',
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
                name: 'empDistrictId',
                caption: '所属区域',
                type: 'treecombobox',
                options: {
                    idKey: 'distId',
                    pIdKey: 'distParentId',
                    name: 'distName',
                    N: '',
                    Y: '',
                    chkStyle: 'radio',
                    radioType: "all",
                    nodes: app.getTreeComboboxNodes('dist/listData.do')
                }
            }, {
                name: 'empLoginName',
                caption: '登录名',
                inputType: 'alphanumeric',
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
                name: 'roleId',
                caption: '员工所属角色',
                type: 'listcombobox',
                options: app.getListComboboxOptions('role/listData.do', 'roleName', 'roleId')
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
                name: 'isAdmin',
                caption: '是否是管理员',
                type: 'listcombobox',
                options: [{
                    key: '是',
                    value: true
                }, {
                    key: '否',
                    value: false
                }]
            }, {
                name: 'remarks',
                caption: '备注'
            }];
        case 'entryApplyRepair':
            return [{
                name: 'applyRepairType',
                caption: '报修类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("apply_repair_type")
            }, {
                name: 'userId',
                caption: '户号',
                maxlength: 10,
                disabled: true
            }, {
                name: 'userName',
                caption: '用户姓名',
                disabled: true
            }, {
                name: 'userAddress',
                caption: '用户地址',
                disabled: true
            }, {
                name: 'distName',
                caption: '用户区域',
                disabled: true
            }, {
                name: 'distCode',
                caption: '用户区域编码',
                disabled: true
            }, {
                name: 'userPhone',
                caption: '用户电话',
                disabled: true
            }, {
                name: 'userTelPhone',
                caption: '主叫号码',
                inputType: 'mobile'
            }, {
                name: 'applyRepairFaultDesc',
                caption: '故障说明',
                required: true,
                maxlength: 255
            }, {
                name: 'applyRepairAppealContent',
                caption: '诉求内容',
                maxlength: 255
            }, {
                name: 'startTime',
                caption: '预约开始时间',
                type: 'date',
                required: true
            }, {
                name: 'endTime',
                caption: '预约截止时间',
                type: 'date',
                required: true
            }, {
                name: 'remarks',
                caption: '备注',
                maxlength: 255
            }];
        case 'solet':
            return [{
                name: 'serviceOutletName',
                caption: '网点名称',
                required: true
            }, {
                name: 'serviceOutletAddress',
                caption: '网点地址',
                required: true
            }, {
                name: 'serviceOutletOpenTime',
                caption: '网点营业时间',
                required: true
            }, {
                name: 'serviceOutletPhone',
                caption: '网点联系方式',
                required: true
            }, {
                name: 'serviceOutletContent',
                caption: '网点营业范围'
            }];
        case 'evalItem':
            return [{
                name: 'evalItemContent',
                caption: '评价项内容',
                required: true
            },{
                name: 'remarks',
                caption: '备注'
            }];
    }
};

/**
 * 头部筛选查询列
 */
app.getToolbarFields = function (name) {
    switch (name) {
        case 'dist':
            return [{
                name: 'add',
                caption: '新增',
                perm: 'sys:dist:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm: 'sys:dist:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm: 'sys:dist:delete'
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
                perm: 'sys:org:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm: 'sys:org:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm: 'sys:org:delete'
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
                perm: 'sys:emp:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm: 'sys:emp:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm: 'sys:emp:delete'
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
                name: 'empDistrictId',
                caption: '所属区域',
                type: 'treecombobox',
                options: {
                    idKey: 'distId',
                    pIdKey: 'distParentId',
                    name: 'distName',
                    N: '',
                    Y: '',
                    chkStyle: 'radio',
                    radioType: "all",
                    nodes: app.getTreeComboboxNodes('dist/listData.do')
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
                perm: 'sys:dic:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm: 'sys:dic:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm: 'sys:dic:delete'
            }, {
                name: 'dictCategory',
                caption: '字典类型',
                type: 'input'
            }];
        case 'permission':
            return [{
                name: 'add',
                caption: '新增',
                perm: 'sys:perm:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm: 'sys:perm:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm: 'sys:perm:delete'
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
                perm: 'sys:role:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm: 'sys:role:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm: 'sys:role:delete'
            }, {
                name: 'roleName',
                caption: '角色名称',
                type: 'input'
            }];
        case 'entryApplyRepair':
            return [{
                name: 'add',
                caption: '新增',
                perm: 'applyRepair:entryApplyRepair:create'
            }, {
                name: 'delete',
                caption: '删除',
                perm: 'applyRepair:entryApplyRepair:delete'
            }, {
                name: 'userId',
                caption: '户号',
                type: 'input'
            }, {
                name: 'userName',
                caption: '用户姓名',
                type: 'input'
            }, {
                name: 'userPhone',
                caption: '用户电话',
                type: 'input'
            }, {
                name: 'userTelPhone',
                caption: '主叫号码',
                type: 'input'
            }];
        case 'solet':
            return [{
                name: 'add',
                caption: '新增',
                perm: 'sys:solet:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm: 'sys:solet:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm: 'sys:solet:delete'
            }, {
                name: 'serviceOutletName',
                caption: '网点名称',
                type: 'input'
            }, {
                name: 'serviceOutletAddress',
                caption: '网点地址',
                type: 'input'
            }];
        case 'evalItem':
            return [{
                name: 'add',
                caption: '新增',
                perm: 'sys:evalItem:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm: 'sys:evalItem:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm: 'sys:evalItem:delete'
            }, {
                name: 'evalItemContent',
                caption: '评价项内容',
                type: 'input'
            }];
        case 'eval':
            return [{
                name: 'userName',
                caption: '用户名',
                type: 'input'
            }, {
                name: 'applyRepairFlowNumber',
                caption: '报修流水号',
                type: 'input'
            }];
        case 'wxNotice':
            return [{
                name: 'add',
                caption: '新增',
                perm: 'sys:wxNotice:create'
            }, {
                name: 'delete',
                caption: '删除',
                perm: 'sys:wxNotice:delete'
            }, {
                name: 'wxNoticeTitle',
                caption: '微信公告标题',
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
    'emp': 'empId',
    'entryApplyRepair': 'applyRepairId',
    'solet' : 'serviceOutletId',
    'evalItem': 'evalItemId',
    'wxNotice': 'wxNoticeId'
};