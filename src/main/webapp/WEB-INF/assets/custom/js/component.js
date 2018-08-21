(function () {

    /**
     * 生成 checkbox 的 HTML 结构
     * @param tag
     * @returns {string}
     */
    var checkboxHTML = function (tag) {
        return '<' + tag + ' class="mdui-table-cell-checkbox">' +
            '<label class="mdui-checkbox">' +
            '<input type="checkbox"/>' +
            '<i class="mdui-checkbox-icon"></i>' +
            '</label>' +
            '</' + tag + '>';
    };


    /**
     * Table 表格
     * @param params
     * @constructor
     */
    function Table(params) {
        var _this = this;
        /**
         * table基础dom结构
         */
        var baseDom = '<table class="mdui-table mdui-table-selectable mdui-table-hoverable"><thead class="fields"></thead><tbody class="records"></tbody></table>';
        var parent = _this.$parent = $(params.parent).eq(0);
        var table = _this.$table = $(baseDom).appendTo(parent);

        var fields = _this.fields = params.fields;
        var thead = table.find('thead');
        var thRow = $('<tr></tr>').addClass('fields').appendTo(thead);
        fields.forEach(function (field) {
            $('<th>' + field.caption + '</th>').addClass(field.name).appendTo(thRow);
        });
        var data = _this.data = params.data;
        var tbody = table.find('tbody');
        data.forEach(function (item) {
            var tdRow = $('<tr></tr>').appendTo(tbody);
            fields.forEach(function (field) {
                $('<td>' + (item[field.name] ? item[field.name] : '') + '</td>').appendTo(tdRow);
            });
        });
        _this.init();
    }

    /**
     * 初始化
     */
    Table.prototype.init = function () {
        var _this = this;

        _this.$thRow = _this.$table.find('thead tr');
        _this.$tdRows = _this.$table.find('tbody tr');
        _this.$tdCheckboxs = $();
        _this.selectable = _this.$table.hasClass('mdui-table-selectable');
        _this.selectedRow = 0;

        _this._updateThCheckbox();
        _this._updateTdCheckbox();
        _this._updateNumericCol();
    };

    /**
     * 更新表格行的 checkbox
     */
    Table.prototype._updateTdCheckbox = function () {
        var _this = this;

        _this.$tdRows.each(function () {
            var $tdRow = $(this);

            // 移除旧的 checkbox
            $tdRow.find('.mdui-table-cell-checkbox').remove();

            if (!_this.selectable) {
                return;
            }

            // 创建 DOM
            var $checkbox = $(checkboxHTML('td'))
                .prependTo($tdRow)
                .find('input[type="checkbox"]');

            // 默认选中的行
            if ($tdRow.hasClass('mdui-table-row-selected')) {
                $checkbox[0].checked = true;
                _this.selectedRow++;
            }

            // 所有行都选中后，选中表头；否则，不选中表头
            _this.$thCheckbox[0].checked = _this.selectedRow === _this.$tdRows.length;

            // 绑定事件
            $checkbox.on('change', function () {
                if ($checkbox[0].checked) {
                    $tdRow.addClass('mdui-table-row-selected');
                    _this.selectedRow++;
                } else {
                    $tdRow.removeClass('mdui-table-row-selected');
                    _this.selectedRow--;
                }

                // 所有行都选中后，选中表头；否则，不选中表头
                _this.$thCheckbox[0].checked = _this.selectedRow === _this.$tdRows.length;
            });

            _this.$tdCheckboxs = _this.$tdCheckboxs.add($checkbox);
        });
    };

    /**
     * 更新表头的 checkbox
     */
    Table.prototype._updateThCheckbox = function () {
        var _this = this;

        // 移除旧的 checkbox
        _this.$thRow.find('.mdui-table-cell-checkbox').remove();

        if (!_this.selectable) {
            return;
        }

        _this.$thCheckbox = $(checkboxHTML('th'))
            .prependTo(_this.$thRow)
            .find('input[type="checkbox"]')
            .on('change', function () {

                var isCheckedAll = _this.$thCheckbox[0].checked;
                _this.selectedRow = isCheckedAll ? _this.$tdRows.length : 0;

                _this.$tdCheckboxs.each(function (i, checkbox) {
                    checkbox.checked = isCheckedAll;
                });

                _this.$tdRows.each(function (i, row) {
                    $(row)[isCheckedAll ? 'addClass' : 'removeClass']('mdui-table-row-selected');
                });

            });
    };

    /**
     * 更新数值列
     */
    Table.prototype._updateNumericCol = function () {
        var _this = this;
        var $th;
        var $tdRow;

        _this.$thRow.find('th').each(function (i, th) {
            $th = $(th);

            _this.$tdRows.each(function () {
                $tdRow = $(this);
                var method = $th.hasClass('mdui-table-col-numeric') ? 'addClass' : 'removeClass';
                $tdRow.find('td').eq(i)[method]('mdui-table-col-numeric');
            });
        });
    };

    /**
     * 获取选中项的Index
     */
    Table.prototype.getSelectedIndexs = function () {
        var _this = this;
        var indexs = [];
        _this.$tdRows.each(function (i, tr) {
            var $tr = $(tr);
            if ($tr.hasClass('mdui-table-row-selected')) {
                indexs.push(i);
            }
        });
        return indexs;
    };

    /**
     * 获取选中项的数据
     */
    Table.prototype.getSelectedData = function () {
        var _this = this;
        var data = [];
        var selectedIndexs = _this.getSelectedIndexs();
        selectedIndexs.forEach(function (index) {
            data.push(_this.data[index]);
        });
        return data;
    };

    /**
     * 获取table的fields
     */
    Table.prototype.getFields = function () {
        return this.fields;
    };

    /**
     * 刷新Table
     */
    Table.prototype.refresh = function (data) {
        var _this = this;
        var tbody = _this.$table.find('tbody');
        tbody.empty();
        data.forEach(function (item) {
            var tdRow = $('<tr></tr>').appendTo(tbody);
            _this.fields.forEach(function (field) {
                $('<td>' + (item[field.name] ? item[field.name] : '') + '</td>').appendTo(tdRow);
            });
        });
        _this.init();
    };

    /**
     * 新建表格
     * @param params
     */
    app.createTable = function (params) {
        return new Table(params);
    };

    /**
     * 表单控件
     * @param params
     * @constructor
     */
    function Form(params) {
        this._init(params);
    }

    /**
     * 初始化表单
     * @param params
     * @private
     */
    Form.prototype._init = function (params) {
        this._initDom(params);
        this._initData(params);
        this._initEvents(params);
    };
    /**
     * 初始化dom
     * @private
     */
    Form.prototype._initDom = function (params) {
        var _this = this;
        // 表单控件基础dom
        var baseDom = '<form class="form"><div class="form-title col s12"></div><div class="form-body col s12 form-fields"></div></form>';
        var parent = _this.$parent = $(params.parent).eq(0);
        var table = _this.$dom = $(baseDom).appendTo(parent);
        var title = table.find('.form-title');
        var body = table.find('.form-body');

        var fields = _this.fields = params.fields;
        fields.forEach(function (field) {
            var $field = $('<div></div>').addClass('form-field').appendTo(body);
            var $span = $('<span></span>').text(field.caption + ':').appendTo($field);
            $span.addClass('captionClass iconstyle');
            $('<input type="text">').addClass('field').addClass('align-center inputHeight').addClass(field.name).attr('name', field.name).attr('placeholder', field.caption).appendTo($span);
        });
    };

    /**
     * 初始化数据
     * @param params
     * @private
     */
    Form.prototype._initData = function (params) {
        var _this = this;
        var $dom = _this.$dom;
        var data = _this.data = params.data ? params.data : {};
        var $fields = _this.$fields = $dom.find('.field');
        $fields.each(function (index, field) {
            $(field).val(data[field.name]);
        });
    };

    /**
     * 初始化事件
     * @private
     */
    Form.prototype._initEvents = function () {
        var _this = this;
        _this.$fields.each(function (index, field) {
            var $field = $(field);
            $field.on('change', function () {
                _this.data[field.name] = $field.val();
            })
        });
    };

    /**
     * 获取表单数据
     * @returns {*}
     */
    Form.prototype.getData = function () {
        return this.data;
    };

    /**
     * 获取表单数据
     * @returns {*}
     */
    Form.prototype.getDom = function () {
        return this.$dom;
    };

    /**
     * 创建Form表单
     * @param params
     * @returns {Form}
     */
    app.createForm = function (params) {
        return new Form(params);
    };

    /**
     * 工具栏控件
     * @param params
     * @constructor
     */
    function Toolbar(params) {
        this._init(params);
    }

    /**
     * 初始化
     * @param params
     * @private
     */
    Toolbar.prototype._init = function (params) {
        this._initDom(params);
        this._initEvents(params);
    };

    /**
     * 初始化工具栏dom
     * @private
     */
    Toolbar.prototype._initDom = function (params) {
        var _this = this;
        // 表单控件基础dom
        var baseDom = '<div class="mdui-toolbar"></div>';
        var parent = _this.$parent = $(params.parent).eq(0);
        var $toolbar = _this.$dom = $(baseDom).prependTo(parent);

        var fields = _this.fields = params.fields;
        fields.forEach(function (field) {
            _this._initItemDom($toolbar, field);
        });
    };

    /**
     * 初始化各个子项dom
     * @private
     */
    Toolbar.prototype._initItemDom = function ($toolbar, field) {
        switch (field.type) {
            case 'input':
                // todo
                break;
            default :
                var $field = $('<div class="mdui-btn mdui-btn-icon mdui-ripple mdui-ripple-white operator field"></div>').attr('name', field.name).attr('mdui-tooltip', '{content:\'' + field.caption + '\'}').appendTo($toolbar);
                $('<i class="mdui-icon material-icons mdui-text-color-blue">' + field.name + '</i>').appendTo($field);
                break;
        }
    };

    /**
     * 初始化事件
     * @private
     */
    Toolbar.prototype._initEvents = function () {
        var _this = this;
        var fields = _this.fields = $('.field');
        fields.each(function (index, field) {
            var $field = $(field);
            $field.on('click', function () {
                var name = $field.attr('name');
                switch (name) {
                    case 'add':
                        $field.trigger('add');
                        break;
                    case 'edit':
                        $field.trigger('edit');
                        break;
                    case 'delete':
                        $field.trigger('delete');
                        break;
                    case 'search':
                        $field.trigger('search');
                        break;
                    default:
                        break;
                }
            })
        });
    };

    /**
     * 新建工具栏
     * @param params
     * @returns {Toolbar}
     */
    app.createToolbar = function (params) {
        return new Toolbar(params);
    };
})();