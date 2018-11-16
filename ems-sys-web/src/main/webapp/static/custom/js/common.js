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
            if(names == 'eval'){
                $.ajax({
                    async: false,
                    type: 'GET',
                    url: 'evalItem/listData.do',
                    contentType: 'application/json;charset=utf-8',
                    beforeSend: function (xhr) {
                        xhr.withCredentials = true;
                    },
                    success: function (response) {
                        if(response.status) {
                            var data = response.data;
                            data.forEach(function (item) {
                                app.tableFields[names].splice(app.tableFields[names].length-2, 0, {
                                    name: '评价项'+item.evalItemId,
                                    caption: item.evalItemContent
                                });
                            });
                        }
                    }
                });
             }
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

app.removeEvent = function () {
    $('.container-main').off();
};

/**
 * 获取字典值
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

app.getTreeComboboxNodes = function (url) {
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
};

app.getListComboboxOptions = function (url, k, v) {
    var result = [];
    $.ajax({
        async: false,
        type: 'POST',
        url: url,
        contentType: 'application/json;charset=utf-8',
        beforeSend: function (xhr) {
            xhr.withCredentials = true;
        },
        success: function (response) {
            var data = response.data;
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
    return result;
};