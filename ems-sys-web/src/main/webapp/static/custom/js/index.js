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
         * 系统管理：区域管理 机构管理 用户管理 角色管理 权限管理 字典管理 气价管理 日志管理 公告管理
         */
        case 'dist':
        case 'org':
        case 'emp':
        case 'role':
        case 'permission':
        case 'dic':
        case 'gasPrice':
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
        case 'alter':
            panelContent = this.DEFAULT_TEMPLATE;
            break;
        /*
         * 充值缴费管理：预付费充值 补卡充值 后付费充值 发票管理
         */
        case 'prePayment':
        case 'replaceCard':
        case 'postPayment':
        case 'order':
            panelContent = this.DEFAULT_TEMPLATE;
            break;
        case 'assign':
        case 'printCancel':
            panelContent = this.DEFAULT_TEMPLATE;
        case 'eInvoice':
            break;
        /*
         * 维修补气管理: 维修单录入 维修补气 补缴结算 IC卡初始化
         */
        case 'input':
        case 'fillGas':
            panelContent = this.DEFAULT_TEMPLATE;
            break;
        case 'balance':
        case 'initCard':
            panelContent = this.DEFAULT_TEMPLATE;
            break;
        /*
         * 账务处理：预冲账 冲账
         */
        case 'preStrike':
        case 'strike':
            panelContent = this.DEFAULT_TEMPLATE;
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
        case 'ardQuery':
        case 'accountQuery':
        case 'userQuery':
        case 'exceptionQuery':
        case 'businessDataQuery':
        case 'businessReportQuery':
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
        $('body').on('focus', 'form [name="iccardIdentifier"]', function (res) {
            console.log(app.editForm);
            if (app.editForm) {
                var result = app.ReadCard();
                if (result instanceof Array) {
                    app.editForm.setValue('iccardIdentifier', result[2]);
                } else {
                    app.warningMessage(result);
                }
            }
        });
        $('body').on('click', 'form [name="nIcCardIdentifier"]', function (res) {
            console.log(app.editForm);
            if (app.editForm) {
                var res = app.ReadCard();
                if(res instanceof Array) {
                    if(res[1] != 0){
                        app.warningMessage("只能用新卡进行补卡");
                        app.editForm.setValue('nIcCardIdentifier', '');
                        return;
                    }
                   app.editForm.setValue('nIcCardIdentifier', res[2]);
                }else {
                    app.warningMessage(res);
                }
            }
        });
        $('body').on('keyup', '[name="orderGas"]', function (res) {
            var val = $(this).val();
            if (/^\d+(\.\d+)?$/.test(val)) {
                if(val = 0){
                    app.warningMessage("充值气量必须大于0");
                    app.editForm.setValue('orderGas', null);
                    app.editForm.setValue('orderPayment', null);
                    return;
                }
                if(val > 900){
                    app.warningMessage("充值气量不能大于900");
                    app.editForm.setValue('orderGas', null);
                    app.editForm.setValue('orderPayment', null);
                    return;
                }
                $.ajax({
                    async: true,
                    type: 'POST',
                    url: 'gasPrice/calAmount.do',
                    contentType: 'application/x-www-form-urlencoded',
                    data: {
                        "userId": app.editForm.data.userId,
                        "orderGas": val
                    },
                    beforeSend: function (xhr) {
                        xhr.withCredentials = true;
                    },
                    success: function (response) {
                        console.log(response);
                        // response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                        if (response.status) {
                            app.editForm.setValue('orderPayment', response.data);
                        }
                    }
                });
            } else {
                app.editForm.setValue('orderPayment', null);
            }
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
                            url: 'input/getRepairOrderUserById.do',
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
                                        app.addForm.setValue('userAddress', data ? data.userAddress : null);
                                        app.addForm.getData().oldMeterId = data.meterId;
                                        app.addForm.setValue('oldMeterCode', data.meterCode);
                                        app.addForm.setValue('oldMeterTypeId', data.meterTypeId);
                                        app.addForm.setValue('oldMeterDirection', data.meterDirection);
                                    }
                                    if (app.editForm) {
                                        app.editForm.setValue('userName', data ? data.userName : null);
                                        app.editForm.setValue('userPhone', data ? data.userPhone : null);
                                        app.editForm.setValue('userAddress', data ? data.userAddress : null);
                                        app.editForm.getData().oldMeterId = data.meterId;
                                        app.editForm.setValue('oldMeterCode', data.meterCode);
                                        app.editForm.setValue('oldMeterTypeId', data.meterTypeId);
                                        app.editForm.setValue('oldMeterDirection', data.meterDirection);
                                    }
                                }
                            }
                        });
                        break;
                    case 'newMeterCode':
                        $.ajax({
                            async: true,
                            type: 'POST',
                            url: 'entry/getMeterByMeterCode.do',
                            contentType: 'application/x-www-form-urlencoded',
                            data: {
                                'meterCode': value
                            },
                            beforeSend: function (xhr) {
                                xhr.withCredentials = true;
                            },
                            success: function (response) {
                                console.log(response);
                                if (response.status) {
                                    var data = response.data;
                                    if (app.addForm) {
                                        app.addForm.getData().newMeterId = data ? data.meterId : null;
                                        app.addForm.setValue('newMeterTypeId', data ? data.meterTypeId : null);
                                        app.addForm.setValue('newMeterDirection', data ? data.meterDirection : null);
                                    }
                                    if (app.editForm) {
                                        app.editForm.getData().newMeterId = data ? data.meterId : null;
                                        app.editForm.setValue('newMeterTypeId', data ? data.meterTypeId : null);
                                        app.editForm.setValue('newMeterDirection', data ? data.meterDirection : null);
                                    }
                                }
                            }
                        });
                        break;
                    case 'empNumber':
                        $.ajax({
                            async: true,
                            type: 'POST',
                            url: 'emp/getEmpByEmpNumber.do',
                            contentType: 'application/x-www-form-urlencoded',
                            data: {
                                'empNumber': value
                            },
                            beforeSend: function (xhr) {
                                xhr.withCredentials = true;
                            },
                            success: function (response) {
                                console.log(response);
                                if (response.status) {
                                    var data = response.data;
                                    if (app.addForm) {
                                        app.addForm.getData().empId = data ? data[0].empId : null;
                                        app.addForm.setValue('empName', data ? data[0].empName : null);
                                    }
                                    if (app.editForm) {
                                        app.editForm.getData().empId = data ? data[0].empId : null;
                                        app.editForm.setValue('empName', data ? data[0].empName : null);
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
    });
};

app.lockScreen = function () {
    // todo
};

app.refresh = function () {
    if (app.currentPageName) {
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
/*
    初始化卡事件（卡）
 */
app.InitializationCard = function () {
    var res = app.ReadCard();
    if(res =='IC卡未插入写卡器.'|| res =='卡类型不正确.'|| res =='写卡器连接错误.'){
        app.errorMessage(res)
        return;
    }
    //数据转换
    if(res[0] && res[0]=='S'){

        res[0]="读卡成功"
    }else{

        res[0]="读卡失败"
    }
    if(res[1] && res[1]=='0'){

        res[1]="新卡"
    }else if(res[1] && res[1]=='1'){

        res[1]="密码传递卡"
    }else if(res[1] && res[1]=='2'){

        res[1]="一般充值卡"
    }
    //生成标题
    var cardTitle=["执行结果","卡类型","卡序列号","IC卡编号","卡内气量(单位:0.1方)","维修次数","流水号"]
    app.initCardList(res,cardTitle);
}
/*
读取卡面数据,初始化（卡）
 */
app.initCardList = function (res, cardTitle) {
    var password;
    var cardListDialog = mdui.dialog({
        title: '卡面数据',
        modal: true,
        content: ' ',
        buttons: [{
            text: '初始化卡',
            onClick: function () {
                $.ajax({
                    type: 'POST',
                    url: 'account'+'/redCard.do',
                    contentType: 'application/x-www-form-urlencoded',
                    data:{
                        cardId:res[3]
                    } ,
                    beforeSend: function (xhr) {
                        xhr.withCredentials = true;
                    },
                    success: function (response) {
                        if(response.status){
                            password =response.data.cardPassword;
                            var result=  app.initCard(password);
                            if(result=='S'){
                                $.ajax({
                                    type: 'POST',
                                    url: 'account'+'/initCard.do',
                                    contentType: 'application/x-www-form-urlencoded',
                                    data:{
                                        cardId:res[3],result:result
                                    } ,
                                    beforeSend: function (xhr) {
                                        xhr.withCredentials = true;
                                    }
                                });
                                app.successMessage("已成功初始化")

                            }else if(result=='ocx.ErrorDesc'){

                                app.errorMessage("初始化失败")
                            }
                        }else{
                            app.errorMessage(response.message);
                        }
                    }
                });
            }

        }, {
            text: '取消'
        }],
        content:cardTitle[0]+":"+"&nbsp;&nbsp;&nbsp;&nbsp;"+res[0]+"&nbsp;&nbsp;&nbsp;&nbsp;"+
        cardTitle[1]+":"+"&nbsp;&nbsp;&nbsp;&nbsp;"+res[1]+ "<br / >"+
        cardTitle[2]+":"+"&nbsp;&nbsp;&nbsp;&nbsp;"+res[2]+"&nbsp;&nbsp;&nbsp;&nbsp;"+
        cardTitle[3]+":"+"&nbsp;&nbsp;&nbsp;&nbsp;"+res[3]+"<br / >"+
        cardTitle[4]+":"+"&nbsp;&nbsp;&nbsp;&nbsp;"+res[4]+"&nbsp;&nbsp;&nbsp;&nbsp;"+
        cardTitle[5]+":"+"&nbsp;&nbsp;&nbsp;&nbsp;"+res[5]+"&nbsp;&nbsp;&nbsp;&nbsp;"+
        cardTitle[6]+":"+"&nbsp;&nbsp;&nbsp;&nbsp;"+res[6]
    });
    $(".tree-combobox-panel").remove();


};
app.render = function (context) {
    // 从缓存中读取数据
    // var data = app.getDataCache(context.url);
    var data = null;
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
                // app.setDataCache(context.url, data);
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
    if(table!=null){
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
                            response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                            if (response.status) {
                                var url = app.currentPageName + '/listData.do';
                                // app.setDataCache(url, null);
                                console.log("清理" + url + "缓存");
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
        if (table.getSelectedDatas().length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (table.getSelectedDatas().length > 1) {
            app.message('只能选择一条数据');
            return;
        }
        if (app.currentPageName == 'account') {
            var result = app.ReadCard();
            if (result[0] !== 'S') {
                app.errorMessage(result);
                return;
            }
            if (result[1] != '0') {
                app.warningMessage('只能对新卡进行开户');
                return;
            }
        }
        if (app.currentPageName == 'prePayment') {
            var result = app.ReadCard();
            if (result[0] !== 'S') {
                app.errorMessage(result);
                return;
            }
            if (result[1] == '0') {
                app.warningMessage('该卡为新卡，请使用发卡充值');
                return;
            }
            if (result[1] == '1') {
                app.warningMessage('该卡为密码传递卡，不能充值');
                return;
            }
            if (result[4] != '0') {
                var msg = "卡内已有未圈存的气量,确认覆盖已有气量继续充值吗";
                if (confirm(msg) == false) {
                    return;
                }
            }
            if(result[2] != table.getSelectedDatas()[0].iccardIdentifier || result[3] != table.getSelectedDatas()[0].cardId){
                app.warningMessage("该卡不是与该用户绑定的卡");
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
                            response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                            if (response.status) {
                                var rdata = response.data;
                                if (app.currentPageName == "account" || app.currentPageName == 'replaceCard' || app.currentPageName == 'prePayment') {
                                    app.WriteCard(rdata);
                                }
                                var url = app.currentPageName + '/listData.do';
                                // app.setDataCache(url, null);
                                console.log("清理" + url + "缓存");
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
        if (app.currentPageName == 'account' || app.currentPageName == 'prePayment') {
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
                                // app.setDataCache(url, null);
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
                                    // app.setDataCache(url, null);
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
            url: app.currentPageName + '/List.do',
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
                    fields: app.tableFields[app.currentPageName + 'History'],
                    data: data
                });
                dialog.handleUpdate();
            }
        });
    });
    main.on('pictureinpicturealt', function () {
        app.InitializationCard();
    });
    main.on('record_voice_over', function () {

        var result = app.ReadCard();
        if (result[0] !== 'S') {
            app.errorMessage(result);
            return;
        }
        $.ajax({
            type: 'POST',
            url: app.currentPageName + '/search.do',
            contentType: 'application/x-www-form-urlencoded',
            data: {"iccardIdentifier": result[2],"cardOrderGas": result[4]},
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
    main.on('link_name', function () {
        var data = table.getSelectedDatas();
        if (data.length <= 0){
            app.message('请至少选择一条数据');
            return;
        }

    });
    main.on('tranfer', function () {
        var data = table.getSelectedDatas();
        if (data.length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (data.length > 1) {
            app.message('请选择一条数据');
            return;
        }
        var userMoney = 0;
        var OrderSupplement = 0;

        var dialog = mdui.dialog({
            title: '录入新用户信息',
            content: ' ',
            buttons: [{
                text: '确定',
                onClick: function () {
                    var data = form.getData();
                    data.userMoney = userMoney;
                    data.OrderSupplement = OrderSupplement;
                    console.log(data)
                    $.ajax({
                        type: 'POST',
                        url: app.currentPageName + '/userChangeSettlement.do',
                        contentType: 'application/x-www-form-urlencoded',
                        data: data,
                        beforeSend: function (xhr) {
                            xhr.withCredentials = true;
                        },
                        success: function (response) {
                            console.log(response)
                            if (response.status) {
                                //成功变更账户，无超用信息
                                if (response.data == null) {
                                    alert(response.message)

                                } else {
                                    //实际补缴超用userMoney
                                    console.log(data)
                                    var userMoney = prompt(response.message, response.data[0]);
                                    if (userMoney) {

                                        data.userMoney = userMoney;
                                        //应补缴超用
                                        data.OrderSupplement = response.data[1];
                                        console.log(data.OrderSupplement)
                                        $.ajax({
                                            type: 'POST',
                                            url: app.currentPageName + '/userChangeSettlement.do',
                                            contentType: 'application/x-www-form-urlencoded',
                                            data: data,
                                            beforeSend: function (xhr) {
                                                xhr.withCredentials = true;
                                            },
                                            success: function (response) {
                                                response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                                                if (response.status) {
                                                    var url = app.currentPageName + '/listData.do';
                                                    // app.setDataCache(url, null);
                                                    console.log("清理" + url + "缓存");
                                                    app.render({
                                                        url: url
                                                    });
                                                }
                                            }
                                        });

                                    }

                                }

                            } else {
                                app.errorMessage(response.message);
                            }
                            if (response.status) {
                                var url = app.currentPageName + '/listData.do';
                                // app.setDataCache(url, null);
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
            fields: app.getEditFormFields('alter'),
            data: data[0]
        });
        dialog.handleUpdate();
    });
    main.on('assignment', function () {
        var dialog = mdui.dialog({
            title: '分配',
            modal: true,
            content: ' ',
            buttons: [{
                text: '确认',
                onClick: function () {
                    var data = form.getData();
                    $.ajax({
                        type: 'POST',
                        url: app.currentPageName + '/assignment.do',
                        contentType: 'application/x-www-form-urlencoded',
                        data: data,
                        beforeSend: function (xhr) {
                            xhr.withCredentials = true;
                        },
                        success: function (response) {
                            response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                            if (response.status) {
                                var url = app.currentPageName + '/listData.do';
                                // app.setDataCache(url, null);
                                console.log("清理" + url + "缓存");
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
            fields: app.getAddFormFields(formNames + 'assignment')
        });
        dialog.handleUpdate();
    });
    main.on('fillGas', function () {
        function editFillGasOrder(formData) {
            $.ajax({
                type: 'POST',
                url: 'fillGas/edit.do',
                contentType: 'application/x-www-form-urlencoded',
                data: formData,
                beforeSend: function (xhr) {
                    xhr.withCredentials = true;
                },
                success: function (editFillGasOrderResponse) {
                    if (editFillGasOrderResponse.status) {
                        app.successMessage(formData.fillGasOrderType === 1 ? '处理补气单成功' : '处理补缴单成功');
                        // app.setDataCache(url, null);
                        app.render({
                            url: app.currentPageName + '/listData.do'
                        });
                    } else {
                        app.errorMessage(editFillGasOrderResponse.message);
                    }
                }
            });
        }
        var datas = table.getSelectedDatas();
        if (datas.length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (datas.length > 1) {
            app.message('只能选择一条数据');
            return;
        }
        if (datas[0].fillGasOrderStatus === 1){
            app.message('补气单已处理');
            return;
        }
        if (datas[0].fillGasOrderStatus === 2) {
            app.message('补气单已撤销');
            return;
        }
        var dialog = mdui.dialog({
            title: '补气补缴',
            modal: true,
            content: ' ',
            buttons: [{
                text: '确认',
                onClick: function () {
                    var formData = form.getData();
                    var userId = formData.userId;
                    var result = app.ReadCard();
                    if (result[0] === 'S') {
                        $.ajax({
                            type: 'POST',
                            url: 'account/redCard.do',
                            contentType: 'application/x-www-form-urlencoded',
                            data: {
                                cardId: result[3]
                            },
                            beforeSend: function (xhr) {
                                xhr.withCredentials = true;
                            },
                            success: function (readCardResponse) {
                                if (readCardResponse.status) {
                                    var password = readCardResponse.data.cardPassword;
                                    switch (formData.fillGasOrderType) {
                                        case 1:
                                            var serviceTimes = app.getServiceTimesByUserId(userId);
                                            var flowNum = app.getFlowNum();
                                            var isFirstOrder = formData.needFillGas === (formData.gasCount - formData.stopCodeCount);
                                            if (isFirstOrder) {
                                                // 初始化IC卡
                                                var initResult = app.initCard(password);
                                                if (initResult === 'S') {
                                                    // 获取流水号 获取维修次数
                                                    var writePCardResult = app.WritePCard(result[3], password, formData.fillGas, serviceTimes, formData.fillGas, flowNum);
                                                    if (writePCardResult === '写卡成功') {
                                                        // 更新订单状态，生成新订单，
                                                        editFillGasOrder(formData);
                                                    } else {
                                                        app.errorMessage(writePCardResult);
                                                    }
                                                } else {
                                                    app.errorMessage("初始化失败");
                                                }
                                            } else {
                                                //写一般充值卡
                                                var writeUCardResult = app.WriteUCard(result[3], password, formData.fillGas, serviceTimes, flowNum);
                                                if (writeUCardResult === '写卡成功') {
                                                    editFillGasOrder(formData);
                                                } else {
                                                    app.errorMessage(writeUCardResult);
                                                }
                                            }
                                            break;
                                        case 2:
                                            // 初始化IC卡
                                            var initResult = app.initCard(password);
                                            if (initResult === 'S') {
                                                editFillGasOrder(formData);
                                            } else {
                                                app.errorMessage("初始化失败");
                                            }
                                            break;
                                        default:
                                            break;
                                    }

                                } else {
                                    app.errorMessage(readCardResponse.message);
                                }
                            }
                        });
                    } else {
                        app.errorMessage(result);
                    }
                    app.fillGasForm = null;
                }
            }, {
                text: '取消',
                onClick: function () {
                    app.fillGasForm = null;
                }
            }]
        });
        $(".tree-combobox-panel").remove();
        var data = datas[0];
        var fillGasFormFields = ['fillGas', 'overuse'];
        var form = app.fillGasForm = app.createForm({
            parent: '.mdui-dialog-content',
            fields: app.getEditFormFields(data.fillGasOrderType === 1 ? 'fillGas' : 'overuse'),
            data: table.getSelectedDatas()[0]
        });
        dialog.handleUpdate();
    });
    /**
     * 账户销户
     */
    main.on('add_to_queue', function () {
        var data = table.getSelectedDatas();
        console.log(data[0])
        if (data.length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (data.length > 1) {
            app.message('请选择一条数据');
            return;
        }
        if(window.confirm('确定要对该户名为:'+data[0].userName+'销户吗？')){

        var userMoney = 0;
        var OrderSupplement = 0;
        var flage = 0;
        data[0].userMoney = userMoney;
        data[0].OrderSupplement = OrderSupplement;
        data[0].flage = flage;
        $.ajax({
            type: 'POST',
            url: app.currentPageName + '/userEliminationHead.do',
            contentType: 'application/x-www-form-urlencoded',
            data: data[0],
            beforeSend: function (xhr) {
                xhr.withCredentials = true;
            },
            success: function (response) {
                //成功注销，不涉及账务问题
                if (response.status) {

                    if (response.data == null) {
                        app.successMessage(response.message)
                        var url = app.currentPageName + '/listData.do';
                        // app.setDataCache(url, null);
                        console.log("清理" + url + "缓存");
                        app.render({
                            url: url
                        });

                    } else {
                        //涉及用气退钱,用户超用补缴
                        var userMoney = prompt(response.message, response.data[0]);
                        if (userMoney) {
                            data[0].userMoney = userMoney;
                            //应补缴超用
                            data[0].OrderSupplement = response.data[1];
                            data[0].flage = response.data[2];
                            $.ajax({
                                type: 'POST',
                                url: app.currentPageName + '/userEliminationHead.do',
                                contentType: 'application/x-www-form-urlencoded',
                                data: data[0],
                                beforeSend: function (xhr) {
                                    xhr.withCredentials = true;
                                },
                                success: function (response) {
                                    response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                                    if (response.status) {
                                        var url = app.currentPageName + '/listData.do';
                                        // app.setDataCache(url, null);
                                        console.log("清理" + url + "缓存");
                                        app.render({
                                            url: url
                                        });
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
            return true;
        }else{
            return false;
        }
    })
    //查看历史变更记录
    main.on('mail_outline', function () {
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
            url: app.currentPageName + '/userChangeList.do',
            data: {
                "userId": userId
            },
            contentType: 'application/x-www-form-urlencoded',
            beforeSend: function (xhr) {
                xhr.withCredentials = true;
            },
            success: function (response) {
                var data = response.data;
                console.log(data);
                var dialog = mdui.dialog({
                    title: '历史变更记录',
                    content: ' ',
                    buttons: [{text: '关闭'}]
                });
                var table = app.createTable({
                    parent: '.mdui-dialog-content',
                    fields: app.tableFields[app.currentPageName + 'History'],
                    data: data
                });
                dialog.handleUpdate();
            }
        });
    })
    main.on('credit_card', function () {
        var data = table.getSelectedDatas();
        if (data.length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (data.length > 1) {
            app.message('请选择一条数据');
            return;
        }
        if (data[0].orderStatus == 2) {
            app.message('该订单已写卡成功，不能补写');
            return;
        }
        app.WriteCard(data[0]);

    });
    /**
     * 查询统计，导出EXCEL
     */
    main.on('screen_share', function () {

        var data = table.getSelectedDatas();
        if (data.length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (data.length > 1) {
            app.message('请选择一条数据');
            return;
        }
        var str = "用户编号,IC卡号,卡识别号,客户姓名,客户电话,客户地址,购气次数,购气总量,卡内气量";
        var name ="IC卡导出数据";
        app.excelUtils(data,str,name);

    });
    /**
     * 通用导出
     */
    main.on('arrow_downward', function () {
        var data = app.toolbar.getInputsData();
        app.DownLoadFile({
            url : app.currentPageName + '/export.do',
            data : data
        });
    });
    /**
     * 预冲账发起
     */
    main.on('touch_app',function () {
        var data = table.getSelectedDatas();
        console.log(data);
        if (data.length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (data.length > 1) {
            app.message('只能选择一条数据');
            return;
        }

        if(data[0].accountState !=undefined){

            app.errorMessage("已经发起过的账单不可再次发起")
            return ;

        }

       var flag= confirm("确定发起该笔预冲账申请?")
        if(flag){
            $.ajax({
                type: 'POST',
                url: app.currentPageName +'/edit.do',
                contentType: 'application/x-www-form-urlencoded',
                data: data[0],
                beforeSend: function (xhr) {
                    xhr.withCredentials = true;
                },
                success: function (response) {
                    response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                    if (response.status) {
                        var url = app.currentPageName + '/listData.do';
                        // app.setDataCache(url, null);
                        console.log("清理" + url + "缓存");
                        app.render({
                            url: url
                        });
                    }
                }
            });

        }
    })
    /**
     * 用户变更信息查询
     */
    main.on('gradient', function () {

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
            url: app.currentPageName + '/historyQuery.do',
            data: {
                "userId": userId
            },
            contentType: 'application/x-www-form-urlencoded',
            beforeSend: function (xhr) {
                xhr.withCredentials = true;
            },
            success: function (response) {
                var data = response.data;
                console.log(data);
                var dialog = mdui.dialog({
                    title: '历史变更记录',
                    content: ' ',
                    buttons: [{text: '关闭'}]
                });
                var table = app.createTable({
                    parent: '.mdui-dialog-content',
                    fields: app.tableFields[app.currentPageName + 'History'],
                    data: data
                });
                dialog.handleUpdate();
            }
        });

    });
    /**
     * 充值记录查询
     */
    main.on('note_alt', function () {
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
            url: app.currentPageName + '/historyQueryOrders.do',
            data: {
                "userId": userId
            },
            contentType: 'application/x-www-form-urlencoded',
            beforeSend: function (xhr) {
                xhr.withCredentials = true;
            },
            success: function (response) {
                var data = response.data;
                console.log(data);
                var dialog = mdui.dialog({
                    title: '充值记录',
                    content: ' ',
                    buttons: [{text: '关闭'}]
                });
                var table = app.createTable({
                    parent: '.mdui-dialog-content',
                    fields: app.tableFields[app.currentPageName + 'HistoryOrders'],
                    data: data
                });
                dialog.handleUpdate();
            }
        });

    });
    /**
     * 维修记录
     */
    main.on('build', function () {
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
            url: app.currentPageName + '/historyFillGasOrder.do',
            data: {
                "userId": userId
            },
            contentType: 'application/x-www-form-urlencoded',
            beforeSend: function (xhr) {
                xhr.withCredentials = true;
            },
            success: function (response) {
                var data = response.data;
                console.log(data);
                var dialog = mdui.dialog({
                    title: '维修记录',
                    content: ' ',
                    buttons: [{text: '关闭'}]
                });
                var table = app.createTable({
                    parent: '.mdui-dialog-content',
                    fields: app.tableFields[app.currentPageName + 'HistoryFillGasOrder'],
                    data: data
                });
                dialog.handleUpdate();
            }
        });

    });
    /**
     * 用户,卡相关记录
     */
    main.on('userQuery_tab', function () {
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
            url: app.currentPageName + '/historyUserCard.do',
            data: {
                "userId": userId
            },
            contentType: 'application/x-www-form-urlencoded',
            beforeSend: function (xhr) {
                xhr.withCredentials = true;
            },
            success: function (response) {
                var data = response.data;
                console.log(data);
                var dialog = mdui.dialog({
                    title: 'IC卡相关信息',
                    content: ' ',
                    buttons: [{text: '关闭'}]
                });
                var table = app.createTable({
                    parent: '.mdui-dialog-content',
                    fields: app.tableFields[app.currentPageName + 'HistoryUserCard'],
                    data: data
                });
                dialog.handleUpdate();
            }
        });



    });
    /**
     * 发起审批--冲账
     */
    main.on('assignment_turned_in', function () {
        var tableData = table.getSelectedDatas();
        if (table.getSelectedDatas().length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (table.getSelectedDatas().length > 1) {
            app.message('只能选择一条数据');
            return;
        }
        //标识
        var flag = true;
        var cardListDialog = mdui.dialog({
            title: '审核',
            modal: true,
            content: ' ',
            buttons: [{
                text: '审核通过',
                onClick: function () {
                    var data = form.getData();
                    data.flag = true;
                    console.log(data)

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
                                // app.setDataCache(url, null);
                                console.log("清理" + url + "缓存");
                                app.render({
                                    url: url
                                });
                            }
                        }
                    });

                }

            }, {
                text: '不通过',
                onClick: function () {
                    var data = form.getData();
                    data.flag = false;
                    console.log(data)

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
                                // app.setDataCache(url, null);
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
        var form = app.createForm({
            parent: '.mdui-dialog-content',
            fields: app.getEditFormFields('strike'),
            data: tableData[0]
        });
        cardListDialog.handleUpdate();
    });
    // 发票打印
    main.on('print', function () {
        var data = table.getSelectedDatas();
        if (data.length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (data.length > 1) {
            app.message('请选择一条数据');
            return;
        }
        if (data[0].invoiceStatusName != undefined) {
            app.message('该订单已有打印记录，请选择补打');
            return;
        }
        app.findInvoice(data[0], 1);

    });
    //原票打印
    main.on('crop_original', function () {
        var data = table.getSelectedDatas();
        if (data.length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (data.length > 1) {
            app.message('请选择一条数据');
            return;
        }
        if (data[0].invoiceStatusName == undefined) {
            app.message('该订单还没打印过，无法补打');
            return;
        }
        if (data[0].invoiceStatusName == '已作废') {
            app.message('该订单已作废过，无法原票补打');
            return;
        }
        app.findInvoice(data[0], 2);
    });
    //新票打印
    main.on('fiber_new', function () {
        var data = table.getSelectedDatas();
        if (data.length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (data.length > 1) {
            app.message('请选择一条数据');
            return;
        }
        if (data[0].invoiceStatusName == undefined) {
            app.message('该订单还没打印过，无法补打');
            return;
        }
        app.findInvoice(data[0], 3);
    });
    //发票作废
    main.on('cancel', function () {
        var data = table.getSelectedDatas();
        if (data.length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (data.length > 1) {
            app.message('请选择一条数据');
            return;
        }
        if(app.currentPageName == 'order') {
            if (data[0].invoiceStatusName == undefined) {
                app.message('该订单还没打印过，无法作废');
                return;
            }
            $.ajax({
                async: true,
                type: 'Post',
                url: app.currentPageName + '/cancel.do',
                data: {
                    "orderId": data[0].orderId,
                    "userId": data[0].userId,
                    "invoiceCode": data[0].invoiceCode,
                    "invoiceNumber": data[0].invoiceNumber
                },
                contentType: 'application/x-www-form-urlencoded',
                beforeSend: function (xhr) {
                    xhr.withCredentials = true;
                },
                success: function (response) {
                    response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                    if (response.status) {
                        var url = app.currentPageName + '/listData.do';
                        // app.setDataCache(url, null);
                        console.log("清理" + url + "缓存");
                        app.render({
                            url: url
                        });
                    }
                }
            });
        }
        if(app.currentPageName == 'printCancel') {
            if (data[0].invoiceStatusName == '已作废') {
                app.message('该发票已作废过，无法再作废');
                return;
            }
            $.ajax({
                async: true,
                type: 'Post',
                url: app.currentPageName + '/cancel.do',
                data: {
                    "invoiceCode": data[0].invoiceCode,
                    "invoiceNumber": data[0].invoiceNumber
                },
                contentType: 'application/x-www-form-urlencoded',
                beforeSend: function (xhr) {
                    xhr.withCredentials = true;
                },
                success: function (response) {
                    response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                    if (response.status) {
                        var url = app.currentPageName + '/listData.do';
                        // app.setDataCache(url, null);
                        console.log("清理" + url + "缓存");
                        app.render({
                            url: url
                        });
                    }
                }
            });
        }
    });
    // 维修单录入
    main.on('receipt', function () {
        var dialog = mdui.dialog({
            title: '新增维修单',
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
        form.$dom.on('change', function (event) {
            var $target = $(event.target);
            var value = $target.val();
            switch (event.target.name) {
                case 'repairType':
                    // 换表、机换IC卡、工商户换表
                    if (value === '0' || value === '6' || value === '7') {
                        form.showField('newMeterCode');
                        form.showField('newMeterTypeId');
                        form.showField('newMeterDirection');
                        form.showField('newMeterStopCode');
                        form.showField('newSafetyCode');
                    } else {
                        form.hideField('newMeterCode');
                        form.hideField('newMeterTypeId');
                        form.hideField('newMeterDirection');
                        form.hideField('newMeterStopCode');
                        form.hideField('newSafetyCode');
                    }
                    break;
                default:
                    break;
            }
        });
        dialog.handleUpdate();
    });
    // 维修单编辑
    main.on('repairOrderEdit', function () {
        /**
         * 判断补气单是否被处理
         */
        function hasFillGasOrderResolved(userId, repairOrderId) {
            var result;
            $.ajax({
                type: 'POST',
                async: false,
                url: 'input/hasFillGasOrderResolved.do',
                contentType: 'application/x-www-form-urlencoded',
                data: {
                    userId: userId,
                    repairOrderId: repairOrderId
                },
                beforeSend: function (xhr) {
                    xhr.withCredentials = true;
                },
                success: function (response) {
                    if (response.status) {
                        result = response.data;
                    }
                }
            });
            return result;
        }

        /**
         * 判断是否为最近的一条订单
         */
        function isLatestFillGasOrder(id, userId) {
            var result;
            $.ajax({
                type: 'POST',
                async: false,
                url: 'input/isLatestFillGasOrder.do',
                contentType: 'application/x-www-form-urlencoded',
                data: {
                    id: id,
                    userId: userId
                },
                beforeSend: function (xhr) {
                    xhr.withCredentials = true;
                },
                success: function (response) {
                    if (response.status) {
                        result = response.data;
                    }
                }
            });
            return result;
        }
        var selectDatas = table.getSelectedDatas();
        if (table.getSelectedDatas().length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (table.getSelectedDatas().length > 1) {
            app.message('只能选择一条数据');
            return;
        }
        if (!isLatestFillGasOrder(selectDatas[0].id, selectDatas[0].userId)) {
            app.message("该维修单不是最新的，不能编辑");
            return;
        }
        if (hasFillGasOrderResolved(selectDatas[0].userId, selectDatas[0].repairOrderId)) {
            app.message("该维修单的补气单或超用单已被处理，不能编辑");
            return;
        }
        var dialog = mdui.dialog({
            title: '编辑维修单',
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
        var data = form.getData();
        // 判断是否为换表
        if (data.repairType === 0 || data.repairType === 6 || data.repairType === 7) {
            form.disableField("repairType");
            form.disableField("gasEquipmentType");
            form.disableField("oldMeterTypeId");
            form.disableField("oldMeterDirection");
            form.disableField("newMeterTypeId");
            form.disableField("newMeterDirection");
            form.disableField("repairFaultType");
            form.disableField("repairResultType");
        } else {
            //禁用新表输入项
            form.hideField('newMeterCode');
            form.hideField('newMeterTypeId');
            form.hideField('newMeterDirection');
            form.hideField('newMeterStopCode');
            form.hideField('newSafetyCode');
            form.disableField("repairType");
            form.disableField("oldMeterTypeId");
            form.disableField("oldMeterDirection");
            form.disableField("gasEquipmentType");
            // 判断是否为补气维修单
            if(data.repairResultType === 4 || data.repairResultType === 9) {
                form.disableField("repairFaultType");
                form.disableField("repairResultType");
            }
        }
        dialog.handleUpdate();
    });
    // 绑定新卡
    main.on('bindNewCard', function(){
        function getBindNewCardParamByUserId(userId) {
            var data = null;
            $.ajax({
                type: 'POST',
                async: false,
                url: 'input/getBindNewCardParamByUserId.do',
                contentType: 'application/x-www-form-urlencoded',
                data: {
                    userId: userId
                },
                beforeSend: function (xhr) {
                    xhr.withCredentials = true;
                },
                success: function (response) {
                    if (response.status) {
                        data = response.data;
                    }
                }
            });
            return data;
        }
        if (table.getSelectedDatas().length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (table.getSelectedDatas().length > 1) {
            app.message('只能选择一条数据');
            return;
        }
        var dialog = mdui.dialog({
            title: '绑定新卡',
            content: ' ',
            buttons: [{
                text: '确认',
                onClick: function () {
                    var data = form.getData();
                    $.ajax({
                        type: 'POST',
                        url: app.currentPageName + '/bindNewCard.do',
                        contentType: 'application/x-www-form-urlencoded',
                        data: data,
                        beforeSend: function (xhr) {
                            xhr.withCredentials = true;
                        },
                        success: function (response) {
                            response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                        }
                    });
                    app.bindNewCardForm = null;
                }
            }, {
                text: '取消',
                onClick: function () {
                    app.bindNewCardForm = null;
                }
            }]
        });
        $(".tree-combobox-panel").remove();
        var form = app.bindNewCardForm= app.createForm({
            parent: '.mdui-dialog-content',
            fields: app.getEditFormFields('bindNewCard'),
            data: getBindNewCardParamByUserId(table.getSelectedDatas()[0].userId)
        });
        dialog.handleUpdate();
    });
    main.on('payment', function () {
        if (table.getSelectedDatas().length === 0) {
            app.message('请选择一条数据');
            return;
        }
        if (table.getSelectedDatas().length > 1) {
            app.message('只能选择一条数据');
            return;
        }
        var result = app.ReadCard();
        if (result[0] !== 'S') {
            app.errorMessage(result);
            return;
        }
        if (result[1] != '0') {
            app.warningMessage('只能对新卡进行发卡充值');
            return;
        }
        if(result[2] != table.getSelectedDatas()[0].iccardIdentifier){
            app.warningMessage("该卡不是与该用户绑定的卡");
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
                                var rdata = response.data;
                                var wresult = app.WritePCard(rdata.iccardId, rdata.iccardPassword, rdata.orderGas, rdata.serviceTimes, rdata.orderGas, rdata.flowNumber);
                                app.updateOrderStatus(wresult);
                                var url = app.currentPageName + '/listData.do';
                                // app.setDataCache(url, null);
                                console.log("清理" + url + "缓存");
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
        if (table.getSelectedDatas()[0]['meterCategory'] == 'IC卡表')
            formNames = app.currentPageName + 'IC';
        else
            formNames = app.currentPageName + 'MessAndUnion';
        var form = app.editForm = app.createForm({
            parent: '.mdui-dialog-content',
            fields: app.getEditFormFields(formNames),
            data: table.getSelectedDatas()[0]
        });
        dialog.handleUpdate();
    });
};
app.verifyCard = function(result,data){
    if(result[2] != data.iccardIdentifier || result[3] != data.cardId){
        app.warningMessage("该卡不是与该用户绑定的卡");
        return false;
    }
    return true;

}
app.DownLoadFile = function (options) {
    var config = $.extend(true, { method: 'post' }, options);
    var $iframe = $('<iframe id="down-file-iframe" />');
    var $form = $('<form target="down-file-iframe" method="' + config.method + '" />');
    $form.attr('action', config.url);
    for (var key in config.data) {
        $form.append('<input type="hidden" name="' + config.data[key].name + '" value="' + config.data[key].value + '" />');
    }
    $iframe.append($form);
    $(document.body).append($iframe);
    $form[0].submit();
    $iframe.remove();
}
app.findInvoice = function (data, printType) {
    $.ajax({
        async: true,
        type: 'Post',
        url: '/findInvoice.do',
        data: {
            "orderId": data.orderId,
            "userId": data.userId,
            "printType": printType
        },
        contentType: 'application/x-www-form-urlencoded',
        beforeSend: function (xhr) {
            xhr.withCredentials = true;
        },
        success: function (response) {
            if (response.status) {
                var sdata = response.data;
                app.PrintInvoice(data, sdata);
            }else{
                app.errorMessage(response.message);
            }
        }
    });
}
app.PrintInvoice = function(data ,sdata){
    $.ajax({
        async: true,
        type: 'Post',
        url: '/print.do',
        data: {
            "orderId": data.orderId ,
            "invoiceCode" : sdata.invoiceCode,
            "invoiceNumber" : sdata.invoiceNumber
        },
        contentType: 'application/x-www-form-urlencoded',
        beforeSend: function (xhr) {
            xhr.withCredentials = true;
        },
        success: function (response) {
            response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
            if (response.status) {
                var url = app.currentPageName + '/listData.do';
                // app.setDataCache(url, null);
                console.log("清理" + url + "缓存");
                app.render({
                    url: url
                });
            }
        }
    });
    console.log("打印");
}
app.removeEvent = function () {
    $('.container-main').off();
};

app.WriteCard = function(rdata){
    var wresult;
    if (app.currentPageName == 'account') {
        wresult = app.WritePCard(rdata.iccardId, rdata.iccardPassword, rdata.orderGas, 0, rdata.orderGas, rdata.flowNumber);
    }
    if (app.currentPageName == 'prePayment') {
        wresult = app.WriteUCard(rdata.iccardId, rdata.iccardPassword, rdata.orderGas, rdata.serviceTimes, rdata.flowNumber);
    }
    if(app.currentPageName == 'replaceCard'){
        app.WritePCard(rdata.iccardId, rdata.iccardPassword, 0, rdata.serviceTimes, 0, rdata.flowNumber);
        wresult = app.WriteUCard(rdata.iccardId, rdata.iccardPassword, rdata.orderGas, rdata.serviceTimes, rdata.flowNumber);
    }
    if(app.currentPageName == 'order'){
        var result = app.ReadCard();
        if (result[0] !== 'S') {
            app.errorMessage(result);
            return;
        }
        if(rdata.orderStatus == 2){
            app.warningMessage("该订单已经写卡成功，不能补写");
        }
        if(rdata.orderType == 1){
            wresult = app.WritePCard(rdata.iccardId, rdata.iccardPassword, rdata.orderGas, 0, rdata.orderGas, rdata.flowNumber);
        }
        if(rdata.orderType == 2){
            wresult = app.WriteUCard(rdata.iccardId, rdata.iccardPassword, rdata.orderGas, rdata.serviceTimes, rdata.flowNumber);
        }
        if(rdata.orderType == 3){
            app.WritePCard(rdata.iccardId, rdata.iccardPassword, 0, rdata.serviceTimes, 0, rdata.flowNumber);
            wresult = app.WriteUCard(rdata.iccardId, rdata.iccardPassword, rdata.orderGas, rdata.serviceTimes, rdata.flowNumber);
        }
    }
    app.updateOrderStatus(wresult,rdata.orderId);
}

app.updateOrderStatus = function(wresult,orderId) {
    if(wresult == '写卡成功'){
        $.ajax({
            type: 'POST',
            async: false,
            url: 'order/updateOrderStatus.do',
            data : {
                'orderId' : orderId,
                'orderStatus' : 2
            },
            contentType: 'application/x-www-form-urlencoded',
            beforeSend: function (xhr) {
                xhr.withCredentials = true;
            },
            success: function (response) {
                response.status ? app.successMessage(response.message) : app.errorMessage(response.message);
                if (response.status) {
                    var url = app.currentPageName + '/listData.do';
                    // app.setDataCache(url, null);
                    console.log("清理" + url + "缓存");
                    app.render({
                        url: url
                    });
                }
            }
        });
    }else {
        app.errorMessage("充值成功，写卡失败，请前往订单页面写卡");
    }
}
/**
 * 获取流水号
 */
app.getFlowNum = function() {
    var result;
    $.ajax({
        type: 'POST',
        async: false,
        url: 'fillGas/getFlowNum.do',
        contentType: 'application/x-www-form-urlencoded',
        beforeSend: function (xhr) {
            xhr.withCredentials = true;
        },
        success: function (response) {
            result = response.data;
        }
    });
    return result;
};

app.getServiceTimesByUserId = function(userId) {
    var result;
    $.ajax({
        type: 'POST',
        async: false,
        url: 'fillGas/getServiceTimesByUserId.do',
        data : {
            userId: userId
        },
        contentType: 'application/x-www-form-urlencoded',
        beforeSend: function (xhr) {
            xhr.withCredentials = true;
        },
        success: function (response) {
            result = response.data;
        }
    });
    return result;
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
    gasPrice: [{
        name: 'userTypeName',
        caption: '用户类型'
    }, {
        name: 'userGasTypeName',
        caption: '用气类型'
    }, {
        name: 'gasRangeOne',
        caption: '第一阶梯起始气量'
    }, {
        name: 'gasPriceOne',
        caption: '第一阶梯气价'
    }, {
        name: 'gasRangeTwo',
        caption: '第二阶梯起始气量(不含)'
    }, {
        name: 'gasPriceTwo',
        caption: '第二阶梯气价'
    }, {
        name: 'gasRangeThree',
        caption: '第三阶梯起始气量(不含)'
    }, {
        name: 'gasPriceThree',
        caption: '第三阶梯气价'
    }, {
        name: 'gasRangeFour',
        caption: '第四阶梯起始气量(不含)'
    }, {
        name: 'gasPriceFour',
        caption: '第四阶梯气价'
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
    }, {
        name: 'meterStatusName',
        caption: '表具状态'
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
    lockAccountHistory: [{
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
    }],
    input: [{
        name: 'repairOrderId',
        caption: '维修单编号'
    }, {
        name: 'userId',
        caption: '户号'
    }, {
        name: 'userName',
        caption: '用户名称'
    }, {
        name: 'userPhone',
        caption: '用户手机'
    }, {
        name: 'userAddress',
        caption: '用户地址'
    }, {
        name: 'repairTypeName',
        caption: '维修类型'
    }, {
        name: 'gasEquipmentTypeName',
        caption: '燃气设备类型'
    }, {
        name: 'oldMeterCode',
        caption: '旧表编号'
    }, {
        name: 'oldMeterTypeName',
        caption: '旧表类型'
    }, {
        name: 'oldMeterDirectionName',
        caption: '旧表表向'
    }, {
        name: 'oldMeterStopCode',
        caption: '旧表止码'
    }, {
        name: 'newMeterCode',
        caption: '新表止码'
    }, {
        name: 'newMeterTypeName',
        caption: '新表类型'
    }, {
        name: 'newMeterDirectionName',
        caption: '新表表向'
    }, {
        name: 'newMeterStopCode',
        caption: '新表止码'
    }, {
        name: 'repairFaultTypeName',
        caption: '维修故障类型'
    }, {
        name: 'repairResultTypeName',
        caption: '维修结果'
    }, {
        name: 'empNumber',
        caption: '员工编号'
    }, {
        name: 'empName',
        caption: '员工姓名'
    }, {
        name: 'repairStartTime',
        caption: '维修开始时间'
    }, {
        name: 'repairEndTime',
        caption: '维修结束时间'
    }, {
        name: 'newSafetyCode',
        caption: '新安全卡编号'
    }, {
        name: 'oldSafetyCode',
        caption: '旧安全卡编号'
    }],
    prePayment: [{
        name: 'userId',
        caption: '用户编号'
    }, {
        name: 'iccardId',
        caption: 'IC卡编号'
    }, {
        name: 'iccardIdentifier',
        caption: 'IC卡识别号'
    }, {
        name: 'userName',
        caption: '用户名'
    }, {
        name: 'userPhone',
        caption: '用户电话'
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
        name: 'totalOrderTimes',
        caption: '购气次数'
    }, {
        name: 'totalOrderGas',
        caption: '购气总量'
    }, {
        name: 'totalOrderPayment',
        caption: '购气总额'
    }],
    replaceCard:[{
        name: 'userId',
        caption: '用户编号'
    },{
        name: 'userName',
        caption: '用户名'
    },{
        name: 'iccardId',
        caption: 'IC卡编号'
    }, {
        name: 'iccardIdentifier',
        caption: 'IC卡识别号'
    }],
    replaceCardHistory: [{
        name: 'userId',
        caption: '用户编号'
    }, {
        name: 'iccardId',
        caption: 'IC卡编号'
    }, {
        name: 'iccardIdentifier',
        caption: 'IC卡识别号'
    }, {
        name: 'castCost',
        caption: '补卡工本费'
    }, {
        name: 'orderGas',
        caption: '充值气量'
    }, {
        name: 'orderPayment',
        caption: '充值金额'
    }, {
        name: 'createTime',
        caption: '换卡时间'
    }],
    fillGas: [{
        name: 'repairOrderId',
        caption: '维修单编号'
    }, {
        name: 'userId',
        caption: '户号'
    }, {
        name: 'fillGasOrderTypeName',
        caption: '订单类型'
    }, {
        name: 'userName',
        caption: '用户名称'
    }, {
        name: 'userPhone',
        caption: '用户手机'
    }, {
        name: 'userAddress',
        caption: '用户地址'
    }, {
        name: 'gasCount',
        caption: '历史购气总量'
    }, {
        name: 'stopCodeCount',
        caption: '历史表止码'
    }, {
        name: 'fillGasOrderStatusName',
        caption: '补气单状态'
    }, {
        name: 'remarks',
        caption: '备注'
    }],
    alter: [{
        name: 'userId',
        caption: '用户编号'
    }, {
        name: 'userName',
        caption: '用户姓名'
    }, {
        name: 'userPhone',
        caption: '用户电话'
    }, {
        name: 'distName',
        caption: '区域名称'
    }, {
        name: 'userAddress',
        caption: '用户地址'
    }, {
        name: 'userIdcard',
        caption: '身份证号'
    }, {
        name: 'userDeed',
        caption: '房产证号'
    }, {
        name: 'userTypeName',
        caption: '用户类型'
    },{
        name: 'userGasTypeName',
        caption: '用气类型'
    },{
        name: 'userStatusName',
        caption: '用户状态'
    }],
    assign: [{
        name: 'invoiceCode',
        caption: '发票代码'
    },{
        name: 'invoiceNumber',
        caption: '发票号码'
    },{
        name: 'invoiceStatusName',
        caption: '发票状态'
    },{
        name: 'createByName',
        caption: '生成员工'
    },{
        name: 'invoiceGenerateTime',
        caption: '发票生成时间'
    }],
    printCancel:[{
        name: 'invoiceCode',
        caption: '发票代码'
    },{
        name: 'invoiceNumber',
        caption: '发票号码'
    },{
        name: 'invoiceStatusName',
        caption: '发票状态'
    },{
        name: 'empName',
        caption: '所属员工'
    },{
        name: 'invoiceAssignTime',
        caption: '发票分配时间'
    },{
        name: 'invoicePrintTime',
        caption: '发票打印时间'
    },{
        name: 'invoiceCancelEmpName',
        caption: '作废人'
    },{
        name: 'invoiceCancelTime',
        caption: '发票作废时间'
    }],
    order : [{
        name: 'orderId',
        caption: '订单编号'
    },{
        name: 'userId',
        caption: '用户编号'
    },{
        name: 'userName',
        caption: '用户名'
    },{
        name: 'iccardId',
        caption: 'IC卡编号'
    },{
        name: 'iccardIdentifier',
        caption: 'IC卡识别号'
    },{
        name: 'orderGas',
        caption: '购气量'
    },{
        name: 'orderPayment',
        caption: '购气金额'
    },{
        name: 'flowNumber',
        caption: '流水号'
    },{
        name: 'orderCreateEmpName',
        caption: '订单生成员工'
    },{
        name: 'orderCreateTime',
        caption: '订单生成时间'
    },{
        name: 'invoiceCode',
        caption: '发票代码'
    },{
        name: 'invoiceNumber',
        caption: '发票号码'
    },{
        name: 'invoiceStatusName',
        caption: '发票状态'
    },{
        name: 'invoicePrintEmpName',
        caption: '发票打印员工'
    },{
        name: 'invoicePrintTime',
        caption: '发票打印时间'
    },{
        name: 'invoiceCancelEmpName',
        caption: '发票作废员工'
    },{
        name: 'invoiceCancelTime',
        caption: '发票作废时间'
    }],
    alterHistory: [{
        name: 'userChangeName',
        caption: '用户名称'
    }, {
        name: 'userChangePhone',
        caption: '用户电话'
    }, {
        name: 'userChangeIdcard',
        caption: '用户身份证号码'
    }, {
        name: 'userChangeDeed',
        caption: '用户房产证号码'
    },{
        name: 'userOldName',
        caption: '旧用户名称'
    }, {
        name: 'userOldPhone',
        caption: '旧用户电话'
    }, {
        name: 'userOldIdcard',
        caption: '旧用户身份证号码'
    }, {
        name: 'userOldDeed',
        caption: '旧用户房产证号码'
    }],
    userQueryHistory: [{
        name: 'userChangeName',
        caption: '用户名称'
    }, {
        name: 'userChangePhone',
        caption: '用户电话'
    }, {
        name: 'userChangeIdcard',
        caption: '用户身份证号码'
    }, {
        name: 'userChangeDeed',
        caption: '用户房产证号码'
    },{
        name: 'userOldName',
        caption: '旧用户名称'
    }, {
        name: 'userOldPhone',
        caption: '旧用户电话'
    }, {
        name: 'userOldIdcard',
        caption: '旧用户身份证号码'
    }, {
        name: 'userOldDeed',
        caption: '旧用户房产证号码'
    }],
    userQueryHistoryOrders: [{
        name: 'userId',
        caption: '用户编号'
    }, {
        name: 'orderPayment',
        caption: '实付金额'
    }, {
        name: 'orderGas',
        caption: '充值气量'
    }, {
        name: 'flowNumber',
        caption: '流水号'
    },{
        name: 'orderSupplement',
        caption: '应付金额'
    }, {
        name: 'orderStatusName',
        caption: '订单状态'
    }, {
        name: 'orderTypeName',
        caption: '订单类型'
    }, {
        name: 'accountStateName',
        caption: '账务状态'
    }, {
        name: 'createTime',
        caption: '创建时间'
    }],
    userQueryHistoryFillGasOrder: [{
        name: 'userId',
        caption: '用户编号'
    }, {
        name: 'fillGasOrderTypeName',
        caption: '订单类型'
    }, {
        name: 'gasCount',
        caption: '历史购气总量'
    }, {
        name: 'stopCodeCount',
        caption: '历史表止码'
    },{
        name: 'needFillGas',
        caption: '应补气量'
    },{
        name: 'fillGas',
        caption: '实补气量'
    }, {
        name: 'leftGas',
        caption: '剩余气量'
    }, {
        name: 'needFillMoney',
        caption: '应补金额'
    }, {
        name: 'fillMoney',
        caption: '实补金额'
    }, {
        name: 'leftMoney',
        caption: '剩余金额'
    }, {
        name: 'fillGasOrderStatusName',
        caption: '订单状态'
    }, {
        name: 'createTime',
        caption: '创建时间'
    }],
    userQueryHistoryUserCard: [{
        name: 'userId',
        caption: '用户编号'
    },{
        name: 'cardId',
        caption: 'IC卡卡号'
    },{
        name: 'cardIdentifier',
        caption: 'IC卡识别号'
    },{
        name: 'cardIdentifier',
        caption: 'IC卡识别号'
    },{
        name: 'cardCost',
        caption: '补卡工本费用'
    },{
        name: 'createTime',
        caption: '创建时间'
    }],
    preStrike:[{
        name: 'userName',
        caption: '用户姓名'
    },{
        name: 'userTypeName',
        caption: '用户类型'
    },{
        name: 'userGasTypeName',
        caption: '用气类型'
    },{
        name: 'orderPayment',
        caption: '实际充值金额(单位:方)'
    },{
        name: 'orderGas',
        caption: '充值气量(单位:元)'
    },{
        name: 'orderTypeName',
        caption: '订单类型'
    },{
        name: 'orderCreateTime',
        caption: '充值时间'
    },{
        name: 'accountStateName',
        caption: '账务状态'
    },{
        name: 'userAddress',
        caption: '用户地址'
    }],
    strike:[{
        name: 'orderId',
        caption: '订单编号'
    },{
        name:'userName',
        caption:'用户名称'
    },{
        name:'nucleusGas',
        caption:'充值气量单位(方)'
    },{
        name:'nucleusPayment',
        caption:'充值金额'
    },{
        name:'nucleusLaunchingPerson',
        caption:'发起人姓名'
    },{
        name:'rechargeTime',
        caption:'充值时间'
    }],
    accountQuery:[{
        name: 'userId',
        caption: '用户编号'
    }, {
        name: 'userName',
        caption: '用户名'
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
        name: 'openByName',
        caption: '开户人'
    }, {
        name: 'openTime',
        caption: '开户时间'
    }],
    ardQuery:[{
        name:'userId',
        caption:'用户编号'
    },{
        name:'iccardId',
        caption:'IC卡号'
    },{
        name:'iccardIdentifier',
        caption:'卡识别号'
    },{
        name:'userName',
        caption:'客户姓名'
    },{
        name:'userPhone',
        caption:'客户电话'
    },{
        name:'userAddress',
        caption:'客户地址'
    },{
        name:'cardOrderGas',
        caption:'卡内气量'
    },{
        name:'totalOrderGas',
        caption:'购气总量'
    },{
        name:'totalOrderTimes',
        caption:'购气次数'
    }],
    exceptionQuery:[{
        name: 'userId',
        caption: '用户编号'
    }, {
        name: 'userName',
        caption: '用户名'
    }, {
        name: 'iccardId',
        caption: 'IC卡卡号'
    }, {
        name: 'iccardIdentifier',
        caption: 'IC卡识别号'
    },{
        name: 'userPhone',
        caption: '用户手机号'
    },{
        name: 'userDistName',
        caption: '用户区域'
    }, {
        name: 'userAddress',
        caption: '用户地址'
    },{
        name: 'totalOrderGas',
        caption: '购气总量'
    }, {
        name: 'totalOrderPayment',
        caption: '购气总额'
    },{
        name: 'startBuyDay',
        caption: '初次购气日期'
    }, {
        name: 'endBuyDay',
        caption: '最后购气日期'
    },{
        name: 'notBuyDayCount',
        caption: '未购气天数'
    }, {
        name: 'monthAveGas',
        caption: '月均购气量'
    }, {
        name: 'monthAvePayment',
        caption: '月均购气金额'
    }],
    businessDataQuery:[{
        name:'orderId',
        caption:'订单ID'
    },{
        name:'userName',
        caption:'用户姓名'
    },{
        name:'accountStateName',
        caption:'账务状态'
    },{
        name:'userPhone',
        caption:'用户电话'
    },{
        name:'userIdcard',
        caption:'用户身份证号'
    },{
        name:'userAddress',
        caption:'用户地址'
    },{
        name:'serviceTimes',
        caption:'维修次数'
    },{
        name:'orderPayment',
        caption:'支付金额'
    },{
        name:'orderGas',
        caption:'充值气量(单位:方)'
    },{
        name:'rechargeTime',
        caption:'充值时间'
    },{
        name:'empName',
        caption:'操作人姓名'
    }],
    userQuery:[{
        name:'userId',
        caption:'用户编号'
    },{
        name:'userName',
        caption:'用户名称'
    },{
        name:'userPhone',
        caption:'用户手机号码'
    },{
        name:'userIdcard',
        caption:'用户身份证号'
    },{
        name:'userAddress',
        caption:'用户地址'
    },{
        name:'createTime',
        caption:'创建时间'
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
            },{
                name: 'isAdmin',
                caption:'是否是管理员',
                type:'listcombobox',
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
                }, {
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
        case 'input':
            return [{
                name: 'repairOrderId',
                caption: '维修单编号',
                required: true,
                maxlength: 20
            }, {
                name: 'userId',
                caption: '户号',
                required: true,
                queryField: true,
                maxlength: 10
            }, {
                name: 'userName',
                caption: '用户名称',
                disabled: true
            }, {
                name: 'userPhone',
                caption: '用户手机',
                disabled: true
            }, {
                name: 'userAddress',
                caption: '用户地址',
                disabled: true
            }, {
                name: 'repairType',
                caption: '维修类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("repair_type")
            }, {
                name: 'gasEquipmentType',
                caption: '燃气设备类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("gas_equipment_type")
            }, {
                name: 'oldMeterCode',
                caption: '旧表编号',
                disabled: true
            }, {
                name: 'oldMeterTypeId',
                caption: '旧表类型',
                type: 'listcombobox',
                options: app.getListComboboxOptions('entry/getAllMeterTypes.do', 'meterType', 'meterTypeId')
            }, {
                name: 'oldMeterDirection',
                caption: '旧表表向',
                type: 'listcombobox',
                options: [{
                    key: '左',
                    value: true
                }, {
                    key: '右',
                    value: false
                }]
            }, {
                name: 'oldMeterStopCode',
                caption: '旧表止码',
                required: true,
                inputType: 'num'
            }, {
                name: 'oldSafetyCode',
                caption: '旧安全卡编号'
            }, {
                name: 'newMeterCode',
                caption: '新表编号',
                queryField: true
            }, {
                name: 'newMeterTypeId',
                caption: '新表类型',
                type: 'listcombobox',
                options: app.getListComboboxOptions('entry/getAllMeterTypes.do', 'meterType', 'meterTypeId')
            }, {
                name: 'newMeterDirection',
                caption: '新表表向',
                type: 'listcombobox',
                options: [{
                    key: '左',
                    value: true
                }, {
                    key: '右',
                    value: false
                }]
            }, {
                name: 'newMeterStopCode',
                caption: '新表止码',
                inputType: 'num'
            }, {
                name: 'newSafetyCode',
                caption: '新安全卡编号'
            }, {
                name: 'repairFaultType',
                caption: '维修故障类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("repair_fault_type")
            }, {
                name: 'repairResultType',
                caption: '维修结果',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("repair_result_type")
            }, {
                name: 'empNumber',
                caption: '维修员工号',
                required: true,
                queryField: true,
                maxlength: 50
            }, {
                name: 'empName',
                caption: '维修员姓名',
                disabled: true
            }, {
                name: 'repairStartTime',
                caption: '维修开始时间',
                type: 'date'
            }, {
                name: 'repairEndTime',
                caption: '维修结束时间',
                type: 'date'
            }];
        case 'assign' :
            return [{
                name: 'invoiceCode',
                caption:'发票代码',
                required: true
            },{
                name: 'sInvoiceNumber',
                caption: '发票起始号码',
                maxlength: 8,
                required: true
            },{
                name: 'eInvoiceNumber',
                caption: '发票终止号码',
                maxlength: 8,
                required: true
            }];
        case 'assignassignment' :
            return [{
                name: 'invoiceCode',
                caption:'发票代码',
                required: true
            },{
                name: 'sInvoiceNumber',
                caption:'发票起始号码',
                maxlength: 8,
                required: true
            },{
                name: 'eInvoiceNumber',
                caption:'发票终止号码',
                maxlength: 8,
                required: true
            },{
                name: 'empId',
                caption:'所属员工',
                type: 'listcombobox',
                options: app.getListComboboxOptions('emp/listData.do', 'empName', 'empId')
            }];
    }
};

app.getTreeComboboxNodes = function (url) {
    // var data = this.getDataCache(url);
    var data = null;
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
                // app.setDataCache(url, data);
                console.log("更新"+ url+ "缓存");
            }
        });
        return data;
    }
};

app.getListComboboxOptions = function (url, k, v) {
    var result = [];
    // var data = this.getDataCache(url);
    var data = null;
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
                // app.setDataCache(url, data);
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
            },  {
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
        case 'gasPrice':
            return [{
                name: 'userTypeName',
                caption: '用户类型',
                disabled: true
            }, {
                name: 'userGasTypeName',
                caption: '用气类型',
                disabled: true
            }, {
                name: 'gasRangeOne',
                caption: '第一阶梯起始气量',
                inputType: 'num'
            }, {
                name: 'gasPriceOne',
                caption: '第一阶梯气价',
                inputType: 'num'
            }, {
                name: 'gasRangeTwo',
                caption: '第二阶梯起始气量(不含)',
                inputType: 'num'
            }, {
                name: 'gasPriceTwo',
                caption: '第二阶梯气价',
                inputType: 'num'
            }, {
                name: 'gasRangeThree',
                caption: '第三阶梯起始气量(不含)',
                inputType: 'num'
            }, {
                name: 'gasPriceThree',
                caption: '第三阶梯气价',
                inputType: 'num'
            }, {
                name: 'gasRangeFour',
                caption: '第四阶梯起始气量(不含)',
                inputType: 'num'
            }, {
                name: 'gasPriceFour',
                caption: '第四阶梯气价',
                inputType: 'num'
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
                caption:'是否是管理员',
                type:'listcombobox',
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
                }, {
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
        case 'prePaymentIC':
            return [{
                name: 'userName',
                caption: '充值用户名',
                disabled: true
            }, {
                name: 'iccardIdentifier',
                caption: '充值IC卡识别号',
                disabled: true
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
        case 'prePaymentMessAndUnion':
            return [{
                name: 'userName',
                caption: '充值用户名',
                disabled: true
            }, {
                name: 'iccardIdentifier',
                caption: '充值IC卡识别号',
                disabled: true
            }, {
                name: 'orderPayment',
                caption: '充值金额',
                required: true
            }];
        case 'replaceCard':
            return [{
                name: 'userName',
                caption: '充值用户名',
                disabled: true
            }, {
                name: 'iccardIdentifier',
                caption: 'IC卡识别号',
                disabled: true
            }, {
                name: 'cardCost',
                caption: '补卡工本费',
                value:app.getDictionaryByCategory("cardCost"),
                disabled: true
            }, {
                name: 'nIcCardIdentifier',
                caption: '新IC卡识别号',
                required: true,
                maxlength: 12
            }, {
                name: 'orderGas',
                caption: '充值气量'
            }, {
                name: 'orderPayment',
                caption: '充值金额',
                disabled: true
            }];
        case 'input':
            return [{
                name: 'repairOrderId',
                caption: '维修单编号',
                required: true,
                maxlength: 20
            }, {
                name: 'userId',
                caption: '户号',
                disabled: true,
                required: true,
                maxlength: 10
            }, {
                name: 'userName',
                caption: '用户名称',
                disabled: true
            }, {
                name: 'userPhone',
                caption: '用户手机',
                disabled: true
            }, {
                name: 'userAddress',
                caption: '用户地址',
                disabled: true
            }, {
                name: 'repairType',
                caption: '维修类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("repair_type")
            }, {
                name: 'gasEquipmentType',
                caption: '燃气设备类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("gas_equipment_type")
            }, {
                name: 'oldMeterCode',
                caption: '旧表编号',
                disabled: true,
                required: true,
                maxlength: 12
            }, {
                name: 'oldMeterTypeId',
                caption: '旧表类型',
                type: 'listcombobox',
                options: app.getListComboboxOptions('entry/getAllMeterTypes.do', 'meterType', 'meterTypeId')
            }, {
                name: 'oldMeterDirection',
                caption: '旧表表向',
                type: 'listcombobox',
                options: [{
                    key: '左',
                    value: true
                },{
                    key: '右',
                    value: false
                }]
            }, {
                name: 'oldMeterStopCode',
                caption: '旧表止码',
                required: true,
                inputType: 'num'
            }, {
                name: 'oldSafetyCode',
                caption: '旧安全卡编号'
            }, {
                name: 'newMeterCode',
                caption: '新表编号',
                queryField: true
            }, {
                name: 'newMeterTypeId',
                caption: '新表类型',
                type: 'listcombobox',
                options: app.getListComboboxOptions('entry/getAllMeterTypes.do', 'meterType', 'meterTypeId')
            }, {
                name: 'newMeterDirection',
                caption: '新表表向',
                type: 'listcombobox',
                options: [{
                    key: '左',
                    value: true
                },{
                    key: '右',
                    value: false
                }]
            }, {
                name: 'newMeterStopCode',
                caption: '新表止码',
                inputType: 'num'
            }, {
                name: 'newSafetyCode',
                caption: '新安全卡编号'
            }, {
                name: 'repairFaultType',
                caption: '维修故障类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("repair_fault_type")
            }, {
                name: 'repairResultType',
                caption: '维修结果',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("repair_result_type")
            }, {
                name: 'empNumber',
                caption: '维修员工号',
                required: true,
                queryField: true,
                maxlength: 50
            }, {
                name: 'empName',
                caption: '维修员姓名',
                disabled: true
            }, {
                name: 'repairStartTime',
                caption: '维修开始时间',
                type: 'date'
            }, {
                name: 'repairEndTime',
                caption: '维修结束时间',
                type: 'date'
            }];
        case 'bindNewCard':
            return [{
                name: 'userId',
                caption: '户号',
                disabled: true
            }, {
                name: 'cardId',
                caption: 'IC卡号',
                disabled: true
            }, {
                name: 'oldCardIdentifier',
                caption: '旧IC卡识别号',
                disabled: true
            }, {
                name: 'cardCost',
                caption: '补卡费用',
                value: app.getDictionaryByCategory("cardCost"),
                disabled: true
            }, {
                name: 'newCardIdentifier',
                caption: '新IC卡识别号',
                required: true
            }];
        case 'fillGas':
            return [{
                name: 'userId',
                caption: '户号',
                disabled: true
            }, {
                name: 'userName',
                caption: '用户名称',
                disabled: true
            }, {
                name: 'userPhone',
                caption: '用户手机',
                disabled: true
            }, {
                name: 'userAddress',
                caption: '用户地址',
                disabled: true
            }, {
                name: 'repairOrderId',
                caption: '维修单编号',
                disabled: true
            }, {
                name: 'gasCount',
                caption: '历史购气总量',
                disabled: true
            }, {
                name: 'stopCodeCount',
                caption: '历史表止码',
                disabled: true
            }, {
                name: 'needFillGas',
                caption: '应补气量',
                disabled: true
            }, {
                name: 'fillGas',
                caption: '实补气量',
                disabled: true
            }, {
                name: 'leftGas',
                caption: '剩余气量',
                disabled: true
            }, {
                name: 'fillGasOrderStatusName',
                caption: '补气单状态',
                disabled: true
            }];
        case 'overuse':
            return [{
                name: 'userId',
                caption: '户号',
                disabled: true
            }, {
                name: 'userName',
                caption: '用户名称',
                disabled: true
            }, {
                name: 'userPhone',
                caption: '用户手机',
                disabled: true
            }, {
                name: 'userAddress',
                caption: '用户地址',
                disabled: true
            }, {
                name: 'repairOrderId',
                caption: '维修单编号',
                disabled: true
            }, {
                name: 'gasCount',
                caption: '历史购气总量',
                disabled: true
            }, {
                name: 'stopCodeCount',
                caption: '历史表止码',
                disabled: true
            }, {
                name: 'needFillGas',
                caption: '应补气量',
                disabled: true
            }, {
                name: 'fillGas',
                caption: '实补气量',
                disabled: true
            }, {
                name: 'leftGas',
                caption: '剩余气量',
                disabled: true
            }, {
                name: 'needFillMoney',
                caption: '应补金额',
                disabled: true
            }, {
                name: 'fillMoney',
                caption: '实补金额',
                required: true
            }, {
                name: 'leftMoney',
                caption: '剩余金额',
                required: true
            }, {
                name: 'fillGasOrderStatusName',
                caption: '补气单状态',
                disabled: true
            },{
                name: 'remarks',
                caption: '备注'
            }];
        case 'alter':
            return [{
                name: 'userChangeName',
                caption: '名称',
                required: true
            }, {
                name: 'userChangePhone',
                caption: '电话',
                required: true

            }, {
                name: 'userChangeIdcard',
                caption: '身份证号码',
                required: true

            }, {
                name: 'userChangeDeed',
                caption: '房产证号码',
                required: true

            }, {
                name: 'tableCode',
                caption: '燃气表当前止码',
                required: true
            }];
        case 'strike':
            return [ {
                name: 'orderId',
                caption: '订单编号',
                disabled: true
            },{
                name: 'userName',
                caption: '用户名称',
                disabled: true
            }, {
                name: 'nucleusLaunchingPerson',
                caption: '发起人姓名',
                disabled: true

            }, {
                name: 'nucleusOpinion',
                caption: '审核意见'
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
        case 'gasPrice' :
            return [{
                name: 'edit',
                caption: '编辑',
                perm: 'sys:gasPrice:update'
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
        case 'entry':
            return [{
                name: 'add',
                caption: '新增',
                perm: 'account:entryMeter:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm: 'account:entryMeter:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm: 'account:entryMeter:delete'
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
                }, {
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
                perm: 'account:createArchive:create'
            }, {
                name: 'edit',
                caption: '编辑',
                perm: 'account:createArchive:update'
            }, {
                name: 'delete',
                caption: '删除',
                perm: 'account:createArchive:delete'
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
                perm: 'account:installation:update'
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
                perm: 'account:createAccount:update'
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
                perm: 'account:lockAccount:lock'
            }, {
                name: 'history',
                caption: '历史锁定记录',
                perm: 'account:lockAccount:lockList'
            }, {
                name: 'userName',
                caption: '用户名称',
                type: 'input'
            }, {
                name: 'iccardId',
                caption: 'IC卡号',
                type: 'input'
            }];
        case 'initCard':
            return [{
                name: 'picture_in_picture_alt',
                caption: '初始化卡',
                perm:'repairorder:iccardinit:visit'
            }];
        case 'prePayment':
            return [{
                name: 'record_voice_over',
                caption: '识别IC卡',
                perm: 'recharge:pre:record'
            }, {
                name: 'edit',
                caption: '预充值',
                perm: 'recharge:pre:update'
            }, {
                name: 'payment',
                caption: '发卡充值',
                perm: 'recharge:pre:new'
            }, {
                name: 'userName',
                caption: '用户名称',
                type: 'input'
            }, {
                name: 'iccardId',
                caption: 'IC卡号',
                type: 'input'
            }, {
                name: 'iccardIdentifier',
                caption: 'IC卡识别号',
                type: 'input'
            }];
        case 'replaceCard' :
            return [{
                name: 'edit',
                caption: '补卡',
                perm: 'recharge:supplement:update'
            }, {
                name: 'history',
                caption: '历史补卡记录',
                perm: 'recharge:supplement:supList'
            }, {
                name: 'userName',
                caption: '用户名称',
                type: 'input'
            }, {
                name: 'iccardId',
                caption: 'IC卡号',
                type: 'input'
            }, {
                name: 'iccardIdentifier',
                caption: 'IC卡识别号',
                type: 'input'
            }];
        case 'input':
            return [{
                name: 'receipt',
                caption: '新增',
                perm:'repairorder:entry:create'
            }, {
                name: 'mode_edit',
                caption: '编辑',
                perm:'repairorder:entry:update'
            }, {
                name: 'picture_in_picture',
                caption: '补卡',
                perm:'repairorder:entry:create'
            },{
                name: 'repairOrderId',
                caption: '维修单编号',
                type: 'input'
            }, {
                name: 'userId',
                caption: '户号',
                type: 'input'
            }, {
                name: 'repairType',
                caption: '维修类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("repair_type")
            }, {
                name: 'empName',
                caption: '维修员姓名',
                type: 'input'
            }];
        case 'fillGas':
            return [{
                name: 'local_gas_station',
                caption: '补气补缴',
                perm: 'repairorder:fillGas:fillGas'
            },{
                name: 'repairOrderId',
                caption: '维修单编号',
                type: 'input'
            }, {
                name: 'userId',
                caption: '户号',
                type: 'input'
            }, {
                name: 'fillGasOrderType',
                caption: '维修类型',
                type: 'listcombobox',
                options: app.getDictionaryByCategory("fill_gas_order_type")
            }];
        case 'alter':
            return [{
                name: 'event',
                caption: '过户',
                perm:'account:alter:visit'
            }, {
                name: 'add_to_queue',
                caption: '账户销户',
                perm:'account:alter:visit'
            }, {
                name: 'mail_outline',
                caption: '过户变更记录',
                perm:'account:alter:visit'
            }, {
                name: 'userName',
                caption: '用户姓名',
                type: 'input'
            }];
        case 'assign' :
            return [{
                name: 'add',
                caption: '发票录入',
                perm:'invoice:assign:add'
            },{
                name: 'assignment',
                caption: '发票分配',
                perm:'invoice:assign:assignment'
            }, {
                name: 'invoiceCode',
                caption: '发票代码',
                type: 'input'
            }, {
                name: 'invoiceNumber',
                caption: '发票号码',
                type: 'input'
            }];
        case 'printCancel' :
            return [{
                name: 'cancel',
                caption: '发票作废',
                perm:'recharge:printCancel:cancel'
            }, {
                name: 'invoiceCode',
                caption: '发票代码',
                type: 'input'
            }, {
                name: 'invoiceNumber',
                caption: '发票号码',
                type: 'input'
            }, {
                name: 'empId',
                caption: '所属员工',
                type: 'listcombobox',
                options: app.getListComboboxOptions('emp/listData.do', 'empName', 'empId')
            }];
        case 'order' :
            return [{
                name: 'record_voice_over',
                caption: '识别IC卡',
                perm:'recharge:order:record'
            },{
                name: 'credit_card',
                caption: '写卡',
                perm:'recharge:order:writeCard'
            },{
                name: 'print',
                caption: '发票打印',
                perm:'recharge:order:print'
            },{
                name: 'crop_original',
                caption: '原票补打',
                perm:'recharge:order:old'
            },{
                name: 'fiber_new',
                caption: '新票补打',
                perm:'recharge:order:new'
            },{
                name: 'cancel',
                caption: '发票作废',
                perm:'recharge:order:cancel'
            }, {
                name: 'userName',
                caption: '用户名称',
                type: 'input'
            }, {
                name: 'iccardId',
                caption: 'IC卡编号',
                type: 'input'
            }, {
                name: 'iccardIdentifier',
                caption: 'IC卡识别号',
                type: 'input'
            }, {
                name: 'invoiceCode',
                caption: '发票代码',
                type: 'input'
            }, {
                name: 'invoiceNumber',
                caption: '发票号码',
                type: 'input'
            }];
        case 'preStrike':
            return[{
                name: 'touch_app',
                caption: '预冲账处理',
                perm:'financial:prestrike:visit'
            },{
                name: 'userName',
                caption: '用户姓名',
                type: 'input'
            },{
                name: 'userTypeName',
                caption: '用户类型',
                type: 'input'
            }];
        case 'strike':
        return[{
            name: 'orderId',
            caption: '订单编号',
            type: 'input'
        },{
            name: 'userName',
            caption: '用户名称',
            type: 'input'
        },{
            name: 'assignment_turned_in',
            caption: '审批',
            perm:'financial:strike:visit'
        }];
        case 'ardQuery':
            return [{
                name: 'record_voice_over',
                caption: '识别IC卡',
                perm: 'querystats:ardQuery:record'
            },{
                name: 'screen_share',
                caption: 'EXCEL导出',
                perm:'querystats:ardQuery:visit'
            }];
        case 'businessDataQuery':
            return[{
                name: 'link',
                caption: 'EXCEL导出',
                perm:'businessDataQuery:data:visit'
            },{
                name: 'startTime',
                caption: '起始时间',
                type: 'date',
                formatter: 'yyyy-mm-dd',
                minView: 3
            },{
                name: 'endTime',
                caption: '截止时间',
                type: 'date',
                formatter: 'yyyy-mm-dd',
                minView: 3
            },{
                name: 'empId',
                caption: '操作人名称',
                type: 'listcombobox',
                options: app.getListComboboxOptions('emp/listData.do', 'empName', 'empId')
            },{
                name: 'accountState',
                caption: '账务状态',
                type: 'listcombobox',
                options: app.getDictionaryByCategory('account_state')

            }];
        case 'accountQuery':
            return [{
                name: 'arrow_downward',
                caption: '导出',
                perm:'querystats:account:export'
            }, {
                name: 'startDate',
                caption: '开户起始日期',
                type: 'date',
                formatter: 'yyyy-mm-dd',
                minView: 2
            },{
                name: 'endDate',
                caption: '开户终止日期',
                type: 'date',
                formatter: 'yyyy-mm-dd',
                minView: 2
            },{
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
            },{
                name: 'userAddress',
                caption: '用户地址',
                type: 'input'
            }];
        case 'exceptionQuery':
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
            },{
                name: 'userAddress',
                caption: '用户地址',
                type: 'input'
            },{
                name: 'notBuyDayCount',
                caption: '未购气天数(天)',
                type: 'input'
            },{
                name: 'monthAveGas',
                caption: '月均购气量(立方)',
                type: 'input'
            },{
                name: 'monthAvePayment',
                caption: '月均购气金额(元)',
                type: 'input'
            }];
        case 'userQuery':
            return [{
                name: 'gradient',
                caption: '变更信息',
                perm:'querystats:accountdetail:visit'
            },{
                name: 'note',
                caption: '充值信息',
                perm:'querystats:accountdetail:visit'
            },{
                name: 'build',
                caption: '维修信息',
                perm:'querystats:accountdetail:visit'
            },{
                name: 'tab',
                caption: '卡信息',
                perm:'querystats:accountdetail:visit'
            },{
                name:'userId',
                caption:'用户编号',
                type: 'input'
            },{
                name:'userName',
                caption:'用户名称',
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
    'emp': 'empId',
    'input': 'id'
};