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
        fields == null ? '' : fields.forEach(function (field) {
            $('<th>' + field.caption + '</th>').addClass(field.name).appendTo(thRow);
        });
        var data = _this.data = params.data;
        var tbody = table.find('tbody');
        data == null ? '' : data.forEach(function (item) {
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
    Table.prototype.getSelectedDatas = function () {
        var _this = this;
        var datas = [];
        var selectedIndexs = _this.getSelectedIndexs();
        selectedIndexs.forEach(function (index) {
            datas.push(_this.data[index]);
        });
        return datas;
    };
    /**
     * 获取选中项的Id
     */
    Table.prototype.getSelectedIds = function (id) {
        console.log(id);
        var _this = this;
        var _id = id;
        var datas = [];
        var selectedIndexs = _this.getSelectedIndexs();
        selectedIndexs.forEach(function (index) {
            console.log(_this.data[index][_id]);
            datas.push(_this.data[index][_id]);
        });
        return datas;
    }
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
        _this.data = data;
        var tbody = _this.$table.find('tbody');
        tbody.empty();
        data == null ? '' : data.forEach(function (item) {
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
            switch (field.type) {
                case 'listcombobox':
                    app.createListCombobox({
                        parent: $field[0],
                        clazz: 'field ',
                        name: field.name,
                        options: field.options
                    });
                    break;
                case 'treecombobox':
                    app.createTreeCombobox({
                        parent: $field[0],
                        clazz: 'field ',
                        name: field.name,
                        options: field.options,
                        data: params.data ? params.data[field.name] : null
                    });
                    break;
                default:
                    $span.addClass('captionClass iconstyle');
                    $('<input type="text">').addClass('field').addClass('align-center inputHeight').addClass(field.name).attr('name', field.name).attr('placeholder', field.caption).appendTo($span);
                    break;
            }
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
        var data = _this.data = params.data ? JSON.parse(JSON.stringify(params.data)) : {};
        var $fields = _this.$fields = $dom.find('.field');
        $fields.each(function (index, field) {
            $(field).val(data[field.name] === true|| data[field.name] === false ? JSON.stringify(data[field.name]) : data[field.name]);
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
        fields == null ? '' :fields.forEach(function (field) {
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
                var $field = $('<div class="mdui-textfield operator field"></div>').appendTo($toolbar);
                $('<span class="mdui-text-color-blue">' + field.caption + ':</span>').appendTo($field);
                $('<input type="text" class="field">').addClass(field.name).attr('name', field.name).appendTo($field);
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
     * 获取Input中的数据
     */
    Toolbar.prototype.getInputsData = function () {
        var inputsData = [];
        var $inputs = this.$dom.find('input');
        $inputs.each(function (index, input) {
            inputsData.push({
                name: $(input).attr('name'),
                value: $(input).val()
            })
        });
        return inputsData;
    };

    /**
     * 新建工具栏
     * @param params
     * @returns {Toolbar}
     */
    app.createToolbar = function (params) {
        return new Toolbar(params);
    };

    /**
     * Tree控件默认配置
     */
    app.TREE_DEFAULT_SETTING = {
        check: {
            enable: true,
            chkboxType: {
                Y: "ps",
                N: "ps"
            }
        },
        data: {
            key: {},
            simpleData: {
                enable: true,
                rootPId: null
            }
        },
        view: {
            showIcon: false,
            showTitle: true
        }
    };

    /**
     * Tree控件
     * @param params
     * @constructor
     */
    function Tree(params) {
        this._init(params);
    }

    /**
     * 初始化
     * @private
     */
    Tree.prototype._init = function (params) {
        var setting = this.setting = JSON.parse(JSON.stringify(app.TREE_DEFAULT_SETTING));
        var idKey = params.idKey;
        // 定义节点选中的联动行为，p影响父节点，s影响子节点
        setting.check.chkboxType.Y = params.Y;
        setting.check.chkboxType.N = params.N;
        setting.check.chkStyle = params.chkStyle ? params.chkStyle : 'checkbox';
        setting.data.simpleData.idKey = this.idKey = params.idKey ? params.idKey : 'id';
        setting.data.simpleData.pIdKey = this.pIdKey = params.pIdKey ? params.pIdKey : 'pId';
        setting.data.key.name = this.nameKey = params.name ? params.name : 'name';
        setting.data.key.title = this.titleKey = params.title ? params.title : '';
        this._initEvents();
        var $parent = $(params.parent);
        var ztree = this.ztree = $.fn.zTree.initz($parent, setting, params.nodes);
        this.$dom = $($parent.children()[0]);
        ztree.expandAll(true);
    };

    /**
     * 初始化事件
     * @returns {Tree}
     */
    Tree.prototype._initEvents = function () {
        var _this = this;
        _this.setting.callback = {};
        _this.setting.callback.onCheck = function () {
            _this.$dom.trigger('checkNode');
        };
    };

    /**
     * 获取选中的节点
     * @returns {Tree}
     */
    Tree.prototype.getSelectedNodes = function () {
        return this.ztree.getSelectedNodes();
    };

    /**
     * 获取选中的节点
     * @returns {Tree}
     */
    Tree.prototype.getCheckedNodes = function () {
        return this.ztree.getCheckedNodes();
    };

    /**
     * 获取根节点
     */
    Tree.prototype.getRoot = function () {
        return this.ztree.getNodes()[0];
    };

    /**
     * 获取所有节点的列表
     */
    Tree.prototype.getAllNodes = function () {
        var result = [];
        this._traverseNode(this.getRoot(), result);
        return result;
    };

    Tree.prototype._traverseNode = function (node, result) {
        var _this = this;
        result.push(node);
        if (node.children) {
            node.children.forEach(function (child) {
                _this._traverseNode(child, result);
            })
        }
    };

    /**
     * 获取节点
     */
    Tree.prototype.getNodeByName = function (name) {
        var _this = this;
        var nodes = _this.getAllNodes();
        var result = null;
        nodes.forEach(function (node) {
            if (node[_this.nameKey] === name) {
                result = node;
            }
        });
        return result;
    };

    /**
     * 依据name勾选node
     */
    Tree.prototype.selectNodeByName = function (name) {
        var node = this.getNodeByName(name);
        if (node) {
            this.ztree.selectNode(node, true);
        }
    };

    /**
     * 依据name勾选node
     */
    Tree.prototype.checkNodeByName = function (name) {
        var node = this.getNodeByName(name);
        if (node) {
            this.ztree.checkNode(node, true, true);
        }
    };

    /**
     * 获取nameKey
     */
    Tree.prototype.getNameKey = function () {
        return this.nameKey;
    };

    /**
     * 创建Tree
     * @param params
     * @returns {Tree}
     */
    app.createTree = function (params) {
        return new Tree(params);
    };

    /**
     * 下拉框控件
     * @constructor
     */
    function ListCombobox(params) {
        this._init(params);
    }

    /**
     * 初始化
     * @param params
     * @private
     */
    ListCombobox.prototype._init = function (params) {
        this._initDom(params);
        this._initData(params);
        this._initEvents(params);
    };

    /**
     * 初始化dom
     */
    ListCombobox.prototype._initDom = function (params) {
        var $parent = this.$parent = $(params.parent).eq(0);
        var $dom = this.$dom = $('<select class="mdui-select"></select>').appendTo($parent);
        if (params.clazz) {
            $dom.addClass(params.clazz)
        }
        if (params.name) {
            $dom.attr('name', params.name)
        }
        var options = this.options = params.options;
        if (options) {
            options.forEach(function (option) {
                $('<option value="' + option.value + '">' + option.key + '</option>').appendTo($dom);
            });
        }
    };

    /**
     * 初始化dom
     */
    ListCombobox.prototype._initData = function (params) {
        this.$dom.val(params.value);
    };

    /**
     * 初始化dom
     */
    ListCombobox.prototype._initEvents = function (params) {

    };

    /**
     * 初始化dom
     */
    ListCombobox.prototype.setValue = function (value) {
        return this.$dom.val(value);
    };

    /**
     *
     */
    ListCombobox.prototype.getValue = function () {
        return this.$dom.val();
    };

    app.createListCombobox = function (params) {
        return new ListCombobox(params);
    };

    /**
     * 下拉框控件
     * @constructor
     */
    function TreeCombobox(params) {
        this._init(params);
    }

    /**
     * 初始化
     * @param params
     * @private
     */
    TreeCombobox.prototype._init = function (params) {
        this._initDom(params);
        this._initData(params);
        this._initEvents(params);
    };

    /**
     * 初始化dom
     */
    TreeCombobox.prototype._initDom = function (params) {
        var parent = params.parent;
        var $parent = this.$parent = $(params.parent).eq(0);
        var $dom = this.$dom = $('<div class="tree-combobox"></div>').appendTo($parent);
        var $input = this.$input = $('<input/>').appendTo($dom);
        if (params.clazz) {
            $input.addClass(params.clazz)
        }
        if (params.name) {
            $input.attr('name', params.name)
        }
        var $span = this.$span = $('<span><i class="mdui-icon material-icons">arrow_drop_down</i></span>').appendTo($dom);
        var options = params.options;
        options.parent = options.parent ? options.parent : '.tree-combobox-panel';
        var $panelDom = this.$panelDom = $('<div class="tree-combobox-panel ztree mdui-shadow-2"></div>').css({
            display: 'none',
            position: 'absolute'
        }).appendTo($('body'));
        this.tree = app.createTree(options);
    };

    /**
     * 初始化data
     */
    TreeCombobox.prototype._initData = function (params) {
        var data = this.data = params.data ? JSON.stringify(params.data) : '';
        var dataList = data.split(',');
        var _this = this;
        dataList.forEach(function (item) {
            _this.tree.checkNodeByName(item);
        })
    };

    /**
     * 初始化events
     */
    TreeCombobox.prototype._initEvents = function () {
        var _this = this;
        this.$span.click(function (event) {
            event.stopPropagation();
            _this.$panelDom.toggle();
            var $dom = _this.$dom;
            var offset = $dom.offset();
            _this.$panelDom.css({
                left: offset.left,
                top: offset.top + $dom.height(),
                'min-width': $dom.width()
            });
        });

        this.$panelDom.on('checkNode', function () {
            var nodes = _this.tree.getCheckedNodes();
            var nameKey = _this.tree.getNameKey();
            var value = [];
            nodes.forEach(function (node) {
                value.push(node[nameKey]);
            });
            var val = _this.data = value.join();
            _this.$input.val(val);
            _this.$input.trigger('change');
        });

        $('.tree-combobox-panel').click(function (event) {
            event.stopPropagation();
        });
    };

    /**
     * 设置value
     */
    TreeCombobox.prototype.setValue = function (value) {
        this.data = value;
        this.$input.val(value);
        var dataList = value.split(',');
        var _this = this;
        dataList.forEach(function (item) {
            _this.tree.checkNodeByName(item);
        })
    };

    /**
     * 获取value
     */
    TreeCombobox.prototype.getValue = function () {
        return this.$input.val();
    };

    app.createTreeCombobox = function (params) {
        return new TreeCombobox(params);
    };

    /**
     * 消息提示
     */
    app.message = function (message) {
        spop({
            template: message,
            position: 'top-center',
            style: 'default',
            autoclose: 2000
        });
    };
    app.successMessage = function (message) {
        spop({
            template: message,
            position: 'top-center',
            style: 'success',
            autoclose: 2000
        });
    };
    app.errorMessage = function (message) {
        spop({
            template: message,
            position: 'top-center',
            style: 'error',
            autoclose: 2000
        });
    };
    app.warningMessage = function (message) {
        spop({
            template: message,
            position: 'top-center',
            style: 'warning',
            autoclose: 2000
        });
    };
})();