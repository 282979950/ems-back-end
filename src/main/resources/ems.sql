/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : ems

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-07-30 16:11:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- 员工表
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `emp_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '员工ID',
  `emp_number` varchar(50) NOT NULL DEFAULT '' COMMENT '员工工号',
  `emp_name` varchar(50) NOT NULL DEFAULT '' COMMENT '员工名称',
  `emp_org_id` int(4) unsigned NOT NULL COMMENT '员工所属机构',
  `emp_district_id` int(4) unsigned DEFAULT NULL COMMENT '员工所属区域',
  `emp_login_name` varchar(20) NOT NULL COMMENT '登录名',
  `emp_password` varchar(32) NOT NULL COMMENT '密码',
  `emp_email` varchar(20) DEFAULT '' COMMENT '员工邮箱',
  `emp_phone` varchar(20) DEFAULT '' COMMENT '员工电话',
  `emp_mobile` varchar(20) DEFAULT '' COMMENT '员工手机号码',
  `emp_address` varchar(50) DEFAULT '' COMMENT '员工住址',
  `emp_type` varchar(10) NOT NULL COMMENT '员工类型',
  `emp_management_dist_id` varchar(200) NOT NULL COMMENT '员工负责片区',
  `emp_login_ip` varchar(20) DEFAULT '' COMMENT '员工登录IP地址',
  `emp_login_date` datetime DEFAULT NULL COMMENT '用户登录日期',
  `emp_login_flag` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '员工登录标记',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT '' COMMENT '注释',
  PRIMARY KEY (`emp_id`),
  UNIQUE KEY `emp_id` (`emp_id`) USING BTREE,
  KEY `emp_org_id_index` (`emp_org_id`) USING BTREE,
  KEY `emp_district_id_index` (`emp_district_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000000001 DEFAULT CHARSET=utf8mb4;

INSERT INTO `employee` VALUES ('1000000000', 'test00', 'test00', '1001', '1011', 'ltr00', 'F68625125825F5EF868864F306759FC6', null, null, null, null, '测试员', '', null, null, '1', null, null, '2018-07-10 12:03:40', null, '1', null);

-- ----------------------------
-- 员工位置信息表
-- ----------------------------
DROP TABLE IF EXISTS `employee_location`;
CREATE TABLE `employee_location` (
  `id` char(32) NOT NULL,
  `emp_id` bigint(20) unsigned NOT NULL COMMENT '机构ID',
  `longtitude` decimal(10,7) unsigned DEFAULT NULL COMMENT '经度',
  `latitude` decimal(10,7) unsigned DEFAULT NULL COMMENT '纬度',
  `create_by` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`id`),
  UNIQUE KEY `emp_id_index` (`emp_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- 员工角色表
-- ----------------------------
DROP TABLE IF EXISTS `employee_role`;
CREATE TABLE `employee_role` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `emp_id` int(10) unsigned NOT NULL COMMENT '员工ID',
  `role_id` int(4) unsigned NOT NULL COMMENT '角色ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned NOT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned NOT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT '' COMMENT '注释',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- 表具表
-- ----------------------------
DROP TABLE IF EXISTS `meter`;
CREATE TABLE `meter` (
  `id` char(32) NOT NULL,
  `meter_id` bigint(20) unsigned NOT NULL COMMENT '表具ID',
  `meter_code` varchar(20) DEFAULT NULL COMMENT '表编号',
  `meter_stopcode` decimal(10,3) unsigned NOT NULL DEFAULT '0.000' COMMENT '表止码',
  `meter_type_id` int(8) unsigned NOT NULL COMMENT '表具型号ID',
  `meter_direction` tinyint(1) DEFAULT NULL COMMENT '表向',
  `meter_prod_date` datetime NOT NULL COMMENT '表具生产日期',
  `meter_entry_date` datetime NOT NULL COMMENT '表具录入时间',
  `meter_install_time` datetime DEFAULT NULL COMMENT '表具安装时间',
  `meter_scrap_time` datetime DEFAULT NULL COMMENT '表具报废时间',
  `meter_validityperiod` int(10) unsigned DEFAULT '0' COMMENT '表具有效期(单位：年)',
  `meter_status` int(10) unsigned DEFAULT '1' COMMENT '表具状态',
  `meter_comm_key` char(32) DEFAULT NULL COMMENT '通讯密钥',
  `meter_comm_num` char(20) DEFAULT NULL COMMENT '通讯号码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`id`),
  UNIQUE KEY `meter_id_index` (`meter_id`) USING BTREE,
  KEY `meter_type_id_index` (`meter_type_id`) USING BTREE,
  CONSTRAINT `fk_meter_type_id` FOREIGN KEY (`meter_type_id`) REFERENCES `meter_type` (`meter_type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- 表具类型表
-- ----------------------------
DROP TABLE IF EXISTS `meter_type`;
CREATE TABLE `meter_type` (
  `id` char(32) NOT NULL,
  `meter_type_id` int(8) unsigned NOT NULL COMMENT '表具型号ID',
  `meter_category` varchar(20) NOT NULL COMMENT '表具类别',
  `meter_type` varchar(20) NOT NULL COMMENT '表具型号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`id`),
  UNIQUE KEY `meter_type_id_index` (`meter_type_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- 系统字典表
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `dict_id` int(4) unsigned NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  `dict_key` varchar(20) NOT NULL,
  `dict_value` varchar(20) NOT NULL,
  `dict_category` varchar(20) NOT NULL COMMENT '类别',
  `dict_sort` int(10) unsigned DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned NOT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned NOT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '注释',
  UNIQUE KEY `dic_id_index` (`dict_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- 系统区域表
-- ----------------------------
DROP TABLE IF EXISTS `sys_district`;
CREATE TABLE `sys_district` (
  `dist_id` int(4) unsigned NOT NULL AUTO_INCREMENT COMMENT '区域ID',
  `dist_name` varchar(20) NOT NULL COMMENT '区域名称',
  `dist_code` varchar(20) DEFAULT NULL COMMENT '区域代码',
  `dist_category` varchar(20) NOT NULL COMMENT '区域类别',
  `dist_address` varchar(50) DEFAULT NULL COMMENT '区域地址',
  `dist_parent_id` int(4) unsigned DEFAULT NULL COMMENT '父级区域',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dist_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1012 DEFAULT CHARSET=utf8mb4;

INSERT INTO `sys_district` VALUES ('1000', '石门市', 'SMS', '市', null, null, null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1001', '楚江镇', 'CJZ', '镇', null, '1000', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1002', '蒙泉镇', 'CQZ', '镇', null, '1000', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1003', '顺岭岗村', 'SLGC', '村', null, '1002', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1004', '顺岭岗村001', '', '户', null, '1003', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1005', '顺岭岗村002', '', '户', null, '1003', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1006', '顺岭岗村003', '', '户', null, '1003', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1007', '清水潭村', 'QSTC', '村', null, '1002', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1008', '夹山镇', 'JSZ', '镇', null, '1000', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1009', '二都乡', 'EDX', '乡', null, '1000', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1010', '新铺乡', 'XPX', '乡', null, '1000', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1011', '白云乡', 'BYX', '乡', null, '1000', null, null, null, null, '1', '');

-- ----------------------------
-- 系统日志表
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `log_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `log_title` varchar(50) NOT NULL COMMENT '日志标题',
  `log_type` varchar(10) NOT NULL COMMENT '日志类型',
  `log_operator` varchar(20) NOT NULL COMMENT '操作人员',
  `log_operator_ip` varchar(50) NOT NULL COMMENT '操作人员IP地址',
  `log_exception_info` varchar(255) NOT NULL COMMENT '异常信息',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned NOT NULL COMMENT '创建者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '注释',
  UNIQUE KEY `log_id_index` (`log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- 系统菜单表
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(4) unsigned NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(20) NOT NULL COMMENT '菜单名称',
  `menu_href` varchar(50) NOT NULL COMMENT '菜单路径',
  `menu_parent_id` int(4) unsigned DEFAULT NULL COMMENT '菜单等级',
  `menu_showable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否显示',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`menu_id`),
  UNIQUE KEY `menu_id_index` (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1043 DEFAULT CHARSET=utf8mb4;

INSERT INTO `sys_menu` VALUES ('1000', '根目录', '/', null, '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1001', '系统管理', '/sys/', '1000', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1002', '区域管理', '/sys/dist', '1001', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1003', '机构管理', '/sys/org', '1001', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1004', '用户管理', '/sys/emp', '1001', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1005', '角色管理', '/sys/role', '1001', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1006', '权限管理', '/sys/auth', '1001', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1007', '字典管理', '/sys/dic', '1001', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1008', '日志管理', '/sys/log', '1001', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1009', '公告管理', '/sys/notice', '1001', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1010', '账户开户管理', '/accout/', '1000', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1011', '用户建档', '/accout/createarchives', '1010', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1012', '表具入库', '/accout/warehouse', '1010', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1013', '挂表信息', '/accout/installation', '1010', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1014', '账户开户', '/accout/create', '1010', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1015', '账户管理', '/accout/mgt', '1010', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1016', '充值缴费管理', '/recharge/', '1000', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1017', '预付费充值', '/recharge/pre', '1016', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1018', '补卡充值', '/recharge/supplement', '1016', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1019', '后付费缴费', '/recharge/suff', '1016', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1020', '发票管理', '/recharge/invoice', '1016', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1021', '报修单管理', '/declareorder/', '1000', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1022', '业务功能描述', '/declareorder/business', '1021', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1023', '数据项说明', '/declareorder/data', '1021', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1024', '维修补气管理', '/repairorder/', '1000', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1025', '维修单录入提交', '/repairorder/entry', '1024', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1026', '维修补气', '/repairorder/supplement', '1024', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1027', '超用补缴结算', '/repairorder/overuse', '1024', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1028', 'IC卡初始化', '/repairorder/iccardinit', '1024', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1029', '账务处理', '/financial/', '1000', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1030', '预冲账处理', '/financial/prestrike ', '1029', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1031', '冲账处理', '/financial/strike', '1029', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1032', '表具运行管理', '/meter/', '1000', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1033', '抄表管理', '/meter/readmeter', '1032', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1034', '阀门控制', '/meter/valve', '1032', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1035', '异常情况管理', '/meter/exception', '1032', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1036', '查询统计', '/querystats/', '1000', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1037', 'IC卡查询', '/querystats/iccard', '1036', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1038', '开户信息查询', '/querystats/account', '1036', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1039', '燃气用户信息查询', '/querystats/accountdetail', '1036', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1040', '异常用户查询', '/querystats/abnormaluser', '1036', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1041', '营业数据查询', '/querystats/data', '1036', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1042', '营业报表统计', '/querystats/report', '1036', '1', null, null, null, null, '1', '');

-- ----------------------------
-- 系统机构表
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `org_id` bigint(20) unsigned NOT NULL COMMENT '机构ID',
  `org_name` varchar(50) NOT NULL COMMENT '机构名称',
  `org_code` varchar(50) DEFAULT NULL COMMENT '机构代码',
  `org_category` varchar(20) DEFAULT NULL COMMENT '机构类别',
  `org_parent_id` bigint(20) unsigned DEFAULT NULL COMMENT '父级机构',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '注释',
  UNIQUE KEY `org_id_index` (`org_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `sys_organization` VALUES ('100000000000', '武汉表具管理有限公司', null, '公司', null, null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100001000000', '总经办事处1', null, '总经办', '100000000000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100001001000', '形象部', null, '部门', '100001000000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100001002000', '艺术部', null, '部门', '100001000000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100001003000', '教育部', null, '部门', '100001000000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100001004000', '宣传部', null, '部门', '100001000000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100001005000', '业务部', null, '部门', '100001000000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100001006000', '研发部', null, '部门', '100001000000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100001006001', '实施小组', null, '小组', '100001006000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100001006002', '其他小组', null, '小组', '100001006000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100001006003', '测试小组', null, '小组', '100001006000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100001006004', '电子政务小组', null, '小组', '100001006000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100001006005', '档案小组', null, '小组', '100001006000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100001007000', '其他部门', null, '部门', '100001000000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100002000000', '总经办事处2', null, '总经办', '100000000000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100002001000', '业务部test', null, '部门', '100002000000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100002002000', '宣传部test', null, '部门', '100002000000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100002003000', '教育部test', null, '部门', '100002000000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100002004000', '艺术部test', null, '部门', '100002000000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('100002005000', '形象部test', null, '部门', '100002000000', null, null, null, null, '1', null);

-- ----------------------------
-- 系统权限表
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `perm_id` int(4) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `perm_name` varchar(20) NOT NULL DEFAULT '' COMMENT '权限名称',
  `perm_type` int(4) unsigned NOT NULL COMMENT '权限类型',
  `menu_id` int(4) unsigned NOT NULL COMMENT '菜单ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`perm_id`),
  UNIQUE KEY `auth_id_index` (`perm_id`) USING BTREE,
  KEY `fk_menu_id` (`menu_id`),
  CONSTRAINT `fk_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`menu_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- 系统角色表
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(4) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `role_dists` varchar(200) NOT NULL COMMENT '角色区域范围',
  `role_orgs` varchar(200) NOT NULL COMMENT '角色组织范围',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_id_index` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- 系统角色权限表
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_perm`;
CREATE TABLE `sys_role_perm` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(4) unsigned NOT NULL COMMENT '角色ID',
  `perm_id` int(4) unsigned NOT NULL COMMENT '权限ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

INSERT INTO `sys_role_perm` VALUES ('1', '1000', '1000', '2018-07-18 16:26:56', '1000000000', '2018-07-18 16:26:56', '1000000000', '1', null);

-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` char(32) NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `user_name` varchar(20) DEFAULT NULL COMMENT '机构名称',
  `user_phone` varchar(20) DEFAULT NULL COMMENT '用户电话',
  `user_idcard` varchar(20) DEFAULT NULL COMMENT '用户身份证号码',
  `user_deed` varchar(20) DEFAULT NULL COMMENT '用户房产证号码',
  `user_dist_id` bigint(20) unsigned DEFAULT NULL COMMENT '用户所在区域ID',
  `user_address` varchar(100) NOT NULL COMMENT '用户住址',
  `user_type` tinyint(2) unsigned DEFAULT NULL COMMENT '用户类型',
  `user_gas_type` tinyint(2) unsigned DEFAULT NULL COMMENT '用气类型',
  `user_meter_type` tinyint(2) unsigned DEFAULT NULL COMMENT '用户表类型',
  `iccard_id` bigint(20) DEFAULT NULL COMMENT 'IC卡编号',
  `iccard_identifier` varchar(20) DEFAULT NULL COMMENT 'IC卡识别号',
  `iccard_password` char(6) DEFAULT NULL COMMENT 'IC卡密码',
  `user_locked` tinyint(1) unsigned DEFAULT '0' COMMENT '是否被锁定',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`) USING BTREE,
  KEY `user_dist_id_index` (`user_dist_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- 用户表具表
-- ----------------------------
DROP TABLE IF EXISTS `user_meters`;
CREATE TABLE `user_meters` (
  `id` char(32) NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `meter_id` bigint(20) unsigned NOT NULL COMMENT '表具ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`) USING BTREE,
  KEY `meter_id_index` (`meter_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- 用户订单表
-- ----------------------------
DROP TABLE IF EXISTS `user_orders`;
CREATE TABLE `user_orders` (
  `id` char(32) NOT NULL COMMENT '机构ID',
  `order_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户ID',
  `employee_id` bigint(20) unsigned NOT NULL COMMENT '员工ID',
  `order_payment` decimal(10,2) DEFAULT NULL COMMENT '充值金额',
  `order_status` int(10) unsigned DEFAULT NULL COMMENT '订单状态',
  `order_create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `order_close_time` datetime DEFAULT NULL COMMENT '订单关闭时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id_index` (`order_id`) USING BTREE,
  KEY `employee_id_index` (`employee_id`),
  KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
