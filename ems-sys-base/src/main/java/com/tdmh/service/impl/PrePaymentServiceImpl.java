package com.tdmh.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.entity.UserCard;
import com.tdmh.entity.UserOrders;
import com.tdmh.entity.mapper.*;
import com.tdmh.param.PrePaymentParam;
import com.tdmh.param.WriteCardParam;
import com.tdmh.service.IPrePaymentService;
import com.tdmh.util.OrderGasUtil;
import com.tdmh.utils.IdWorker;
import com.tdmh.utils.RmbConvert;
import com.tdmh.utils.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Lucia on 2018/10/12.
 */
@Service
public class PrePaymentServiceImpl implements IPrePaymentService {

    @Autowired
    private PrePaymentMapper prePaymentMapper;

    @Autowired
    private UserOrdersMapper userOrdersMapper;

    @Autowired
    private UserCardMapper userCardMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FillGasOrderMapper fillGasOrderMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Override
    public JsonData getAllOrderInformation() {
        List<PrePaymentParam> list = prePaymentMapper.getAllOrderInformation(null);
        return list == null || list.size() == 0 ? JsonData.successMsg("暂无可充值用户") : JsonData.successData(list);
    }

    @Transactional
    @Override
    public JsonData createUserOrder(UserOrders userOrders,String name, String userType) {
        BeanValidator.check(userOrders);
        //充值时查询该用户是否存在未处理的补气补缴单,若有则不允许充值fillGasOrderStatus=0查询未处理
        int count = fillGasOrderMapper.getFillGasOrderCountByUserId(userOrders.getUserId(),0);
        if(count>0){
            return JsonData.fail("该户存在未处理的补气补缴单请无法充值");
        }
        //支持最大充气量
        switch (OrderGasUtil.MaxOrderGas(userType, userOrders.getOrderGas())){
            case 1:
                return JsonData.fail("未获取到数据请重试！");
            case 2:
                return JsonData.fail("商用最大支持9999");
            case 3:
                return JsonData.fail("民用最大支持999");
        }
        //限定充值次数.每天限定充值一次，查询当前是否存在：2普通订单，3补卡订单，5微信订单
        int resultOrdersCount = userOrdersMapper.queryCurrentDataByDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), userOrders.getUserId());
        if(resultOrdersCount > 0){
            return JsonData.fail("每天只支持充值一次");
        }
        userOrders.setUsable(true);
        userOrders.setFlowNumber(IdWorker.getId().nextId() + "");
        //判断是否使用优惠券充值
        if (userOrders.getCouponGas() != null && userOrders.getCouponGas().compareTo(BigDecimal.ZERO) > 0) {
            if (StringUtils.isNotBlank(userOrders.getCouponNumber())) {
                //优惠券回收
                List<String> list = Arrays.asList(userOrders.getCouponNumber().split(","));
                couponMapper.deleteCouponByCouponNumber(list);
            }
        }
        BigDecimal freeGas = userOrders.getFreeGas();
        if (freeGas == null || freeGas.equals(BigDecimal.ZERO)) {
            userOrders.setFreeGas(BigDecimal.ZERO);
        } else {
            userMapper.updateFreeGasFlagByUserId(userOrders.getUserId(), false);
            userOrders.setFreeGas(freeGas);
        }
        userOrders.setOrderType(2); //2为普通充值类型
        userOrders.setUpdateTime(new Date());
        userOrders.setOrderStatus(1);
        int resultCount = userOrdersMapper.insert(userOrders);
        if (resultCount == 0) {
            return JsonData.fail("充值订单失败");
        }
        UserCard userCard = userCardMapper.getUserCardByUserIdAndCardId(userOrders.getUserId(), null);
        WriteCardParam param = new WriteCardParam();
        param.setIccardId(userCard.getCardId());
        param.setIccardPassword(userCard.getCardPassword());
        param.setOrderGas(userOrders.getOrderGas());
        param.setOrderId(userOrders.getOrderId());
        param.setFlowNumber(userOrders.getFlowNumber());
        int serviceTimes = userMapper.getServiceTimesByUserId(userOrders.getUserId());
        param.setServiceTimes(serviceTimes);
        param.setName(name);
        RmbConvert rmb = new RmbConvert();
        param.setRmbBig(rmb.simpleToBig(userOrders.getOrderPayment().doubleValue()));
        return JsonData.success(param, "充值订单成功");
    }

    @Override
    public JsonData selectFindListByPre(PrePaymentParam param, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PrePaymentParam> list = prePaymentMapper.getAllOrderInformation(param);
        PageInfo<PrePaymentParam> info = new PageInfo<>(list);
        return JsonData.success(info,"查询成功");
    }

    /**
     * 查询统计---ic卡查询
     * @param param
     * @return
     */
    @Override
    public JsonData selectFindListArdQueryService(PrePaymentParam param) {
        List<PrePaymentParam> list = prePaymentMapper.getOrderInformationCardOrderGas(param);
        if(list.size()==0){
            return JsonData.fail("暂无用户记录,或该卡为新卡未查询到数据");
        }
        //把卡面的气量设置该条数据中
        list.get(0).setCardOrderGas(param.getCardOrderGas());
        return list == null || list.size() == 0 ? JsonData.successMsg("暂无用户记录") : JsonData.success(list,"查询成功");
    }
    @Override
    public void selectFindListExportArdQueryService(PrePaymentParam param,HttpServletResponse response){
        //执行导出开始
        //设置公共变量
        HSSFRow nRow =null;
        int rowNo=2;
        //创建单元格对象
        int cellNO=0;

        //获取页面头部筛选条件结束
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle setBorder = wb.createCellStyle();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet("ic卡查询导出");
        //自定义导出名称
        String fileName =sheet.getSheetName();
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1=sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell=row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("ic卡查询导出");
        //查询list表数据
        List<PrePaymentParam> list = prePaymentMapper.getOrderInformationCardOrderGas(param);
        list.get(0).setCardOrderGas(param.getCardOrderGas());
        String titles [] ={"用户编号","IC卡号","卡识别号","客户姓名","客户电话","客户地址","卡内气量","购气总量","购气次数"};
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,titles.length));
        int count =0;
        //在sheet里创建第二行
        HSSFRow row2=sheet.createRow(1);
        for (int i = 0; i < titles.length; i++) {

            row2.createCell(count).setCellValue(titles[i]);
            count++;
        }
        for (int i=0;i<list.size();i++) {
            cellNO=0;
            //在sheet里创建第三行根据后台<list>长度决定创建多少
            nRow = sheet.createRow(rowNo++);
            //初始化时间
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            //初始化BigDecimal
            BigDecimal bd = new BigDecimal("0");
            //用户编号

            if((list.get(i).getUserId()).intValue()!=0 ){

                nRow.createCell(cellNO++).setCellValue(list.get(i).getUserId());//设置内容与对应单元格对象
            }else{
                nRow.createCell(cellNO++).setCellValue("");
            }
            //IC卡号

            if((list.get(i).getIccardId()).intValue()!=0 ){

                nRow.createCell(cellNO++).setCellValue(list.get(i).getIccardId());
            }else{
                nRow.createCell(cellNO++).setCellValue("");
            }
            //卡识别号
            if((list.get(i).getIccardIdentifier())!=null || (list.get(i).getIccardIdentifier())!="" ){

                nRow.createCell(cellNO++).setCellValue(list.get(i).getIccardIdentifier());
            }else{
                nRow.createCell(cellNO++).setCellValue("");
            }
            //客户姓名

            if((list.get(i).getUserName())!=null|| (list.get(i).getUserName())!="" ){

                nRow.createCell(cellNO++).setCellValue(list.get(i).getUserName());
            }else{
                nRow.createCell(cellNO++).setCellValue("");
            }
            //客户电话

            if((list.get(i).getUserPhone())!=null|| (list.get(i).getUserPhone())!="" ){

                nRow.createCell(cellNO++).setCellValue(list.get(i).getUserPhone());
            }else{
                nRow.createCell(cellNO++).setCellValue("");
            }
            //客户地址

            if((list.get(i).getUserAddress())!=null ||(list.get(i).getUserAddress())!="" ){

                nRow.createCell(cellNO++).setCellValue(list.get(i).getUserAddress());
            }else{
                nRow.createCell(cellNO++).setCellValue("");
            }
            //卡内气量

            if((list.get(i).getCardOrderGas())!=null){
                bd=list.get(i).getCardOrderGas();
                nRow.createCell(cellNO++).setCellValue(bd.toString());
            }else{
                nRow.createCell(cellNO++).setCellValue("");
            }
            //购气总量

            if((list.get(i).getTotalOrderGas())!=null ){
                bd=list.get(i).getTotalOrderGas();

                nRow.createCell(cellNO++).setCellValue(bd.toString());
            }else{
                nRow.createCell(cellNO++).setCellValue("");
            }
            //购气次数
            if((list.get(i).getTotalOrderTimes()).intValue()!=0 ){

                nRow.createCell(cellNO++).setCellValue(list.get(i).getTotalOrderTimes());
            }else{
                nRow.createCell(cellNO++).setCellValue("");
            }
        }

        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(5, 8766);
        //输出Excel文件
        OutputStream output= null;
        try {
            output = response.getOutputStream();
            response.reset();
            // 默认文件名称  response.setHeader("Content-disposition", "attachment; filename=details.xls");
            // 设置自定义名称
            response.addHeader("Content-Disposition", "attachment;filename="+new String((fileName + ".xls").getBytes(), "iso-8859-1"));
            response.setContentType("application/msexcel");
            wb.write(output);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //执行导出结束
    }

    @Override
    public JsonData verifyCard(PrePaymentParam param) {
        int result = userCardMapper.getCardByCardMessage(null,param.getIccardId(),param.getIccardIdentifier());
        if(result == 0){
            return JsonData.fail("IC卡识别号与IC卡号与数据库绑定的不一致");
        }
        return JsonData.success();
    }
}
