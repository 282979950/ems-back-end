/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
(function ($) {
    var methods = {
        init: function (options) {
            var o = $.extend({
                items: 1,
                itemsOnPage: 1,
                pages: 0,
                displayedPages: 5,
                edges: 2,
                currentPage: 1,
                hrefTextPrefix: '#page-',
                hrefTextSuffix: '',
                prevText: 'Prev',
                nextText: 'Next',
                ellipseText: '&hellip;',
                cssStyle: 'light-theme',
                selectOnClick: true,
                onPageClick: function (pageNumber, event) {
                    // Callback triggered when a page is clicked
                    // Page number is given as an optional parameter
                },
                onInit: function () {
                    // Callback triggered immediately after initialization
                }
            }, options || {});

            var self = this;

            o.pages = o.pages ? o.pages : Math.ceil(o.items / o.itemsOnPage) ? Math.ceil(o.items / o.itemsOnPage) : 1;
            o.currentPage = o.currentPage - 1;
            o.halfDisplayed = o.displayedPages / 2;

            this.each(function () {
                self.addClass(o.cssStyle + ' simple-pagination').data('pagination', o);
                methods._draw.call(self);
            });

            o.onInit();

            return this;
        },

        selectPage: function (page) {
            methods._selectPage.call(this, page - 1);
            return this;
        },

        prevPage: function () {
            var o = this.data('pagination');
            if (o.currentPage > 0) {
                methods._selectPage.call(this, o.currentPage - 1);
            }
            return this;
        },

        nextPage: function () {
            var o = this.data('pagination');
            if (o.currentPage < o.pages - 1) {
                methods._selectPage.call(this, o.currentPage + 1);
            }
            return this;
        },

        getPagesCount: function () {
            return this.data('pagination').pages;
        },

        getCurrentPage: function () {
            return this.data('pagination').currentPage + 1;
        },

        destroy: function () {
            this.empty();
            return this;
        },

        redraw: function () {
            methods._draw.call(this);
            return this;
        },

        disable: function () {
            var o = this.data('pagination');
            o.disabled = true;
            this.data('pagination', o);
            methods._draw.call(this);
            return this;
        },

        enable: function () {
            var o = this.data('pagination');
            o.disabled = false;
            this.data('pagination', o);
            methods._draw.call(this);
            return this;
        },

        updateItems: function (newItems) {
            var o = this.data('pagination');
            o.items = newItems;
            o.pages = Math.ceil(o.items / o.itemsOnPage) ? Math.ceil(o.items / o.itemsOnPage) : 1;
            this.data('pagination', o);
            methods._draw.call(this);
        },

        _draw: function () {
            var o = this.data('pagination'),
                    interval = methods._getInterval(o),
                    i;

            methods.destroy.call(this);

            var $panel = this.prop("tagName") === "UL" ? this : $('<ul></ul>').appendTo(this);

            // Generate Prev link
            if (o.prevText) {
                methods._appendItem.call(this, o.currentPage - 1, {text: o.prevText, classes: 'prev'});
            }

            // Generate start edges
            if (interval.start > 0 && o.edges > 0) {
                var end = Math.min(o.edges, interval.start);
                for (i = 0; i < end; i++) {
                    methods._appendItem.call(this, i);
                }
                if (o.edges < interval.start && (interval.start - o.edges != 1)) {
                    $panel.append('<li class="disabled"><span class="ellipse">' + o.ellipseText + '</span></li>');
                } else if (interval.start - o.edges == 1) {
                    methods._appendItem.call(this, o.edges);
                }
            }

            // Generate interval links
            for (i = interval.start; i < interval.end; i++) {
                methods._appendItem.call(this, i);
            }

            // Generate end edges
            if (interval.end < o.pages && o.edges > 0) {
                if (o.pages - o.edges > interval.end && (o.pages - o.edges - interval.end != 1)) {
                    $panel.append('<li class="disabled"><span class="ellipse">' + o.ellipseText + '</span></li>');
                } else if (o.pages - o.edges - interval.end == 1) {
                    methods._appendItem.call(this, interval.end++);
                }
                var begin = Math.max(o.pages - o.edges, interval.end);
                for (i = begin; i < o.pages; i++) {
                    methods._appendItem.call(this, i);
                }
            }

            // Generate Next link
            if (o.nextText) {
                methods._appendItem.call(this, o.currentPage + 1, {text: o.nextText, classes: 'next'});
            }
        },

        _getInterval: function (o) {
            return {
                start: Math.ceil(o.currentPage > o.halfDisplayed ? Math.max(Math.min(o.currentPage - o.halfDisplayed, (o.pages - o.displayedPages)), 0) : 0),
                end: Math.ceil(o.currentPage > o.halfDisplayed ? Math.min(o.currentPage + o.halfDisplayed, o.pages) : Math.min(o.displayedPages, o.pages))
            };
        },

        _appendItem: function (pageIndex, opts) {
            var self = this, options, $link, o = self.data('pagination'), $linkWrapper = $('<li></li>'), $ul = self.find('ul');

            pageIndex = pageIndex < 0 ? 0 : (pageIndex < o.pages ? pageIndex : o.pages - 1);

            options = $.extend({
                text: pageIndex + 1,
                classes: ''
            }, opts || {});

            if (pageIndex == o.currentPage || o.disabled) {
                if (o.disabled) {
                    $linkWrapper.addClass('disabled');
                } else {
                    $linkWrapper.addClass('active');
                }
                $link = $('<span class="current">' + (options.text) + '</span>');
            } else {
                $link = $('<a href="' + o.hrefTextPrefix + (pageIndex + 1) + o.hrefTextSuffix + '" class="page-link">' + (options.text) + '</a>');
                $link.click(function (event) {
                    return methods._selectPage.call(self, pageIndex, event);
                });
            }

            if (options.classes) {
                $link.addClass(options.classes);
            }

            $linkWrapper.append($link);

            if ($ul.length) {
                $ul.append($linkWrapper);
            } else {
                self.append($linkWrapper);
            }
        },

        _selectPage: function (pageIndex, event) {
            var o = this.data('pagination');
            o.currentPage = pageIndex;
            if (o.selectOnClick) {
                methods._draw.call(this);
            }
            return o.onPageClick(pageIndex + 1, event);
        }

    };

    $.fn.pagination = function (method) {

        // Method calling logic
        if (methods[method] && method.charAt(0) != '_') {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.pagination');
        }

    };

})(jQuery);
var app = {
    template:
            '<div class="row search-box">\n\
                <div class="col s3">\n\
                    <label>员工名称：<input type="text" class="emp-name" placeholder="员工名称"/></label>\n\
                </div>\n\
                <div class="col s3">\n\
                    <label>\n\
                        部门编号：\n\
                        <select class="emp-deptno">\n\
                            <option value="" disabled selected>请选择</option>\n\
                            <option value="10">10</option>\n\
                            <option value="20">20</option>\n\
                            <option value="30">30</option>\n\
                        </select>\n\
                    </label>\n\
                </div>\n\
                <div class="col s3">\n\
                    <label>\n\
                        部门编号：\n\
                        <select class="emp-job">\n\
                            <option value="" disabled selected>请选择</option>\n\
                            <option value="CLERK">职员</option>\n\
                            <option value="SALESMAN">销售员</option>\n\
                            <option value="ANALYST">分析师</option>\n\
                            <option value="MANAGER">经理</option>\n\
                            <option value="PRESIDENT">董事长</option>\n\
                        </select>\n\
                    </label>\n\
                </div>\n\
                <div class="col s3 align-center">\n\
                    <div class="waves-effect waves-light green lighten-4 btn">\n\
                        <i class="material-icons">search</i>\n\
                    </div>\n\
                </div>\n\
            </div>\n\
            <div class="row data-box">\n\
                <table>\n\
                    <thead class="fields">\n\
                        <tr>\n\
                            <th class="empno">编号</th>\n\
                            <th class="ename">姓名</th>\n\
                            <th class="job">职务</th>\n\
                            <th class="mgr">上级</th>\n\
                            <th class="hiredate">时间</th>\n\
                            <th class="sal">工资</th>\n\
                            <th class="comm">补助</th>\n\
                            <th class="deptno">部门</th>\n\
                        </tr>\n\
                    </thead>\n\
                    <tbody class="records"></tbody>\n\
                </table>\n\
            </div>\n\
            <div class="row controller-box">\n\
                <a class="waves-effect waves-light light-blue lighten-4 btn">首页</a>\n\
                <a class="waves-effect waves-light light-blue lighten-4 btn">上一页</a>\n\
                <a class="waves-effect waves-light light-blue lighten-4 btn">下一页</a>\n\
                <a class="waves-effect waves-light light-blue lighten-4 btn">尾页</a>\n\
            </div>',
    Page: function () {
        var item = {}, prev = new Page(), next = new Page();
        this.item = item;
        this.next = next;
        this.prev = prev;
    },
    getPaneContent: function (name) {
        var paneContent = '';
        switch (name) {
            /**
             * 系统管理：区域管理 机构管理 用户管理 角色管理 权限管理 字典管理 日志管理 公告管理
             */
            case 'regional':
                paneContent = this.template;
                break;
            case 'organization':
                paneContent = this.template;
                break;
            case 'user':
                paneContent = this.template;
                break;
            case 'role':
                paneContent = this.template;
                break;
            case 'permission':
                paneContent = this.template;
                break;
            case 'dictionary':
                break;
            case 'log':
                break;
            case 'announcement':
                break;
                /**
                 * 账户管理：表具入库 用户建档 挂表 开户 账户变更
                 */
            case 'inbound':
                break;
            case 'file':
                break;
            case 'install':
                break;
            case 'account':
                break;
            case 'alter':
                break;
                /**
                 * 充值缴费管理：预付费充值 补卡充值 后付费充值 发票管理
                 */
            case 'prePayment':
                break;
            case 'replaceCard':
                break;
            case 'postPayment':
                break;
            case 'invoice':
                break;
                /**
                 * 维修补气管理: 维修单录入 维修补气 补缴结算 IC卡初始化
                 */
            case 'input':
                break;
            case 'fillGas':
                break;
            case 'balance':
                break;
            case 'initCard':
                break;
                /**
                 * 账务处理：预冲账 冲账
                 */
            case 'preStrike':
                break;
            case 'strike':
                break;
                /**
                 * 表具运行管理：抄表 阀门控制 异常情况
                 */
            case 'record':
                break;
            case 'control':
                break;
            case 'exception':
                break;
                /**
                 * 查询统计：IC卡查询 开户信息查询 用户信息查询 异常用户查询 营业数据查询 营业报表查询
                 */
            case 'cardQuery':
                break;
            case 'accountQuery':
                break;
            case 'userQuery':
                break;
            case 'exceptionQuery':
                break;
            case 'businessDataQuery':
                break;
            case 'businessReportQuery':
                break;
        }
        return paneContent;
    },
    initPane: function (pane) {
        var name = pane.id.split('-')[0];
        
    }
};

