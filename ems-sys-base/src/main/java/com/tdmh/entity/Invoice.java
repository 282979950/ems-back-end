package com.tdmh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Liuxia on 2018/10/18.
 */

@Getter
@Setter
public class Invoice extends BaseEntity{
    /**
     * 发票id
     */
    private Integer invoiceId;

    /**
     * 发票代码
     */
    private String invoiceCode;

    /**
     * 发票号码
     */
    private String invoiceNumber;

    /**
     * 发票状态(1已生成未分配，2已分配未打印，3已打印，4已报废)
     */
    private Integer invoiceStatus;

    private String invoiceStatusName;

    /**
     * 发票生成时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date invoiceGenerateTime;

    /**
     * 发票分配时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date invoiceAssignTime;

    /**
     * 发票打印时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date invoicePrintTime;

    /**
     * 发票报废时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date invoiceCancelTime;

    /**
     * 发票所属员工ID
     */
    private Integer empId;

    private String empName;

    private String createByName;

    public Invoice(Integer invoiceId, String invoiceCode, String invoiceNumber, Integer invoiceStatus, Date invoiceGenerateTime, Date invoiceAssignTime, Date invoicePrintTime, Date invoiceCancelTime, Integer empId, Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.invoiceId = invoiceId;
        this.invoiceCode = invoiceCode;
        this.invoiceNumber = invoiceNumber;
        this.invoiceStatus = invoiceStatus;
        this.invoiceGenerateTime = invoiceGenerateTime;
        this.invoiceAssignTime = invoiceAssignTime;
        this.invoicePrintTime = invoicePrintTime;
        this.invoiceCancelTime = invoiceCancelTime;
        this.empId = empId;
    }

    public Invoice() {
    }
}
