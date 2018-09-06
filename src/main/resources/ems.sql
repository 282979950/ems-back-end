/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : ems

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-08-22 08:38:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `employee`
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
  KEY `emp_district_id_index` (`emp_district_id`) USING BTREE,
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`emp_org_id`) REFERENCES `sys_organization` (`org_id`),
  CONSTRAINT `employee_ibfk_2` FOREIGN KEY (`emp_district_id`) REFERENCES `sys_district` (`dist_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000002 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1000000000', 'test', 'test', '1001', '1011', 'test', '4acfaf58decb315fea0ae13c58e5bdce', null, null, null, null, '测试员', '', null, null, '1', null, null, '2018-07-10 12:03:40', null, '1', null);
INSERT INTO `employee` VALUES ('1000000001', 'admin', 'admin', '1001', '1001', 'admin', '4acfaf58decb315fea0ae13c58e5bdce', '', '', '', '', '管理员', ' ', '', null, '1', null, null, null, null, '1', '');

-- ----------------------------
-- Table structure for `employee_location`
-- ----------------------------
DROP TABLE IF EXISTS `employee_location`;
CREATE TABLE `employee_location` (
  `id` int(10) NOT NULL,
  `emp_id` int(10) unsigned NOT NULL COMMENT '机构ID',
  `longtitude` decimal(10,7) unsigned DEFAULT NULL COMMENT '经度',
  `latitude` decimal(10,7) unsigned DEFAULT NULL COMMENT '纬度',
  `create_by` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`id`),
  UNIQUE KEY `emp_id_index` (`emp_id`) USING BTREE,
  CONSTRAINT `employee_location_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of employee_location
-- ----------------------------

-- ----------------------------
-- Table structure for `employee_role`
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
  PRIMARY KEY (`id`),
  KEY `emp_id` (`emp_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `employee_role_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`emp_id`),
  CONSTRAINT `employee_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of employee_role
-- ----------------------------
INSERT INTO `employee_role` VALUES ('1', '1000000001', '1001', '2018-08-01 15:16:15', '1000000001', '2018-08-01 15:16:21', '1000000001', '1', '');

-- ----------------------------
-- Table structure for `meter`
-- ----------------------------
DROP TABLE IF EXISTS `meter`;
CREATE TABLE `meter` (
  `meter_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '表具ID',
  `meter_code` varchar(20) NOT NULL COMMENT '表编号',
  `meter_stop_code` decimal(10,3) unsigned NOT NULL DEFAULT '0.000' COMMENT '表止码',
  `meter_type_id` int(8) unsigned NOT NULL COMMENT '表具型号ID',
  `meter_direction` tinyint(1) NOT NULL COMMENT '表向',
  `meter_prod_date` datetime NOT NULL COMMENT '表具生产日期',
  `meter_entry_date` datetime NOT NULL COMMENT '表具入库时间',
  `meter_install_time` datetime DEFAULT NULL COMMENT '表具安装时间',
  `meter_scrap_time` datetime DEFAULT NULL COMMENT '表具报废时间',
  `meter_validityperiod` int(10) unsigned DEFAULT '0' COMMENT '表具有效期(单位：年)',
  `meter_status` int(10) unsigned DEFAULT '1' COMMENT '表具状态',
  `meter_comm_key` char(32) DEFAULT NULL COMMENT '通讯密钥',
  `meter_comm_num` char(20) DEFAULT NULL COMMENT '通讯号码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`meter_id`),
  UNIQUE KEY `meter_id_index` (`meter_id`) USING BTREE,
  KEY `meter_type_id_index` (`meter_type_id`) USING BTREE,
  CONSTRAINT `meter_ibfk_1` FOREIGN KEY (`meter_type_id`) REFERENCES `meter_type` (`meter_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000002 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of meter
-- ----------------------------
INSERT INTO `meter` VALUES ('1000000000', '001090500001', '0.000', '1', '1', '2018-08-08 17:23:48', '2018-08-08 17:23:51', null, null, '0', '1', null, null, null, null, null, null, '1', null);
INSERT INTO `meter` VALUES ('1000000001', '101090500001', '0.000', '2', '1', '2018-08-08 21:57:11', '2018-08-08 21:57:11', null, null, '0', '1', null, null, '2018-08-08 21:57:10', '1000000001', '2018-08-08 21:57:10', '1000000001', '1', null);

-- ----------------------------
-- Table structure for `meter_type`
-- ----------------------------
DROP TABLE IF EXISTS `meter_type`;
CREATE TABLE `meter_type` (
  `meter_type_id` int(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '表具型号ID',
  `meter_category` varchar(20) NOT NULL COMMENT '表具类别',
  `meter_type` varchar(20) NOT NULL COMMENT '表具型号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`meter_type_id`),
  UNIQUE KEY `meter_type_id_index` (`meter_type_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of meter_type
-- ----------------------------
INSERT INTO `meter_type` VALUES ('1', 'IC卡表', '10-3B(LMN)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('2', 'IC卡表', '10-4A(G)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('3', 'IC卡表', '10-4A(LMN)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('4', 'IC卡表', '16-3B(LMN)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('5', 'IC卡表', '16-4A(ACD)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('6', 'IC卡表', '16-4A(LMN)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('7', 'IC卡表', '2.5-3B(C)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('8', 'IC卡表', '2.5-3B(C)（T1）', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('9', 'IC卡表', '2.5-3B(CQ)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('10', 'IC卡表', '2.5-3B(QK)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('11', 'IC卡表', '25-3B(LMN)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('12', 'IC卡表', '25-4A(ACD)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('13', 'IC卡表', '25-4A(LMN)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('14', 'IC卡表', '40-3B(LMN)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('15', 'IC卡表', '40-4A(G)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('16', 'IC卡表', '40-4A(LMN)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('17', 'IC卡表', '4-3B(C)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('18', 'IC卡表', '4-3B(C) (II)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('19', 'IC卡表', '4-3B(C) (T1)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('20', 'IC卡表', '4-3B(C) (T1Q)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('21', 'IC卡表', '4-3B(C)（Y6）', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('22', 'IC卡表', '4-3B(CQ)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('23', 'IC卡表', '4-3B(CQ)（T1）', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('24', 'IC卡表', '4-3B(QK)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('25', 'IC卡表', '6-3B(C)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('26', 'IC卡表', '6-3B(C)（T1）', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('27', 'IC卡表', '6-3B(CQ)', null, null, null, null, '1', '');
INSERT INTO `meter_type` VALUES ('28', 'IC卡表', '6-3B(QK)', null, null, null, null, '1', '');

-- ----------------------------
-- Table structure for `sys_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `dict_id` int(4) unsigned NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  `dict_category` varchar(20) NOT NULL COMMENT '类别',
  `dict_key` varchar(20) NOT NULL,
  `dict_value` varchar(20) NOT NULL,
  `dict_sort` int(10) unsigned DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '注释',
  UNIQUE KEY `dic_id_index` (`dict_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1007 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------
INSERT INTO `sys_dictionary` VALUES ('1000', 'gasType', '1', '民用', null, null, null, null, null, '1', null);
INSERT INTO `sys_dictionary` VALUES ('1001', 'gasType', '2', '商用', null, null, null, null, null, '1', null);
INSERT INTO `sys_dictionary` VALUES ('1002', 'userType', '1', '新装IC卡表用户', null, null, null, null, null, '1', null);
INSERT INTO `sys_dictionary` VALUES ('1003', 'userType', '2', '机械表换IC卡表用户', null, null, null, null, null, '1', null);
INSERT INTO `sys_dictionary` VALUES ('1004', 'userType', '3', '零星用户', null, null, null, null, null, '1', null);
INSERT INTO `sys_dictionary` VALUES ('1005', 'userType', '4', '新装短信表用户', null, null, null, null, null, '1', null);
INSERT INTO `sys_dictionary` VALUES ('1006', 'userType', '5', '新装物联表用户', null, null, null, null, null, '1', null);

-- ----------------------------
-- Table structure for `sys_district`
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

-- ----------------------------
-- Records of sys_district
-- ----------------------------
INSERT INTO `sys_district` VALUES ('1000', '石门市', 'SMS', '市', null, null, null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1001', '楚江镇', 'CJZ', '镇', null, '1000', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1002', '蒙泉镇', 'CQZ', '镇', null, '1000', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1003', '顺岭岗村', 'SLGC', '村', null, '1002', null, null, null, '1000000001', '1', '');
INSERT INTO `sys_district` VALUES ('1004', '顺岭岗村101', 'AAA', '户', null, '1003', null, null, null, '1000000001', '1', '');
INSERT INTO `sys_district` VALUES ('1005', '顺岭岗村002', '', '户', null, '1003', null, null, null, '1000000001', '1', '');
INSERT INTO `sys_district` VALUES ('1006', '顺岭岗村003', '', '户', null, '1003', null, null, null, '1000000001', '1', '');
INSERT INTO `sys_district` VALUES ('1007', '清水潭村', 'QSTC', '村', null, '1002', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1008', '夹山镇', 'JSZ', '镇', null, '1000', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1009', '二都乡', 'EDX', '乡', null, '1000', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1010', '新铺乡', 'XPX', '乡', null, '1000', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1011', '白云乡', 'BYX', '乡', null, '1000', null, null, null, null, '1', '');

-- ----------------------------
-- Table structure for `sys_log`
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
-- Records of sys_log
-- ----------------------------


-- ----------------------------
-- Table structure for `sys_organization`
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `org_id` int(4) unsigned NOT NULL AUTO_INCREMENT COMMENT '机构ID',
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
  PRIMARY KEY (`org_id`),
  UNIQUE KEY `org_id_index` (`org_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1019 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_organization
-- ----------------------------
INSERT INTO `sys_organization` VALUES ('1000', '武汉表具管理有限公司', null, '公司', null, null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1001', '总经办事处1', null, '总经办', '1000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1002', '形象部', null, '部门', '1001', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1003', '艺术部', null, '部门', '1001', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1004', '教育部', null, '部门', '1001', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1005', '宣传部', null, '部门', '1001', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1006', '业务部', null, '部门', '1001', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1007', '研发部', null, '部门', '1001', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1008', '实施小组', null, '小组', '1007', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1009', '其他小组', null, '小组', '1007', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1010', '测试小组', null, '小组', '1007', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1011', '电子政务小组', null, '小组', '1007', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1012', '档案小组', null, '小组', '1007', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1013', '总经办事处2', null, '总经办', '1000', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1014', '业务部test', null, '部门', '1013', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1015', '宣传部test', null, '部门', '1013', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1016', '教育部test', null, '部门', '1013', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1017', '艺术部test', null, '部门', '1013', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1018', '形象部test', null, '部门', '1013', null, null, null, null, '1', null);


-- ----------------------------
-- Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
`perm_id`  int(4) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '权限ID' ,
`perm_name`  varchar(50) NOT NULL DEFAULT '' COMMENT '权限名称' ,
`perm_caption`  varchar(20) NOT NULL DEFAULT '' COMMENT '权限标题' ,
`perm_href`  varchar(50) DEFAULT NULL COMMENT '权限路径' ,
`perm_parent_id`  int(4) unsigned DEFAULT NULL COMMENT '权限父级ID' ,
`is_button`  tinyint(1)  UNSIGNED NOT NULL DEFAULT 1 COMMENT  '是否按钮',
`create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`create_by`  int(10) UNSIGNED NULL DEFAULT NULL COMMENT '创建者' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`update_by`  int(10) UNSIGNED NULL DEFAULT NULL COMMENT '更新者' ,
`usable`  tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否可用' ,
`remarks`  varchar(255) DEFAULT NULL COMMENT '备注' ,
PRIMARY KEY (`perm_id`),
UNIQUE INDEX `auth_id_index` (`perm_id`) USING BTREE
)
ENGINE=InnoDB AUTO_INCREMENT=1091 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1000', 'visit', '根目录', '/', null, '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

INSERT INTO `sys_permission` VALUES ('1001', 'sys:visit', '系统管理', '/sys/', '1000', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1002', 'sys:dist:visit', '区域管理', '/sys/dist', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1003', 'sys:org:visit', '机构管理', '/sys/org', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1004', 'sys:emp:visit', '用户管理', '/sys/emp', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1005', 'sys:role:visit', '角色管理', '/sys/role', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1006', 'sys:perm:visit', '权限管理', '/sys/perm', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1007', 'sys:dic:visit', '字典管理', '/sys/dic', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1008', 'sys:log:visit', '日志管理', '/sys/log', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1009', 'sys:notice:visit', '公告管理', '/sys/notice', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

INSERT INTO `sys_permission` VALUES ('1010', 'account:visit', '账户开户管理', '/account/', '1000', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1011', 'account:createArchive:visit', '用户建档', '/account/createArchive', '1010', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1012', 'account:entryMeter:visit', '表具入库', '/account/entryMeter', '1010', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1013', 'account:installation:visit', '挂表信息', '/account/installation', '1010', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1014', 'account:createAccount:visit', '账户开户', '/account/createAccount', '1010', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1015', 'account:lockAccount:visit', '账户锁定', '/account/lockAccount', '1010', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1091', 'account:alter:visit', '账户变更', '/account/alter', '1010', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

INSERT INTO `sys_permission` VALUES ('1016', 'recharge:visit', '充值缴费管理', '/recharge/', '1000', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1017', 'recharge:pre:visit', '预付费充值', '/recharge/pre', '1016', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1018', 'recharge:supplement:visit', '补卡充值', '/recharge/supplement', '1016', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1019', 'recharge:suff:visit', '后付费缴费', '/recharge/suff', '1016', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1020', 'recharge:invoice:visit', '发票管理', '/recharge/invoice', '1016', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

INSERT INTO `sys_permission` VALUES ('1021', 'declareorder:visit', '报修单管理', '/declareorder/', '1000', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1022', 'declareorder:business:visit', '业务功能描述', '/declareorder/business', '1021', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1023', 'declareorder:data:visit', '数据项说明', '/declareorder/data', '1021', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

INSERT INTO `sys_permission` VALUES ('1024', 'repairorder:visit', '维修补气管理', '/repairorder/', '1000', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1025', 'repairorder:entry:visit', '维修单录入提交', '/repairorder/entry', '1024', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1026', 'repairorder:supplement:visit', '维修补气', '/repairorder/supplement', '1024', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1027', 'repairorder:overuse:visit', '超用补缴结算', '/repairorder/overuse', '1024', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1028', 'repairorder:iccardinit:visit', 'IC卡初始化', '/repairorder/iccardinit', '1024', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

INSERT INTO `sys_permission` VALUES ('1029', 'financial:visit', '账务处理', '/financial/', '1000', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1030', 'financial:prestrike:visit', '预冲账处理', '/financial/prestrike', '1029', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1031', 'financial:strike:visit', '冲账处理', '/financial/strike', '1029', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

INSERT INTO `sys_permission` VALUES ('1032', 'meter:visit', '表具运行管理', '/meter/', '1000', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1033', 'meter:readmeter:visit', '抄表管理', '/meter/readmeter', '1032', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1034', 'meter:valve:visit', '阀门控制', '/meter/valve', '1032', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1035', 'meter:exception:visit', '异常情况管理', '/meter/exception', '1032', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

INSERT INTO `sys_permission` VALUES ('1036', 'querystats:visit', '查询统计', '/querystats/', '1000', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1037', 'querystats:iccard:visit', 'IC卡查询', '/querystats/iccard', '1036', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1038', 'querystats:account:visit', '开户信息查询', '/querystats/account', '1036', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1039', 'querystats:accountdetail:visit', '燃气用户信息查询', '/querystats/accountdetail', '1036', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1040', 'querystats:abnormaluser:visit', '异常用户查询', '/querystats/abnormaluser', '1036', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1041', 'querystats:data:visit', '营业数据查询', '/querystats/data', '1036', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1042', 'querystats:report:visit', '营业报表统计', '/querystats/report', '1036', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');


-- ----------------------------
-- Records of 区域管理
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1043', 'sys:dist:create', '增加', null, '1002', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1044', 'sys:dist:update', '修改', null, '1002', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1045', 'sys:dist:delete', '删除', null, '1002', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1046', 'sys:dist:retrieve', '查询', null, '1002', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1047', 'sys:dist:import', '导入', null, '1002', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1048', 'sys:dist:export', '导出', null, '1002', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

-- ----------------------------
-- Records of 机构管理
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1049', 'sys:org:create', '增加', null, '1003', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1050', 'sys:org:update', '修改', null, '1003', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1051', 'sys:org:delete', '删除', null, '1003', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1052', 'sys:org:retrieve', '查询', null, '1003', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1053', 'sys:org:import', '导入', null, '1003', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1054', 'sys:org:export', '导出', null, '1003', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

-- ----------------------------
-- Records of 用户管理
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1055', 'sys:emp:create', '增加', null, '1004', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1056', 'sys:emp:update', '修改', null, '1004', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1057', 'sys:emp:delete', '删除', null, '1004', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1058', 'sys:emp:retrieve', '查询', null, '1004', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1059', 'sys:emp:import', '导入', null, '1004', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1060', 'sys:emp:export', '导出', null, '1004', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

-- ----------------------------
-- Records of 角色管理
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1061', 'sys:role:create', '增加', null, '1005', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1062', 'sys:role:update', '修改', null, '1005', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1063', 'sys:role:delete', '删除', null, '1005', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1064', 'sys:role:retrieve', '查询', null, '1005', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1065', 'sys:role:import', '导入', null, '1005', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1066', 'sys:role:export', '导出', null, '1005', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

-- ----------------------------
-- Records of 权限管理
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1067', 'sys:perm:create', '增加', null, '1006', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1068', 'sys:perm:update', '修改', null, '1006', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1069', 'sys:perm:delete', '删除', null, '1006', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1070', 'sys:perm:retrieve', '查询', null, '1006', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1071', 'sys:perm:import', '导入', null, '1006', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1072', 'sys:perm:export', '导出', null, '1006', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

-- ----------------------------
-- Records of 字典管理
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1073', 'sys:dic:create', '增加', null, '1007', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1074', 'sys:dic:update', '修改', null, '1007', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1075', 'sys:dic:delete', '删除', null, '1007', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1076', 'sys:dic:retrieve', '查询', null, '1007', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1077', 'sys:dic:import', '导入', null, '1007', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1078', 'sys:dic:export', '导出', null, '1007', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

-- ----------------------------
-- Records of 日志管理
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1079', 'sys:log:create', '增加', null, '1008', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1080', 'sys:log:update', '修改', null, '1008', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1081', 'sys:log:delete', '删除', null, '1008', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1082', 'sys:log:retrieve', '查询', null, '1008', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1083', 'sys:log:import', '导入', null, '1008', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1084', 'sys:log:export', '导出', null, '1008', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

-- ----------------------------
-- Records of 通知管理
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1085', 'sys:notice:create', '增加', null, '1009', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1086', 'sys:notice:update', '修改', null, '1009', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1087', 'sys:notice:delete', '删除', null, '1009', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1088', 'sys:notice:retrieve', '查询', null, '1009', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1089', 'sys:notice:import', '导入', null, '1009', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1090', 'sys:notice:export', '导出', null, '1009', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

-- ----------------------------
-- Records of 账户开户
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1092', 'account:createAccount:update', '开户', null, '1014', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1093', 'account:createAccount:retrieve', '查询', null, '1014', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1094', 'account:createAccount:import', '导入', null, '1014', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1095', 'account:createAccount:export', '导出', null, '1014', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

-- ----------------------------
-- Records of 账户锁定
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1096', 'account:lockAccount:retrieve', '查询', null, '1015', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1097', 'account:lockAccount:lock', '锁定解锁', null, '1015', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1098', 'account:lockAccount:lockList', '锁定记录', null, '1015', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1099', 'account:lockAccount:import', '导入', null, '1015', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1100', 'account:lockAccount:export', '导出', null, '1015', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

-- ----------------------------
-- Records of 表具入库
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1118', 'account:entryMeter:create', '增加', null, '1012', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1101', 'account:entryMeter:update', '修改', null, '1012', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1102', 'account:entryMeter:delete', '删除', null, '1012', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1103', 'account:entryMeter:retrieve', '查询', null, '1012', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1104', 'account:entryMeter:import', '导入', null, '1012', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1105', 'account:entryMeter:export', '导出', null, '1012', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

-- ----------------------------
-- Records of 用户建档
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1106', 'account:createArchive:create', '增加', null, '1011', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1107', 'account:createArchive:update', '修改', null, '1011', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1108', 'account:createArchive:delete', '删除', null, '1011', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1109', 'account:createArchive:retrieve', '查询', null, '1011', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1110', 'account:createArchive:import', '导入', null, '1011', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1111', 'account:createArchive:export', '导出', null, '1011', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

-- ----------------------------
-- Records of 挂表
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1112', 'account:installation:create', '增加', null, '1013', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1113', 'account:installation:update', '修改', null, '1013', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1114', 'account:installation:delete', '删除', null, '1013', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1115', 'account:installation:retrieve', '查询', null, '1013', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1116', 'account:installation:import', '导入', null, '1013', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1117', 'account:installation:export', '导出', null, '1013', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

-- ----------------------------
-- Table structure for `sys_role`
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
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1001', 'admin', '', '', '2018-08-01 15:33:09', '1000000001', '2018-08-01 15:33:13', '1000000001', '1', null);

-- ----------------------------
-- Table structure for `sys_role_perm`
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
  PRIMARY KEY (`id`),
  KEY `fk_role_perm` (`role_id`) USING BTREE,
  KEY `fk_perm_id` (`perm_id`),
  CONSTRAINT `fk_perm_id` FOREIGN KEY (`perm_id`) REFERENCES `sys_permission` (`perm_id`),
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role_perm
-- ----------------------------
INSERT INTO `sys_role_perm` VALUES ('1', '1001', '1000', '2018-07-18 16:26:55', '1000000000', '2018-07-18 16:26:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('2', '1001', '1001', '2018-08-01 16:22:28', '1000000000', '2018-08-01 16:22:35', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('3', '1001', '1002', '2018-08-15 16:18:02', '1000000000', '2018-08-15 16:18:16', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('4', '1001', '1003', '2018-08-29 16:13:35', '1000000000', '2018-08-29 16:13:56', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('5', '1001', '1004', '2018-09-12 16:09:07', '1000000000', '2018-09-12 16:09:35', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('6', '1001', '1005', '2018-09-26 16:04:41', '1000000000', '2018-09-26 16:05:15', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('7', '1001', '1006', '2018-10-10 16:00:14', '1000000000', '2018-10-10 16:00:56', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('8', '1001', '1007', '2018-10-24 15:55:47', '1000000000', '2018-10-24 15:56:35', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('9', '1001', '1008', '2018-11-07 15:51:20', '1000000000', '2018-11-07 15:52:15', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('10', '1001', '1009', '2018-11-21 15:46:53', '1000000000', '2018-11-21 15:47:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('11', '1001', '1010', '2018-12-05 15:42:26', '1000000000', '2018-12-05 15:43:36', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('12', '1001', '1011', '2018-12-19 15:37:59', '1000000000', '2018-12-19 15:39:15', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('13', '1001', '1012', '2019-01-02 15:33:32', '1000000000', '2019-01-02 15:34:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('14', '1001', '1013', '2019-01-16 15:29:05', '1000000000', '2019-01-16 15:30:36', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('15', '1001', '1014', '2019-01-30 15:24:38', '1000000000', '2019-01-30 15:26:16', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('16', '1001', '1015', '2019-02-13 15:20:11', '1000000000', '2019-02-13 15:21:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('17', '1001', '1016', '2019-02-27 15:15:44', '1000000000', '2019-02-27 15:17:35', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('18', '1001', '1017', '2019-03-13 15:11:17', '1000000000', '2019-03-13 15:13:16', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('19', '1001', '1018', '2019-03-27 15:06:50', '1000000000', '2019-03-27 15:08:56', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('20', '1001', '1019', '2019-04-10 15:02:23', '1000000000', '2019-04-10 15:04:35', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('21', '1001', '1020', '2019-04-24 14:57:56', '1000000000', '2019-04-24 15:00:15', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('22', '1001', '1021', '2019-05-08 14:53:29', '1000000000', '2019-05-08 14:55:56', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('23', '1001', '1022', '2019-05-22 14:49:02', '1000000000', '2019-05-22 14:51:36', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('24', '1001', '1023', '2019-06-05 14:44:35', '1000000000', '2019-06-05 14:47:15', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('25', '1001', '1024', '2019-06-19 14:40:08', '1000000000', '2019-06-19 14:42:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('26', '1001', '1025', '2019-07-03 14:35:41', '1000000000', '2019-07-03 14:38:36', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('27', '1001', '1026', '2019-07-17 14:31:14', '1000000000', '2019-07-17 14:34:16', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('28', '1001', '1027', '2019-07-31 14:26:47', '1000000000', '2019-07-31 14:29:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('29', '1001', '1028', '2019-08-14 14:22:20', '1000000000', '2019-08-14 14:25:35', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('30', '1001', '1029', '2019-08-28 14:17:53', '1000000000', '2019-08-28 14:21:16', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('31', '1001', '1030', '2019-09-11 14:13:26', '1000000000', '2019-09-11 14:16:56', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('32', '1001', '1031', '2019-09-25 14:08:59', '1000000000', '2019-09-25 14:12:35', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('33', '1001', '1032', '2019-10-09 14:04:32', '1000000000', '2019-10-09 14:08:15', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('34', '1001', '1033', '2019-10-23 14:00:05', '1000000000', '2019-10-23 14:03:56', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('35', '1001', '1034', '2019-11-06 13:55:38', '1000000000', '2019-11-06 13:59:35', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('36', '1001', '1035', '2019-11-20 13:51:11', '1000000000', '2019-11-20 13:55:15', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('37', '1001', '1036', '2019-12-04 13:46:44', '1000000000', '2019-12-04 13:50:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('38', '1001', '1037', '2019-12-18 13:42:17', '1000000000', '2019-12-18 13:46:36', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('39', '1001', '1038', '2020-01-01 13:37:50', '1000000000', '2020-01-01 13:42:15', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('40', '1001', '1039', '2020-01-15 13:33:23', '1000000000', '2020-01-15 13:37:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('41', '1001', '1040', '2020-01-29 13:28:56', '1000000000', '2020-01-29 13:33:36', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('42', '1001', '1041', '2020-02-12 13:24:29', '1000000000', '2020-02-12 13:29:16', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('43', '1001', '1042', '2020-02-26 13:20:02', '1000000000', '2020-02-26 13:24:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('44', '1001', '1043', '2020-03-11 13:15:35', '1000000000', '2020-03-11 13:20:35', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('45', '1001', '1044', '2020-03-25 13:11:08', '1000000000', '2020-03-25 13:16:16', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('46', '1001', '1045', '2020-04-08 13:06:41', '1000000000', '2020-04-08 13:11:56', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('47', '1001', '1046', '2020-04-22 13:02:14', '1000000000', '2020-04-22 13:07:35', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('48', '1001', '1047', '2020-05-06 12:57:47', '1000000000', '2020-05-06 13:03:15', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('49', '1001', '1048', '2020-05-20 12:53:20', '1000000000', '2020-05-20 12:58:56', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('50', '1001', '1049', '2020-06-03 12:48:53', '1000000000', '2020-06-03 12:54:36', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('51', '1001', '1050', '2020-06-17 12:44:26', '1000000000', '2020-06-17 12:50:15', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('52', '1001', '1051', '2020-07-01 12:39:59', '1000000000', '2020-07-01 12:45:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('53', '1001', '1052', '2020-07-15 12:35:32', '1000000000', '2020-07-15 12:41:36', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('54', '1001', '1053', '2020-07-29 12:31:05', '1000000000', '2020-07-29 12:37:16', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('55', '1001', '1054', '2020-08-12 12:26:38', '1000000000', '2020-08-12 12:32:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('56', '1001', '1055', '2020-08-26 12:22:11', '1000000000', '2020-08-26 12:28:35', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('57', '1001', '1056', '2018-12-05 15:42:26', '1000000000', '2018-12-05 15:43:36', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('58', '1001', '1057', '2018-12-19 15:37:59', '1000000000', '2018-12-19 15:39:15', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('59', '1001', '1058', '2019-01-02 15:33:32', '1000000000', '2019-01-02 15:34:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('60', '1001', '1059', '2019-01-16 15:29:05', '1000000000', '2019-01-16 15:30:36', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('61', '1001', '1060', '2019-01-30 15:24:38', '1000000000', '2019-01-30 15:26:16', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('62', '1001', '1061', '2019-02-13 15:20:11', '1000000000', '2019-02-13 15:21:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('63', '1001', '1062', '2019-02-27 15:15:44', '1000000000', '2019-02-27 15:17:35', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('64', '1001', '1063', '2019-03-13 15:11:17', '1000000000', '2019-03-13 15:13:16', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('65', '1001', '1064', '2019-03-27 15:06:50', '1000000000', '2019-03-27 15:08:56', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('66', '1001', '1065', '2019-04-10 15:02:23', '1000000000', '2019-04-10 15:04:35', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('67', '1001', '1066', '2019-04-24 14:57:56', '1000000000', '2019-04-24 15:00:15', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('68', '1001', '1067', '2019-05-08 14:53:29', '1000000000', '2019-05-08 14:55:56', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('69', '1001', '1068', '2019-05-22 14:49:02', '1000000000', '2019-05-22 14:51:36', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('70', '1001', '1069', '2019-06-05 14:44:35', '1000000000', '2019-06-05 14:47:15', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('71', '1001', '1070', '2019-06-19 14:40:08', '1000000000', '2019-06-19 14:42:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('72', '1001', '1071', '2019-07-03 14:35:41', '1000000000', '2019-07-03 14:38:36', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('73', '1001', '1072', '2019-07-17 14:31:14', '1000000000', '2019-07-17 14:34:16', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('74', '1001', '1073', '2019-07-31 14:26:47', '1000000000', '2019-07-31 14:29:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('75', '1001', '1074', '2019-08-14 14:22:20', '1000000000', '2019-08-14 14:25:35', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('76', '1001', '1075', '2019-08-28 14:17:53', '1000000000', '2019-08-28 14:21:16', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('77', '1001', '1076', '2019-09-11 14:13:26', '1000000000', '2019-09-11 14:16:56', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('78', '1001', '1077', '2019-09-25 14:08:59', '1000000000', '2019-09-25 14:12:35', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('79', '1001', '1078', '2019-10-09 14:04:32', '1000000000', '2019-10-09 14:08:15', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('80', '1001', '1079', '2019-10-23 14:00:05', '1000000000', '2019-10-23 14:03:56', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('81', '1001', '1080', '2019-11-06 13:55:38', '1000000000', '2019-11-06 13:59:35', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('82', '1001', '1081', '2019-11-20 13:51:11', '1000000000', '2019-11-20 13:55:15', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('83', '1001', '1082', '2019-12-04 13:46:44', '1000000000', '2019-12-04 13:50:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('84', '1001', '1083', '2019-12-18 13:42:17', '1000000000', '2019-12-18 13:46:36', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('85', '1001', '1084', '2020-01-01 13:37:50', '1000000000', '2020-01-01 13:42:15', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('86', '1001', '1085', '2020-01-15 13:33:23', '1000000000', '2020-01-15 13:37:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('87', '1001', '1086', '2020-01-29 13:28:56', '1000000000', '2020-01-29 13:33:36', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('88', '1001', '1087', '2020-02-12 13:24:29', '1000000000', '2020-02-12 13:29:16', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('89', '1001', '1088', '2020-02-26 13:20:02', '1000000000', '2020-02-26 13:24:55', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('90', '1001', '1089', '2020-03-11 13:15:35', '1000000000', '2020-03-11 13:20:35', '1000000000', '1', '');
INSERT INTO `sys_role_perm` VALUES ('91', '1001', '1090', '2020-03-25 13:11:08', '1000000000', '2020-03-25 13:16:16', '1000000000', '1', '');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户名称',
  `user_phone` varchar(20) DEFAULT NULL COMMENT '用户电话',
  `user_idcard` varchar(20) DEFAULT NULL COMMENT '用户身份证号码',
  `user_deed` varchar(20) DEFAULT NULL COMMENT '用户房产证号码',
  `user_dist_id` int(4) unsigned NOT NULL COMMENT '用户所在区域ID',
  `user_address` varchar(100) NOT NULL COMMENT '用户住址',
  `user_type` int(4) unsigned NOT NULL COMMENT '用户类型',
  `user_gas_type` int(4) unsigned NOT NULL COMMENT '用气类型',
  `iccard_id` int(8) unsigned DEFAULT NULL COMMENT 'IC卡编号',
  `iccard_identifier` varchar(12) DEFAULT NULL COMMENT 'IC卡识别号',
  `iccard_password` varchar(6) DEFAULT NULL COMMENT 'IC卡密码',
  `user_locked` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否被锁定',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id` (`user_id`) USING BTREE,
  KEY `user_dist_id_index` (`user_dist_id`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`user_dist_id`) REFERENCES `sys_district` (`dist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for `user_meters`
-- ----------------------------
DROP TABLE IF EXISTS `user_meters`;
CREATE TABLE `user_meters` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(8) unsigned NOT NULL COMMENT '用户ID',
  `meter_id` int(10) unsigned NOT NULL COMMENT '表具ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`) USING BTREE,
  KEY `meter_id_index` (`meter_id`) USING BTREE,
  CONSTRAINT `user_meters_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `user_meters_ibfk_2` FOREIGN KEY (`meter_id`) REFERENCES `meter` (`meter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_meters
-- ----------------------------

-- ----------------------------
-- Table structure for `user_orders`
-- ----------------------------
DROP TABLE IF EXISTS `user_orders`;
CREATE TABLE `user_orders` (
  `order_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` int(8) unsigned NOT NULL COMMENT '用户ID',
  `employee_id` int(10) unsigned NOT NULL COMMENT '员工ID',
  `order_payment` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '充值金额',
  `order_gas` decimal(10,2) unsigned DEFAULT NULL COMMENT '充值气量',
  `order_status` int(4) unsigned DEFAULT NULL COMMENT '订单状态',
  `order_create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `order_prestrike_time` datetime DEFAULT NULL COMMENT '订单预冲账时间',
  `order_strike_time` datetime DEFAULT NULL COMMENT '订单冲账时间',
  `order_close_time` datetime DEFAULT NULL COMMENT '订单关闭时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `order_id_index` (`order_id`) USING BTREE,
  KEY `employee_id_index` (`employee_id`),
  KEY `user_id_index` (`user_id`) USING BTREE,
  CONSTRAINT `user_orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `user_orders_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_orders
-- ----------------------------


-- ----------------------------
-- Table structure for `user_lock`
-- ----------------------------
DROP TABLE IF EXISTS `user_lock`;
CREATE TABLE `user_lock` (
`user_lock_id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '锁定Id' ,
`user_id`  int(8) unsigned NULL DEFAULT NULL COMMENT '锁定用户Id' ,
`is_lock`  tinyint(1) NULL DEFAULT NULL ,
`lock_reason`  varchar(255)  DEFAULT NULL COMMENT '锁定原因' ,
`create_time`  datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间' ,
`create_by`  int(10) NULL DEFAULT NULL COMMENT '创建者' ,
`update_time`  datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
`update_by`  int(10) NULL DEFAULT NULL COMMENT '更新者' ,
`usable`  tinyint(1) NULL DEFAULT NULL COMMENT '是否可用' ,
`remarks`  varchar(255)  DEFAULT NULL COMMENT '注释',
PRIMARY KEY (`user_lock_id`),
KEY `user_lock_id_index` (`user_lock_id`) USING BTREE,
CONSTRAINT `user_lock_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_lock
-- ----------------------------


