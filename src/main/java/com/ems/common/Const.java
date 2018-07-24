package com.ems.common;

/**
 * @author litairan on 2018/7/2.
 */
public class Const {
    /**
     * 当前登录员工
     */
    public static final String CURRENT_EMPLOYEE = "CURRENT_EMPLOYEE";

    public static final String EMP_LOGIN_NOTEXIST = "用户登录名不存在";

    public static final String EMP_LOGIN_ERRORPASSWORD = "用户密码错误";

    public static final String EMP_LOGIN_SUCCESS = "用户登录成功";

    public static final String EMP_LOGIN_NOTLOGIN = "用户未登录";

    public static final String EMP_LOGOUT_SUCCESS = "用户登出成功";

    public static final String EMP_CREATE_SUCCESS = "用户新建成功";

    public static final String EMP_CREATE_FAIL = "用户新建失败";

    public static final String EMP_CREATE_EXIST = "用户登录名已存在";

    public static final String EMP_DELETE_SUCCESS = "用户删除成功";

    public static final String EMP_DELETE_FAIL = "用户删除失败";

    public static final String EMP_UPDATE_SUCCESS = "用户更新成功";

    public static final String EMP_UPDATE_FAIL = "用户更新失败";

    /**
     * 分页查询默认页
     */
    public static final int DEFAULT_PAGE_NUM = 1;

    /**
     * 分页查询默认页面大小
     */
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 用户权限类型
     */
    public enum EMP_AUTO_TYPE {
        VISIT(0, "访问"),
        CREATE(1, "新增"),
        DELETE(2, "删除"),
        UPDATE(3, "修改"),
        RETRIEVE(4, "查询"),
        IMPORT(5, "导入"),
        EXPORT(6, "导出");

        private int type;
        private String desc;

        EMP_AUTO_TYPE(int type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public int getType() {
            return type;
        }

        public String getDesc() {
            return desc;
        }
    }

    /**
     * 默认分隔符
     */
    public static final String DEFAULT_SEPARATOR = ",";
}
