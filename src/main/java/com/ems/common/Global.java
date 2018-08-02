package com.ems.common;

import com.ems.utils.PropertiesLoader;
import com.ems.utils.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 全局配置类
 *
 * @author hml
 * @version 2016-10-02
 */
public class Global {

    /**
     * 当前对象实例
     */
    private static Global global = new Global();

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = Maps.newHashMap();

    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader loader = new PropertiesLoader("ems.properties");

    /**
     * 显示/隐藏
     */
    public static final String SHOW = "1";

    public static final String HIDE = "0";

    /**
     * 是/否
     */
    public static final String YES = "1";

    public static final String NO = "0";

    /**
     * 对/错
     */
    public static final boolean TRUE = true;

    public static final boolean FALSE = false;


    public static final String SESSION_USER_ID = "userId";

    /**
     * 上传文件基础虚拟路径
     */
    public static final String USERFILES_BASE_URL = "imgdir/";

    public static final String PLAN_USERFILES_BASE_URL = "plan_imgdir/";

    public static final String NOTIFY_USERFILES_BASE_URL = "notify_enclosure/";

    /**
     * 软件更新文件存放目录
     */
    public static final String UPDATE_APP_FILE = "update_pakage/";

    /**
     * 系统默认盐
     */
    public static final String DEFAULT_MD5_SALT = "TDMHEMS";

    /**
     * 是否允许用户多次登录
     */
    public static final boolean USER_MUTIL_LOGIN_ALLOWED = false;

    /**
     * 获取当前对象实例
     */
    public static Global getInstance() {
        return global;
    }

    /**
     * 获取配置
     *
     * @see {fns:getConfig('adminPath')}
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            value = loader.getProperty(key);
            map.put(key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }

    /**
     * 获取管理端根路径
     */
    public static String getAdminPath() {
        return getConfig("admin.path");
    }

    /**
     * 获取前端根路径
     */
    public static String getFrontPath() {
        return getConfig("frontPath");
    }

    /**
     * 获取URL后缀
     */
    public static String getUrlSuffix() {
        return getConfig("urlSuffix");
    }

    /**
     * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
     */
    public static Boolean isDemoMode() {
        String dm = getConfig("demoMode");
        return "true".equals(dm) || "1".equals(dm);
    }

    /**
     * 在修改系统用户和角色时是否同步到Activiti
     */
    public static Boolean isSynActivitiIndetity() {
        String dm = getConfig("activiti.isSynActivitiIndetity");
        return "true".equals(dm) || "1".equals(dm);
    }

    /**
     * 页面获取常量
     *
     * @see {fns:getConst('YES')}
     */
    public static Object getConst(String field) {
        try {
            return Global.class.getField(field).get(null);
        } catch (Exception e) {
            // 异常代表无配置，这里什么也不做
        }
        return null;
    }


    /**
     * 获取工程路径
     *
     * @return
     */
    public static String getProjectPath() {
        // 如果配置了工程路径，则直接返回，否则自动获取。
        String projectPath = Global.getConfig("projectPath");
        if (StringUtils.isNotBlank(projectPath)) {
            return projectPath;
        }
        try {
            File file = new DefaultResourceLoader().getResource("").getFile();
            if (file != null) {
                while (true) {
                    File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
                    if (f == null || f.exists()) {
                        break;
                    }
                    if (file.getParentFile() != null) {
                        file = file.getParentFile();
                    } else {
                        break;
                    }
                }
                projectPath = file.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projectPath;
    }

}
