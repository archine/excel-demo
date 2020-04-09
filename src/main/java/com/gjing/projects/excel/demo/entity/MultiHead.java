package com.gjing.projects.excel.demo.entity;

import cn.gjing.tools.excel.Excel;
import cn.gjing.tools.excel.ExcelField;
import cn.gjing.tools.excel.metadata.ExcelType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Gjing
 **/
@Data
@Excel(value = "导出多级表头",type = ExcelType.XLSX)
public class MultiHead {
    @ExcelField({"用户名","用户名"})
    private String userName;

    @ExcelField({"年龄","年龄"})
    private Integer age;

    @ExcelField({"形态","身高"})
    private BigDecimal height;

    @ExcelField({"形态","体重"})
    private BigDecimal weight;
}
