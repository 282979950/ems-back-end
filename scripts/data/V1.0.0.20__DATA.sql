-- 员工数据
INSERT INTO `employee` VALUES ('1000000000', 'wxCashier', 'wxCashier', '1000', '1000', 'wxCashier', '4ACFAF58DECB315FEA0AE13C58E5BDCE', '', '', '', '', '1', '石门市', null, null, '1', null, null, null, null, '1', null);
INSERT INTO `employee` VALUES ('1000000001', 'admin', 'admin', '1000', '1000', 'admin', '4ACFAF58DECB315FEA0AE13C58E5BDCE', '', '', '','', '1', '石门市', '', null, '1', null, null, null, null, '1', '');

-- 员工角色数据
INSERT INTO `employee_role` VALUES ('1', '1000000001', '1001', null, null, null, null, '1', '');

-- 气价数据
INSERT INTO `gas_price` VALUES ('1', '1', '1', '0.00', '1.00', '100.00', '2.00', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `gas_price` VALUES ('2', '1', '2', '0.00', '1.00', '100.00', '2.00', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `gas_price` VALUES ('3', '2', '2', '0.00', '1.00', '100.00', '2.00', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `gas_price` VALUES ('4', '2', '1', '0.00', '1.00', '100.00', '2.00', null, null, null, null, null, null, null, null, null, null);

-- 表具类型数据
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

-- 字典数据
INSERT INTO `sys_dictionary` VALUES ('1000', 'account_state', '预冲账', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1001', 'account_state', '未冲账', '2', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1002', 'account_state', '已冲帐', '3', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1003', 'card_status', '正常卡片', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1004', 'card_status', '已销户卡片', '2', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1005', 'cardCost', '15', '15', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1006', 'dist_type', '市', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1007', 'dist_type', '镇(乡)', '2', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1008', 'dist_type', '村', '3', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1009', 'dist_type', '小区', '4', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1010', 'emp_login_flag', '是', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1011', 'emp_login_flag', '否', '0', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1012', 'emp_type', '管理员', '1', '1', null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1013', 'emp_type', '测试', '2', '2', null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1014', 'fill_gas_order_status', '未处理', '0', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1015', 'fill_gas_order_status', '已处理', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1016', 'fill_gas_order_status', '已撤销', '2', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1017', 'fill_gas_order_type', '维修补气单', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1018', 'fill_gas_order_type', '超用补缴单', '2', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1019', 'gas_equipment_type', '燃气表', '0', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1020', 'gas_price', '1-299', '2.79', '1', null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1021', 'gas_price', '300-549', '3.09', '2', null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1022', 'gas_price', '550-', '5.68', '3', null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1023', 'invoice_status', '已生成未分配', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1024', 'invoice_status', '已分配未打印', '2', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1025', 'invoice_status', '已打印', '3', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1026', 'invoice_status', '已作废', '4', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1027', 'meter_direction', '左', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1028', 'meter_direction', '右', '0', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1029', 'meter_status', '已入库', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1030', 'meter_status', '已挂表', '2', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1031', 'meter_status', '已报废', '3', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1032', 'nucleus_status', '未审核', '0', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1033', 'nucleus_status', '已审核', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1034', 'order_status', '已充值待写卡', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1035', 'order_status', '已写卡', '2', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1036', 'order_status', '待支付', '3', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1037', 'order_status', '已取消', '4', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1038', 'order_type', '开户订单', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1039', 'order_type', '普通订单', '2', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1040', 'order_type', '补卡订单', '3', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1041', 'order_type', '超用补缴订单', '4', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1042', 'order_type', '微信订单', '5', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1043', 'org_type', '公司', '1', '1', null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1044', 'org_type', '总经办', '2', '2', null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1045', 'org_type', '部门', '3', '3', null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1046', 'org_type', '小组', '4', '4', null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1047', 'repair_fault_type', '插维修卡', '0', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1048', 'repair_fault_type', '阀门关', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1049', 'repair_fault_type', '数显', '2', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1050', 'repair_fault_type', '乱码', '3', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1051', 'repair_fault_type', '表不走字', '4', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1052', 'repair_fault_type', '新换蓝', '5', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1053', 'repair_fault_type', '表挂反', '6', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1054', 'repair_fault_type', '表漏气', '7', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1055', 'repair_fault_type', '表响', '8', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1056', 'repair_fault_type', '单据遗失', '9', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1057', 'repair_fault_type', '单据过期', '10', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1058', 'repair_fault_type', '卡锁', '11', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1059', 'repair_fault_type', '显示不清', '12', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1060', 'repair_fault_type', '短信表升级', '13', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1061', 'repair_fault_type', '表慢', '14', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1062', 'repair_fault_type', '卡字', '15', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1063', 'repair_fault_type', '卡坏', '16', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1064', 'repair_fault_type', '补单', '17', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1065', 'repair_fault_type', '销售挂表', '18', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1066', 'repair_fault_type', '恢复挂表', '19', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1067', 'repair_fault_type', '其他', '20', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1068', 'repair_fault_type', '置换', '21', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1069', 'repair_fault_type', '错过换表', '22', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1070', 'repair_fault_type', '3000(不关阀)', '23', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1071', 'repair_fault_type', '0002', '24', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1072', 'repair_fault_type', '液晶故障', '25', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1073', 'repair_fault_type', '铜接头漏气', '26', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1074', 'repair_fault_type', '报停', '27', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1075', 'repair_result_type', '主管无气', '0', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1076', 'repair_result_type', '紧急开启', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1077', 'repair_result_type', '电话指导好', '2', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1078', 'repair_result_type', '重复工单', '3', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1079', 'repair_result_type', '清零开票', '4', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1080', 'repair_result_type', '检正常', '5', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1081', 'repair_result_type', '换表', '6', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1082', 'repair_result_type', '电池问题', '7', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1083', 'repair_result_type', '换极片', '8', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1084', 'repair_result_type', '换测控板', '9', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1085', 'repair_result_type', '按功能键', '10', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1086', 'repair_result_type', '正常充入', '11', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1087', 'repair_result_type', '排空气', '12', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1088', 'repair_result_type', '灶具问题', '13', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1089', 'repair_result_type', '主管无气', '14', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1090', 'repair_result_type', '管道', '15', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1091', 'repair_result_type', '开票', '16', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1092', 'repair_result_type', '转稽查', '17', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1093', 'repair_result_type', '紧急开启', '18', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1094', 'repair_result_type', '报警器故障', '19', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1095', 'repair_result_type', '插维修卡', '20', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1096', 'repair_result_type', '充值超量', '21', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1097', 'repair_result_type', '开表阀门', '22', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1098', 'repair_result_type', '开通', '23', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1099', 'repair_result_type', '柜子挡住', '24', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1100', 'repair_result_type', '拒绝换表', '25', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1101', 'repair_result_type', '重写充值卡', '26', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1102', 'repair_result_type', '重写开通卡', '27', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1103', 'repair_result_type', '插0010卡', '28', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1104', 'repair_result_type', '插对时卡', '29', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1105', 'repair_result_type', '其他', '30', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1106', 'repair_result_type', '上门指导好', '31', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1107', 'repair_result_type', '换极片清零', '32', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1108', 'repair_type', '换表', '0', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1109', 'repair_type', '维修', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1110', 'repair_type', '上门指导', '2', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1111', 'repair_type', '电话指导', '3', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1112', 'repair_type', '校表 ', '4', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1113', 'repair_type', '报停拆表', '5', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1114', 'repair_type', '机换IC卡', '6', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1115', 'repair_type', '工商户换表', '7', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1116', 'repair_type', '工商户维修', '8', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1117', 'repair_type', '挂表', '9', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1118', 'user_gas_type', '用气类型1', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1119', 'user_gas_type', '用气类型2', '2', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1120', 'user_gas_type', '用气类型3', '3', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1121', 'user_status', '已建档待挂表', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1122', 'user_status', '已挂表待开户', '2', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1123', 'user_status', '已开户', '3', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1124', 'user_status', '已销户', '4', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1125', 'user_type', '民用用户', '1', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1126', 'user_type', '商用用户', '2', null, null, null, null, null, '1', '');
INSERT INTO `sys_dictionary` VALUES ('1127', 'apply_repair_status', '待处理', '1', null, null, null, null, null, '1', null);
INSERT INTO `sys_dictionary` VALUES ('1128', 'apply_repair_status', '已接单', '2', null, null, null, null, null, '1', null);
INSERT INTO `sys_dictionary` VALUES ('1129', 'apply_repair_status', '已完成', '3', null, null, null, null, null, '1', null);
INSERT INTO `sys_dictionary` VALUES ('1130', 'apply_repair_status', '已撤销', '4', null, null, null, null, null, '1', null);
INSERT INTO `sys_dictionary` VALUES ('1131', 'apply_repair_type', '微信报修', '1', null, null, null, null, null, '1', null);
INSERT INTO `sys_dictionary` VALUES ('1132', 'apply_repair_type', '电话报修', '2', null, null, null, null, null, '1', null);
INSERT INTO `sys_dictionary` VALUES ('1133', 'wx_notice_type', '通知', '1', null, null, null, null, null, '1', null);
INSERT INTO `sys_dictionary` VALUES ('1134', 'wx_notice_type', '提醒', '2', null, null, null, null, null, '1', null);

-- 区域数据
INSERT INTO `sys_district` VALUES ('1000', '石门市', 'SMS', '1', '', null, null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1001', '楚江镇', 'CJZ', '2', '', '1000', null, null, null, null, '0', '');
INSERT INTO `sys_district` VALUES ('1002', '蒙泉镇', 'MQZ', '2', '', '1000', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1003', '顺岭岗村', 'SLGC', '3', '', '1002', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1004', '顺岭岗村001', '', '4', null, '1004', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1005', '顺岭岗村002', '', '4', '', '1003', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1006', '顺岭岗村003', '', '4', null, '1003', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1007', '清水潭村', 'QSTC', '3', '', '1002', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1008', '夹山镇', 'JSZ', '2', null, '1000', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1009', '二都乡', 'EDX', '2', null, '1000', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1010', '新铺乡', 'XPX', '2', null, '1000', null, null, null, null, '1', '');
INSERT INTO `sys_district` VALUES ('1011', '白云乡', 'BYX', '2', null, '1000', null, null, null, null, '1', '');

-- 目录数据
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

-- 机构数据
INSERT INTO `sys_organization` VALUES ('1000', '武汉表具管理有限公司', null, '1', '1000', null, null, '2018-09-19 15:57:34', '12324', '1', null);
INSERT INTO `sys_organization` VALUES ('1002', '形象部', null, '3', '1001', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1003', '艺术部', 'wqw', '3', '1001', null, null, '2018-09-11 09:50:52', '12324', '1', null);
INSERT INTO `sys_organization` VALUES ('1004', '教育部', '恩施', '3', '1001', null, null, '2018-10-15 16:25:50', '12324', '1', null);
INSERT INTO `sys_organization` VALUES ('1005', '宣传', 'testlx', '3', '1001', null, null, '2018-09-11 10:46:52', '12324', '1', null);
INSERT INTO `sys_organization` VALUES ('1006', '业务部', null, '3', '1001', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1007', '研发部', null, '3', '1001', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1008', '实施小组', null, '4', '1003', null, null, '2018-09-11 09:52:16', '12324', '1', null);
INSERT INTO `sys_organization` VALUES ('1009', '其他小组', null, '4', '1007', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1010', '测试小组', null, '4', '1007', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1011', '电子政务小组', null, '4', '1007', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1012', '档案小组', null, '4', '1007', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1013', '总经办事处2', null, '2', '1000', null, null, '2018-09-03 16:39:21', '12324', '1', null);
INSERT INTO `sys_organization` VALUES ('1014', '业务部test', null, '3', '1013', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1015', '宣传部test', null, '3', '1013', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1016', '教育部test', null, '3', '1013', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1017', '艺术部test', null, '3', '1013', null, null, null, null, '1', null);
INSERT INTO `sys_organization` VALUES ('1018', '形象部test', 'DDD', '3', '1013', null, null, '2018-10-23 13:56:41', '12324', '1', null);

-- 系统权限
INSERT INTO `sys_permission` VALUES ('1000', 'visit', '根目录', '/', null, '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1001', 'sys:visit', '系统管理', '/sys/', '1000', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1002', 'sys:dist:visit', '区域管理', '/sys/dist', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1003', 'sys:org:visit', '机构管理', '/sys/org', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1004', 'sys:emp:visit', '用户管理', '/sys/emp', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1005', 'sys:role:visit', '角色管理', '/sys/role', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1006', 'sys:perm:visit', '权限管理', '/sys/perm', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1007', 'sys:dic:visit', '字典管理', '/sys/dic', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1008', 'sys:gasPrice:visit', '气价管理', '/sys/gasprice', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1009', 'sys:log:visit', '日志管理', '/sys/log', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1010', 'sys:notice:visit', '公告管理', '/sys/notice', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1011', 'account:visit', '账户开户管理', '/account/', '1000', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1012', 'account:createArchive:visit', '用户建档', '/account/createArchive', '1011', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1013', 'account:entryMeter:visit', '表具入库', '/account/entryMeter', '1011', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1014', 'account:installation:visit', '挂表信息', '/account/installation', '1011', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1015', 'account:createAccount:visit', '账户开户', '/account/createAccount', '1011', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1016', 'account:lockAccount:visit', '账户锁定', '/account/lockAccount', '1011', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1017', 'account:alter:visit', '账户变更', '/account/alter', '1011', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1018', 'recharge:visit', '充值缴费管理', '/recharge/', '1000', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1019', 'recharge:pre:visit', '预付费充值', '/recharge/pre', '1018', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1020', 'recharge:supplement:visit', '补卡充值', '/recharge/supplement', '1018', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1021', 'recharge:suff:visit', '后付费缴费', '/recharge/suff', '1018', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1022', 'recharge:order:visit', '订单管理', '/recharge/order', '1018', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1023', 'invoice:visit', '发票管理', '/invoice', '1000', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1024', 'invoice:assign:visit', '发票分配', '/invoice/assign', '1023', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1025', 'invoice:printCancel:visit', '发票查询', '/invoice/printcancel', '1023', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1026', 'invoice:einvoice:visit', '电子发票管理', '/invoice/einvoice', '1023', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1027', 'repairorder:visit', '维修补气管理', '/repairorder/', '1000', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1028', 'repairorder:entry:visit', '维修单录入提交', '/repairorder/entry', '1027', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1029', 'repairorder:fillGas:visit', '维修补气结算', '/repairorder/supplement', '1027', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1030', 'repairorder:iccardinit:visit', 'IC卡初始化', '/repairorder/iccardinit', '1027', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1031', 'financial:visit', '账务处理', '/financial/', '1000', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1032', 'financial:prestrike:visit', '预冲账处理', '/financial/prestrike', '1031', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1033', 'financial:strike:visit', '冲账处理', '/financial/strike', '1031', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1034', 'meter:visit', '表具运行管理', '/meter/', '1000', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1035', 'meter:readmeter:visit', '抄表管理', '/meter/readmeter', '1034', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1036', 'meter:valve:visit', '阀门控制', '/meter/valve', '1034', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1037', 'meter:exception:visit', '异常情况管理', '/meter/exception', '1034', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1038', 'querystats:visit', '查询统计', '/querystats/', '1000', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1039', 'querystats:ardQuery:visit', 'IC卡查询', '/querystats/ardQuery', '1036', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1040', 'querystats:account:visit', '开户信息查询', '/querystats/account', '1038', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1041', 'querystats:accountdetail:visit', '燃气用户信息查询', '/querystats/accountdetail', '1038', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1042', 'querystats:abnormaluser:visit', '异常用户查询', '/querystats/abnormaluser', '1038', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1043', 'querystats:businessDataQuery:visit', '营业数据查询', '/businessDataQuery/data', '1038', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1044', 'querystats:report:visit', '营业报表统计', '/querystats/report', '1038', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1045', 'applyRepair:visit', '报修单管理', '/applyRepair/', '1000', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1046', 'applyRepair:entryApplyRepair:visit', '报修单录入', '/applyRepair/entryApplyRepair', '1045', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1047', 'sys:solet:visit', '网点管理', '/sys/solet', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1048', 'sys:evalItem:visit', '评价项管理', '/sys/evalItem', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1049', 'sys:eval:visit', '评价查询', '/sys/eval', '1038', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
INSERT INTO `sys_permission` VALUES ('1050', 'sys:wxNotice:visit', '微信公告管理', '/sys/wxNotice', '1001', '0', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

INSERT INTO `sys_permission` (`perm_name`, `perm_caption`, `perm_href`, `perm_parent_id`, `is_button` , `create_time` ,  `create_by` , `update_time` , `update_by`, `usable` , `remarks`)
VALUES
('account:alter:update', '变更修改', '', '1017', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:createAccount:export', '导出', '', '1015', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:createAccount:import', '导入', '', '1015', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:createAccount:retrieve', '查询', '', '1015', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:createAccount:update', '开户', '', '1015', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:createArchive:create', '增加', '', '1012', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:createArchive:delete', '删除', '', '1012', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:createArchive:export', '导出', '', '1012', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:createArchive:import', '导入', '', '1012', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:createArchive:retrieve', '查询', '', '1012', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:createArchive:update', '修改', '', '1012', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:entryMeter:create', '增加', '', '1013', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:entryMeter:delete', '删除', '', '1013', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:entryMeter:export', '导出', '', '1013', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:entryMeter:import', '导入', '', '1013', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:entryMeter:retrieve', '查询', '', '1013', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:entryMeter:update', '修改', '', '1013', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:installation:export', '导出', '', '1014', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:installation:import', '导入', '', '1014', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:installation:retrieve', '查询', '', '1014', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:installation:update', '修改', '', '1014', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:lockAccount:export', '导出', '', '1016', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:lockAccount:import', '导入', '', '1016', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:lockAccount:lock', '锁定解锁', '', '1016', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:lockAccount:lockList', '锁定记录', '', '1016', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('account:lockAccount:retrieve', '查询', '', '1016', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('invoice:assign:add', '发票录入', '', '1024', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('invoice:assign:assignment', '发票分配', '', '1024', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('invoice:assign:retrieve', '查询', '', '1024', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('invoice:printCancel:retrieve', '发票查询', '', '1025', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('querystats:abnormaluser:export', '导出', '', '1042', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('querystats:abnormaluser:retrieve', '查询', '', '1042', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('querystats:account:export', '开户信息查询导出', '', '1040', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('querystats:account:retrieve', '查询', '', '1040', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('querystats:ardQuery:record', '识别IC卡', '', '1039', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('recharge:order:cancel', '发票报废', '', '1022', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('recharge:order:new', '新票补打', '', '1022', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('recharge:order:old', '原票补打', '', '1022', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('recharge:order:print', '订单打印', '', '1022', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('recharge:order:record', '识别IC卡', '', '1022', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('recharge:order:retrieve', '查询', '', '1022', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('recharge:order:writeCard', '订单页面写卡', '', '1022', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('recharge:pre:new', '发卡充值', '', '1019', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('recharge:pre:record', '识别IC卡', '', '1019', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('recharge:pre:retrieve', '查询', '', '1019', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('recharge:pre:update', '预充值', '', '1019', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('invoice:printCancel:cancel', '作废', '', '1025', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('recharge:supplement:retrieve', '补卡查询', '', '1020', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('recharge:supplement:supList', '历史补卡记录', '', '1020', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('recharge:supplement:update', '补卡', '', '1020', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('repairorder:entry:create', '增加', '', '1028', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('repairorder:entry:delete', '删除', '', '1028', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('repairorder:entry:retrieve', '查询', '', '1028', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('repairorder:entry:update', '修改', '', '1028', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('repairorder:fillGas:fillGas', '补气补缴', '', '1029', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:dic:create', '增加', '', '1007', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:dic:delete', '删除', '', '1007', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:dic:export', '导出', '', '1007', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:dic:import', '导入', '', '1007', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:dic:retrieve', '查询', '', '1007', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:dic:update', '修改', '', '1007', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:dist:create', '增加', '', '1002', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:dist:delete', '删除', '', '1002', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:dist:export', '导出', '', '1002', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:dist:import', '导入', '', '1002', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:dist:retrieve', '查询', '', '1002', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:dist:update', '修改', '', '1002', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:emp:create', '增加', '', '1004', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:emp:delete', '删除', '', '1004', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:emp:export', '导出', '', '1004', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:emp:import', '导入', '', '1004', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:emp:retrieve', '查询', '', '1004', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:emp:update', '修改', '', '1004', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:gasPrice:update', '修改', '', '1008', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:log:create', '增加', '', '1009', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:log:delete', '删除', '', '1009', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:log:export', '导出', '', '1009', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:log:import', '导入', '', '1009', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:log:retrieve', '查询', '', '1009', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:log:update', '修改', '', '1009', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:notice:create', '增加', '', '1010', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:notice:delete', '删除', '', '1010', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:notice:export', '导出', '', '1010', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:notice:import', '导入', '', '1010', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:notice:retrieve', '查询', '', '1010', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:notice:update', '修改', '', '1010', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:org:create', '增加', '', '1003', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:org:delete', '删除', '', '1003', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:org:export', '导出', '', '1003', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:org:import', '导入', '', '1003', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:org:retrieve', '查询', '', '1003', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:org:update', '修改', '', '1003', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:perm:create', '增加', '', '1006', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:perm:delete', '删除', '', '1006', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:perm:export', '导出', '', '1006', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:perm:import', '导入', '', '1006', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:perm:retrieve', '查询', '', '1006', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:perm:update', '修改', '', '1006', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:role:create', '增加', '', '1005', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:role:delete', '删除', '', '1005', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:role:export', '导出', '', '1005', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:role:import', '导入', '', '1005', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:role:retrieve', '查询', '', '1005', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:role:update', '修改', '', '1005', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('applyRepair:entryApplyRepair:create', '增加', '', '1046', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('applyRepair:entryApplyRepair:delete', '删除', '', '1046', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('applyRepair:entryApplyRepair:update', '修改', '', '1046', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('applyRepair:entryApplyRepair:retrieve', '查询', '', '1046', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:solet:create', '增加', '', '1047', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:solet:delete', '删除', '', '1047', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:solet:update', '修改', '', '1047', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:solet:retrieve', '查询', '', '1047', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:evalItem:create', '增加', '', '1048', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:evalItem:delete', '删除', '', '1048', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:evalItem:update', '修改', '', '1048', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:evalItem:retrieve', '查询', '', '1048', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', ''),
('sys:eval:retrieve', '查询', '', '1049', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
('sys:wxNotice:create', '新增', '', '1050', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
('sys:wxNotice:delete', '删除', '', '1050', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');
('sys:wxNotice:retrieve', '查询', '', '1050', '1', '2018-08-01 15:38:26', '1000000001', '2018-08-01 15:38:31', '1000000001', '1', '');

-- 角色数据
INSERT INTO `sys_role` VALUES ('1001', 'admin', '1000', '1000', '1', null, null, null, null, '1', '');