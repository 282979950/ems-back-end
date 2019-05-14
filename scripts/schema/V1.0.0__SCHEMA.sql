-- 员工
CREATE TABLE `employee` (
  `emp_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '员工ID',
  `emp_number` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '员工工号',
  `emp_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '员工名称',
  `emp_org_id` int(4) unsigned NOT NULL COMMENT '员工所属机构',
  `emp_district_id` int(4) unsigned DEFAULT NULL COMMENT '员工所属区域',
  `emp_login_name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录名',
  `emp_password` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `emp_email` varchar(20) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '员工邮箱',
  `emp_phone` varchar(20) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '员工电话',
  `emp_mobile` varchar(20) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '员工手机号码',
  `emp_address` varchar(50) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '员工住址',
  `emp_type` int(4) NOT NULL COMMENT '员工类型',
  `emp_management_dist_id` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工负责片区',
  `emp_login_ip` varchar(20) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '员工登录IP地址',
  `emp_login_date` datetime DEFAULT NULL COMMENT '用户登录日期',
  `emp_login_flag` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '员工登录标记',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '注释',
  PRIMARY KEY (`emp_id`),
  UNIQUE KEY `emp_id` (`emp_id`) USING BTREE,
  KEY `emp_org_id_index` (`emp_org_id`) USING BTREE,
  KEY `emp_district_id_index` (`emp_district_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000000000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 员工位置
CREATE TABLE `employee_location` (
  `id` int(10) NOT NULL,
  `emp_id` int(10) unsigned NOT NULL COMMENT '机构ID',
  `longtitude` decimal(10,7) unsigned DEFAULT NULL COMMENT '经度',
  `latitude` decimal(10,7) unsigned DEFAULT NULL COMMENT '纬度',
  `create_by` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`id`),
  UNIQUE KEY `emp_id_index` (`emp_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 员工角色
CREATE TABLE `employee_role` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `emp_id` int(10) unsigned NOT NULL COMMENT '员工ID',
  `role_id` int(4) unsigned NOT NULL COMMENT '角色ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '注释',
  PRIMARY KEY (`id`),
  KEY `emp_id` (`emp_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 补气单
CREATE TABLE `fill_gas_order` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fill_gas_order_type` int(4) unsigned NOT NULL DEFAULT '0',
  `user_id` int(10) NOT NULL,
  `repair_order_id` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `gas_count` decimal(10,2) unsigned DEFAULT NULL,
  `stop_code_count` decimal(10,2) unsigned DEFAULT NULL,
  `need_fill_gas` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  `fill_gas` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  `left_gas` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  `need_fill_money` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  `fill_money` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  `left_money` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  `fill_gas_order_status` int(4) unsigned NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL COMMENT '',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '',
  `update_time` datetime DEFAULT NULL COMMENT '',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '',
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 气价
CREATE TABLE `gas_price` (
  `gas_price_id` int(2) NOT NULL AUTO_INCREMENT,
  `user_type` int(4) DEFAULT NULL,
  `user_gas_type` int(4) DEFAULT NULL,
  `gas_range_one` decimal(10,2) DEFAULT NULL,
  `gas_price_one` decimal(10,2) DEFAULT NULL,
  `gas_range_two` decimal(10,2) DEFAULT NULL,
  `gas_price_two` decimal(10,2) DEFAULT NULL,
  `gas_range_three` decimal(10,2) DEFAULT NULL,
  `gas_price_three` decimal(10,2) DEFAULT NULL,
  `gas_range_four` decimal(10,2) DEFAULT NULL,
  `gas_price_four` decimal(10,2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `create_by` int(10) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `usable` tinyint(1) DEFAULT NULL,
  `update_by` int(10) DEFAULT NULL,
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`gas_price_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 发票
CREATE TABLE `invoice` (
  `invoice_id` int(11) NOT NULL AUTO_INCREMENT,
  `invoice_code` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `invoice_number` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `emp_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `invoice_status` int(4) DEFAULT NULL COMMENT '发票状态(1已生成未分配，2已分配未打印，3已打印，4已报废)',
  `invoice_generate_time` datetime DEFAULT NULL COMMENT '发票生成时间',
  `invoice_assign_time` datetime DEFAULT NULL COMMENT '发票分配时间',
  `invoice_print_emp` int(11) DEFAULT NULL,
  `invoice_print_time` datetime DEFAULT NULL COMMENT '发票打印时间',
  `invoice_cancel_emp` int(11) DEFAULT NULL,
  `invoice_cancel_time` datetime DEFAULT NULL COMMENT '发票报废时间',
  `create_time` datetime DEFAULT NULL,
  `create_by` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `usable` tinyint(1) DEFAULT NULL,
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`invoice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 表具
CREATE TABLE `meter` (
  `meter_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '表具ID',
  `meter_code` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '表编号',
  `meter_stop_code` decimal(10,3) unsigned NOT NULL DEFAULT '0.000' COMMENT '表止码',
  `meter_type_id` int(8) unsigned NOT NULL COMMENT '表具型号ID',
  `meter_direction` int(4) NOT NULL COMMENT '琛ㄥ悜',
  `meter_prod_date` datetime NOT NULL COMMENT '表具生产日期',
  `meter_entry_date` datetime NOT NULL COMMENT '表具入库时间',
  `meter_install_time` datetime DEFAULT NULL COMMENT '表具安装时间',
  `meter_scrap_time` datetime DEFAULT NULL COMMENT '表具报废时间',
  `meter_validityperiod` int(10) unsigned DEFAULT '0' COMMENT '表具有效期(单位：年)',
  `meter_status` int(4) unsigned DEFAULT '1' COMMENT '琛ㄥ叿鐘舵€?',
  `meter_comm_key` char(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '通讯密钥',
  `meter_comm_num` char(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '通讯号码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`meter_id`),
  UNIQUE KEY `meter_id_index` (`meter_id`) USING BTREE,
  KEY `meter_type_id_index` (`meter_type_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000000000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 表具类型
CREATE TABLE `meter_type` (
  `meter_type_id` int(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '表具型号ID',
  `meter_category` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '表具类别',
  `meter_type` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '表具型号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`meter_type_id`),
  UNIQUE KEY `meter_type_id_index` (`meter_type_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 报修单
CREATE TABLE `repair_order` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `repair_order_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `repair_order_status` int(4) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `repair_type` int(4) NOT NULL,
  `gas_equipment_type` int(4) NOT NULL,
  `old_meter_id` int(10) NOT NULL,
  `old_meter_stop_code` decimal(10,3) DEFAULT NULL,
  `new_meter_id` int(10) DEFAULT NULL,
  `new_meter_stop_code` decimal(10,3) DEFAULT NULL,
  `repair_fault_type` int(4) NOT NULL,
  `repair_result_type` int(4) NOT NULL,
  `emp_id` int(10) DEFAULT NULL,
  `repair_start_time` datetime NOT NULL,
  `repair_end_time` datetime NOT NULL,
  `new_safety_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `old_safety_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` int(10) NOT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000002 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 冲账审核
CREATE TABLE `strike_nucleus` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id与业务无关',
  `order_id` int(11) DEFAULT NULL COMMENT '订单ID',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户名称',
  `nucleus_status` int(2) DEFAULT NULL COMMENT '审核状态(0,未审核,1已审核)',
  `nucleus_opinion` varchar(255) DEFAULT NULL COMMENT '审核意见(审核描述)',
  `nucleus_gas` decimal(10,1) DEFAULT NULL COMMENT '充值气量单位(方)',
  `nucleus_payment` decimal(10,2) DEFAULT NULL COMMENT '充值金额',
  `nucleus_launching_person` varchar(20) DEFAULT NULL COMMENT '发起人姓名',
  `recharge_time` datetime DEFAULT NULL COMMENT '充值时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` int(10) NOT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 字典
CREATE TABLE `sys_dictionary` (
  `dict_id` int(4) unsigned NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  `dict_category` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '绫诲埆',
  `dict_key` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `dict_value` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `dict_sort` int(10) unsigned DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注释',
  UNIQUE KEY `dic_id_index` (`dict_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 区域
CREATE TABLE `sys_district` (
  `dist_id` int(4) unsigned NOT NULL AUTO_INCREMENT COMMENT '区域ID',
  `dist_name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域名称',
  `dist_code` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '区域代码',
  `dist_category` int(4) unsigned NOT NULL COMMENT '鍖哄煙绫诲埆',
  `dist_address` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '区域地址',
  `dist_parent_id` int(4) unsigned DEFAULT NULL COMMENT '父级区域',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dist_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 日志
CREATE TABLE `sys_log` (
  `log_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `log_title` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志标题',
  `log_type` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志类型',
  `log_operator` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作人员',
  `log_operator_ip` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作人员IP地址',
  `log_exception_info` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '异常信息',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned NOT NULL COMMENT '创建者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注释',
  UNIQUE KEY `log_id_index` (`log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 机构
CREATE TABLE `sys_organization` (
  `org_id` int(4) unsigned NOT NULL AUTO_INCREMENT COMMENT '机构ID',
  `org_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构名称',
  `org_code` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '机构代码',
  `org_category` int(4) NOT NULL COMMENT '机构类别',
  `org_parent_id` bigint(20) unsigned DEFAULT NULL COMMENT '父级机构',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`org_id`),
  UNIQUE KEY `org_id_index` (`org_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 权限
CREATE TABLE `sys_permission` (
  `perm_id` int(4) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `perm_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限名称',
  `perm_caption` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限标题',
  `perm_href` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限路径',
  `perm_parent_id` int(4) unsigned DEFAULT NULL COMMENT '权限父级ID',
  `is_button` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否按钮',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`perm_id`),
  UNIQUE KEY `auth_id_index` (`perm_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 角色
CREATE TABLE `sys_role` (
  `role_id` int(4) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `is_admin` tinyint(1) DEFAULT NULL COMMENT '是否是管理员',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '注释',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_id_index` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `sys_role_dist` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(4) unsigned NOT NULL COMMENT '角色ID',
  `dist_id` int(4) unsigned NOT NULL COMMENT '区域ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `sys_role_org` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(4) unsigned NOT NULL COMMENT '角色ID',
  `org_id` int(4) unsigned NOT NULL COMMENT '机构ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 角色权限
CREATE TABLE `sys_role_perm` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(4) unsigned NOT NULL COMMENT '角色ID',
  `perm_id` int(4) unsigned NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 用户
CREATE TABLE `user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `user_name` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名称',
  `user_phone` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户电话',
  `user_idcard` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户身份证号码',
  `user_deed` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户房产证号码',
  `user_dist_id` int(4) unsigned NOT NULL COMMENT '用户所在区域ID',
  `user_address` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户住址',
  `user_type` int(4) unsigned NOT NULL COMMENT '用户类型',
  `user_gas_type` int(4) unsigned NOT NULL COMMENT '用气类型',
  `user_status` int(4) NOT NULL DEFAULT '1',
  `service_times` int(4) DEFAULT NULL COMMENT '维修次数',
  `user_locked` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否被锁定',
  `open_time` datetime DEFAULT NULL COMMENT '开户日期',
  `open_by` int(10) DEFAULT NULL COMMENT '开户者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id` (`user_id`) USING BTREE,
  KEY `user_dist_id_index` (`user_dist_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000000000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 用户卡片
CREATE TABLE `user_card` (
  `user_card_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL COMMENT '用户ID',
  `card_id` int(8) DEFAULT NULL COMMENT 'IC卡号',
  `card_identifier` varchar(12) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'IC卡识别号',
  `card_password` varchar(6) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `card_initialization` tinyint(1) DEFAULT '1' COMMENT '鍗″垵濮嬪寲鏍囪',
  `order_id` int(11) DEFAULT '0',
  `card_cost` decimal(10,2) DEFAULT NULL COMMENT '补卡工本费',
  `create_time` datetime DEFAULT NULL,
  `create_by` int(10) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` int(10) DEFAULT NULL,
  `usable` tinyint(1) DEFAULT NULL,
  `card_status` int(1) DEFAULT '1' COMMENT '卡片状态（正常卡片，销户卡片）',
  PRIMARY KEY (`user_card_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 用户过户
CREATE TABLE `user_change` (
  `id` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id与业务无关',
  `user_id` int(10) NOT NULL COMMENT '关联用户ID',
  `user_change_name` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名称',
  `user_change_phone` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户电话',
  `user_change_idcard` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户身份证号码',
  `user_change_deed` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户房产证号码',
  `user_old_name` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '旧用户名称',
  `user_old_phone` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '旧用户电话',
  `user_old_idcard` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '旧用户身份证号码',
  `user_old_deed` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '旧用户房产证号码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 用户锁定记录
CREATE TABLE `user_lock` (
  `user_lock_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '锁定Id',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '锁定用户Id',
  `is_lock` tinyint(1) DEFAULT NULL,
  `lock_reason` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '锁定原因',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` int(10) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` int(10) DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`user_lock_id`),
  KEY `user_lock_id_index` (`user_lock_id`) USING BTREE,
  KEY `user_lock_ibfk_1` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 用户表具记录
CREATE TABLE `user_meters` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户ID',
  `meter_id` int(10) unsigned NOT NULL COMMENT '表具ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`) USING BTREE,
  KEY `meter_id_index` (`meter_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000000000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 用户订单
CREATE TABLE `user_orders` (
  `order_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户ID',
  `employee_id` int(10) unsigned NOT NULL COMMENT '员工ID',
  `order_payment` decimal(10,2) DEFAULT NULL COMMENT '充值金额',
  `order_gas` decimal(10,1) DEFAULT NULL COMMENT '充值气量',
  `order_status` int(4) unsigned DEFAULT NULL COMMENT '订单状态',
  `order_create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `order_prestrike_time` datetime DEFAULT NULL COMMENT '订单预冲账时间(暂不使用)',
  `order_strike_time` datetime DEFAULT NULL COMMENT '发起冲账时间',
  `order_close_time` datetime DEFAULT NULL COMMENT '订单关闭时间',
  `flow_number` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '订单流水号',
  `order_type` int(4) unsigned DEFAULT NULL COMMENT '订单类型',
  `order_supplement` decimal(10,2) DEFAULT NULL COMMENT '应补金额',
  `account_state` int(4) DEFAULT NULL COMMENT '账务状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `order_id_index` (`order_id`) USING BTREE,
  KEY `employee_id_index` (`employee_id`),
  KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000000000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 微信用户
CREATE TABLE `wx_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `wx_user_id` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `create_time` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `getDistrictChildList`(distId INT) RETURNS varchar(1000) CHARSET utf8mb4 COLLATE utf8mb4_general_ci
BEGIN
    DECLARE sTemp VARCHAR(1000);
    DECLARE sTempChd VARCHAR(1000);
    SET sTempChd = cast(distId as CHAR);
    WHILE sTempChd is not null DO
      IF (sTemp is not null) THEN SET sTemp = concat(sTemp,',',sTempChd);
      ELSE SET sTemp = concat(sTempChd);
      END IF;
      SELECT group_concat(dist_id) INTO sTempChd FROM sys_district where FIND_IN_SET(dist_parent_id,sTempChd)>0;
    END WHILE;
    RETURN sTemp;
END
;;
DELIMITER ;
