/* global app, M */
var app = {
    DEFAULT_TEMPLATE: '<div class="mdui-table-fluid mdui-theme-accent-blue"></div>',
    DEFAULT_PAGE_TEMPLATE: '<div class="mdui-table-fluid mdui-theme-accent-blue"></div><div class="custom-pagination"></div>',
    dataCache: {},
    DEFAULT_PAGE_SIZE: 10,
    DEFAULT_PAGE_NUM: 1
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
    if (app.hasPaginator) {
        $.ajax({
            async: false,
            type: 'GET',
            url: context.url + "?pageNum=" + app.pageNum + "&pageSize=" + app.pageSize,
            contentType: 'application/json;charset=utf-8',
            beforeSend: function (xhr) {
                xhr.withCredentials = true;
            },
            success: function (response) {
                var data = response.data;
                _render(data);
            }
        });
    } else {
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
                _render(data);
            }
        });
    }
    function _render(data) {
        if (app.table) {
            if (app.hasPaginator) {
                app.table.refresh(data.list);
            } else {
                app.table.refresh(data);
            }
        } else {
            var names = app.currentPageName;
            app.table = context.table = app.createTable({
                parent: '.mdui-table-fluid',
                fields: app.tableFields[names],
                data: app.hasPaginator ? data.list : data
            });
            if (app.hasPaginator) {
                app.pagination = app.createPagination({
                    parent: '.custom-pagination',
                    totalPage: data.pages,
                    totalSize: data.total,
                    callBack: function (pageNum, pageSize) {
                        var inputsData = app.toolbar.getInputsData();
                        app.pageNum = pageNum;
                        app.pageSize = pageSize;
                        if (inputsData.length) {
                            app.pagination.$parent.trigger('search');
                        } else {
                            app.render({
                                url: app.currentPageName + '/listData.do'
                            });
                        }
                    }
                });
            }
        }
    }
};

app.initPane = function (context) {
    var self = this;
    app.toolbar = app.createToolbar({
        parent: '.container-main',
        fields: app.getToolbarFields(app.currentPageName)
    });
    self.render(context);
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