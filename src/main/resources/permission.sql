-- ----------------------------
-- Table structure for `sys_menu`
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

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
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
INSERT INTO `sys_menu` VALUES ('1011', '用户建档', '/accout/createArchive', '1010', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1012', '表具入库', '/accout/entryMeter', '1010', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1013', '挂表信息', '/accout/installation', '1010', '1', null, null, null, null, '1', '');
INSERT INTO `sys_menu` VALUES ('1014', '账户开户', '/accout/createAccount', '1010', '1', null, null, null, null, '1', '');
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
-- Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `perm_id` int(4) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `perm_name` varchar(50) NOT NULL DEFAULT '' COMMENT '权限名称',
  `perm_caption` varchar(20) DEFAULT NULL COMMENT '权限标题',
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
  CONSTRAINT `sys_permission_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1056 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1000', 'sys:dist:visit', '', '1002', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1001', 'sys:dist:create', '', '1002', '2018-08-01 21:41:53', '1000000001', '2018-08-01 21:42:23', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1002', 'sys:dist:delete', '', '1002', '2018-08-02 03:45:22', '1000000001', '2018-08-02 03:46:14', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1003', 'sys:dist:update', '', '1002', '2018-08-02 09:48:49', '1000000001', '2018-08-02 09:50:06', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1004', 'sys:dist:retrieve', '', '1002', '2018-08-02 15:52:17', '1000000001', '2018-08-02 15:53:58', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1005', 'sys:dist:import', '', '1002', '2018-08-02 21:55:46', '1000000001', '2018-08-02 21:57:51', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1006', 'sys:dist:export', '', '1002', '2018-08-03 03:59:13', '1000000001', '2018-08-03 04:01:43', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1007', 'sys:org:visit', '', '1003', '2018-08-01 16:23:52', '1000000001', '2018-08-01 16:23:59', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1008', 'sys:org:create', '', '1003', '2018-08-01 22:27:19', '1000000001', '2018-08-01 22:27:52', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1009', 'sys:org:delete', '', '1003', '2018-08-02 04:30:48', '1000000001', '2018-08-02 04:31:44', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1010', 'sys:org:update', '', '1003', '2018-08-02 10:34:16', '1000000001', '2018-08-02 10:35:36', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1011', 'sys:org:retrieve', '', '1003', '2018-08-02 16:37:43', '1000000001', '2018-08-02 16:39:27', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1012', 'sys:org:import', '', '1003', '2018-08-02 22:41:12', '1000000001', '2018-08-02 22:43:19', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1013', 'sys:org:export', '', '1003', '2018-08-03 04:44:39', '1000000001', '2018-08-03 04:47:11', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1014', 'sys:emp:visit', '', '1004', '2018-08-01 17:09:18', '1000000001', '2018-08-01 17:09:29', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1015', 'sys:emp:create', '', '1004', '2018-08-01 23:12:45', '1000000001', '2018-08-01 23:13:20', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1016', 'sys:emp:delete', '', '1004', '2018-08-02 05:16:13', '1000000001', '2018-08-02 05:17:12', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1017', 'sys:emp:update', '', '1004', '2018-08-02 11:19:42', '1000000001', '2018-08-02 11:21:05', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1018', 'sys:emp:retrieve', '', '1004', '2018-08-02 17:23:09', '1000000001', '2018-08-02 17:24:57', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1019', 'sys:emp:import', '', '1004', '2018-08-02 23:26:38', '1000000001', '2018-08-02 23:28:49', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1020', 'sys:emp:export', '', '1004', '2018-08-03 05:30:05', '1000000001', '2018-08-03 05:32:40', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1021', 'sys:role:visit', '', '1005', '2018-08-01 17:54:44', '1000000001', '2018-08-01 17:54:58', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1022', 'sys:role:create', '', '1005', '2018-08-01 23:58:12', '1000000001', '2018-08-01 23:58:50', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1023', 'sys:role:delete', '', '1005', '2018-08-02 06:01:39', '1000000001', '2018-08-02 06:02:42', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1024', 'sys:role:update', '', '1005', '2018-08-02 12:05:08', '1000000001', '2018-08-02 12:06:33', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1025', 'sys:role:retrieve', '', '1005', '2018-08-02 18:08:35', '1000000001', '2018-08-02 18:10:25', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1026', 'sys:role:import', '', '1005', '2018-08-03 00:12:04', '1000000001', '2018-08-03 00:14:18', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1027', 'sys:role:export', '', '1005', '2018-08-03 06:15:31', '1000000001', '2018-08-03 06:18:10', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1028', 'sys:perm:visit', '', '1006', '2018-08-01 18:40:10', '1000000001', '2018-08-01 18:40:26', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1029', 'sys:perm:create', '', '1006', '2018-08-02 00:43:38', '1000000001', '2018-08-02 00:44:18', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1030', 'sys:perm:delete', '', '1006', '2018-08-02 06:47:05', '1000000001', '2018-08-02 06:48:11', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1031', 'sys:perm:update', '', '1006', '2018-08-02 12:50:34', '1000000001', '2018-08-02 12:52:03', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1032', 'sys:perm:retrieve', '', '1006', '2018-08-02 18:54:01', '1000000001', '2018-08-02 18:55:54', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1033', 'sys:perm:import', '', '1006', '2018-08-03 00:57:30', '1000000001', '2018-08-03 00:59:46', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1034', 'sys:perm:export', '', '1006', '2018-08-03 07:00:58', '1000000001', '2018-08-03 07:03:38', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1035', 'sys:dic:visit', '', '1007', '2018-08-01 19:25:35', '1000000001', '2018-08-01 19:25:56', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1036', 'sys:dic:create', '', '1007', '2018-08-02 01:29:04', '1000000001', '2018-08-02 01:29:47', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1037', 'sys:dic:delete', '', '1007', '2018-08-02 07:32:31', '1000000001', '2018-08-02 07:33:39', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1038', 'sys:dic:update', '', '1007', '2018-08-02 13:36:00', '1000000001', '2018-08-02 13:37:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1039', 'sys:dic:retrieve', '', '1007', '2018-08-02 19:39:27', '1000000001', '2018-08-02 19:41:24', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1040', 'sys:dic:import', '', '1007', '2018-08-03 01:42:55', '1000000001', '2018-08-03 01:45:16', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1041', 'sys:dic:export', '', '1007', '2018-08-03 07:46:24', '1000000001', '2018-08-03 07:49:07', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1042', 'sys:log:visit', '', '1008', '2018-08-01 20:11:01', '1000000001', '2018-08-01 20:11:25', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1043', 'sys:log:create', '', '1008', '2018-08-02 02:14:30', '1000000001', '2018-08-02 02:15:17', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1044', 'sys:log:delete', '', '1008', '2018-08-02 08:17:57', '1000000001', '2018-08-02 08:19:09', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1045', 'sys:log:update', '', '1008', '2018-08-02 14:21:26', '1000000001', '2018-08-02 14:23:00', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1046', 'sys:log:retrieve', '', '1008', '2018-08-02 20:24:54', '1000000001', '2018-08-02 20:26:52', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1047', 'sys:log:import', '', '1008', '2018-08-03 02:28:21', '1000000001', '2018-08-03 02:30:45', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1048', 'sys:log:export', '', '1008', '2018-08-03 08:31:50', '1000000001', '2018-08-03 08:34:37', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1049', 'sys:notice:visit', '', '1009', '2018-08-01 20:56:27', '1000000001', '2018-08-01 20:56:53', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1050', 'sys:notice:create', '', '1009', '2018-08-02 02:59:56', '1000000001', '2018-08-02 03:00:45', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1051', 'sys:notice:delete', '', '1009', '2018-08-02 09:03:23', '1000000001', '2018-08-02 09:04:38', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1052', 'sys:notice:update', '', '1009', '2018-08-02 15:06:52', '1000000001', '2018-08-02 15:08:30', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1053', 'sys:notice:retrieve', '', '1009', '2018-08-02 21:10:20', '1000000001', '2018-08-02 21:12:22', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1054', 'sys:notice:import', '', '1009', '2018-08-03 03:13:47', '1000000001', '2018-08-03 03:16:13', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1055', 'sys:notice:export', '', '1009', '2018-08-03 09:17:16', '1000000001', '2018-08-03 09:20:05', '1000000001', '1', '');




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


