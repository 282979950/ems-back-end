-- 报修数据
CREATE TABLE `apply_repair` (
  `apply_repair_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `apply_repair_flow_number` varchar(32) DEFAULT NULL COMMENT '报修单编号',
  `user_id` int(10) unsigned NOT NULL COMMENT '户号',
  `apply_repair_type` int(4) NOT NULL COMMENT '报修类型',
  `apply_repair_status` int(4) NOT NULL COMMENT '报修状态',
  `apply_repair_fault_desc` varchar(255) NOT NULL COMMENT '故障描述',
  `apply_repair_appeal_content` varchar(255) DEFAULT NULL COMMENT '诉求内容',
  `apply_repair_time` datetime NOT NULL COMMENT '申请报修时间',
  `meter_id` int(10) unsigned NOT NULL COMMENT '表具ID',
  `current_order_gas_count` decimal(10,1) unsigned NOT NULL COMMENT '当前表购气量',
  `start_time` datetime DEFAULT NULL COMMENT '预约开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '预约截止时间',
  `user_tel_phone` varchar(20) DEFAULT NULL COMMENT '主叫号码',
  `has_reminded` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `has_eval` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否评价',
  `wx_open_id` varchar(30) DEFAULT NULL COMMENT '微信openId',
  `form_id` varchar(50) DEFAULT NULL COMMENT '撤销表单formId',
  `sign_person_id` varchar(64) DEFAULT NULL COMMENT '维修人员ID',
  `repair_man_phone` varchar(20) DEFAULT NULL COMMENT '维修人员手机号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` int(10) DEFAULT NULL COMMENT '修改人',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `remarks` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`apply_repair_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- 服务网点
CREATE TABLE `service_outlet` (
`service_outlet_id`  int(11) NOT NULL AUTO_INCREMENT,
`service_outlet_name`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网点名称' ,
`service_outlet_address`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网点地址' ,
`service_outlet_open_time`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业时间' ,
`service_outlet_phone`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务电话' ,
`service_outlet_content`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业范围' ,
`tx_longitude`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '腾讯地图经度' ,
`tx_latitude`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '腾讯地图纬度' ,
`bd_longitude`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '百度地图经度' ,
`bd_latitude`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '百度地图纬度' ,
`create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`create_by`  int(10) NULL DEFAULT NULL COMMENT '创建人' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
`update_by`  int(10) NULL DEFAULT NULL COMMENT '修改人' ,
`usable`  tinyint(1) NULL DEFAULT NULL ,
`remarks`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`service_outlet_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
ROW_FORMAT=DYNAMIC
;

-- 通知公告
CREATE TABLE `sys_notice` (
`notice_id`  int(11) NOT NULL ,
`notice_title`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知标题' ,
`notice_type`  int(2) NULL DEFAULT NULL COMMENT '通知类型' ,
`notice_content`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知内容' ,
`create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`create_by`  int(10) NULL DEFAULT NULL COMMENT '创建人' ,
`update_time`  datetime NULL DEFAULT NULL COMMENT '修改时间' ,
`update_by`  int(10) NULL DEFAULT NULL COMMENT '修改人' ,
`usable`  tinyint(1) NULL DEFAULT NULL ,
`remark`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`notice_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
ROW_FORMAT=DYNAMIC
;

-- 评价项管理
CREATE TABLE `evaluation_item` (
`evaluation_item_id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '评价项Id' ,
`evaluation_item_content`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评价项内容' ,
`create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`create_by`  int(10) NULL DEFAULT NULL COMMENT '创建人' ,
`update_time`  datetime NULL DEFAULT NULL ,
`update_by`  int(10) NULL DEFAULT NULL ,
`usable`  tinyint(1) NULL DEFAULT NULL ,
`remarks`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`evaluation_item_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
ROW_FORMAT=DYNAMIC
;

--动态评价内容
CREATE TABLE `evaluation` (
`evaluation_id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '评价Id' ,
`apply_repair_id`  int(11) NULL DEFAULT NULL COMMENT '维修单Id' ,
`evaluation_item_id`  int(11) NULL DEFAULT NULL COMMENT '评价项Id' ,
`evaluation_content`  tinyint(1) NULL DEFAULT NULL COMMENT '评价内容(是或否)' ,
`evaluation_time`  datetime NULL DEFAULT NULL COMMENT '评价时间' ,
PRIMARY KEY (`evaluation_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
ROW_FORMAT=DYNAMIC
;

--固定评价内容
CREATE TABLE `fixed_evaluation` (
`fixed_evaluation_id`  int(11) NOT NULL AUTO_INCREMENT ,
`apply_repair_id`  int(11) NULL DEFAULT NULL ,
`fixed_evaluation_select`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否满意本次服务的选择' ,
`fixed_evaluation_content`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评价文本内容' ,
`fixed_evaluation_time`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`fixed_evaluation_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
ROW_FORMAT=DYNAMIC
;

-- 微信公告
CREATE TABLE `wx_notice` (
  `wx_notice_id` int(4) unsigned NOT NULL AUTO_INCREMENT COMMENT '微信公告ID',
  `wx_notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '微信公告标题',
  `wx_notice_type` int(4) NOT NULL COMMENT '微信公告类型',
  `wx_notice_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '微信公告内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(10) unsigned DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` int(10) unsigned DEFAULT NULL COMMENT '更新者',
  `usable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`wx_notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
