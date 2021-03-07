package com.gjing.projects.excel.demo.entity.read;

import cn.gjing.tools.excel.Excel;
import cn.gjing.tools.excel.ExcelField;
import cn.gjing.tools.excel.metadata.ExcelType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Gjing
 **/
@ToString
@Getter
@Setter
@Excel(type = ExcelType.XLSX)
public class ExcelEntity {
    @ExcelField("省")
    private String province;

    @ExcelField("因子")
    private String factor;

    @ExcelField("浓度")
    private BigDecimal mmol;

    /**
     * 这里的数据我们等会通过监听器来生成
     */
    private List<EmissionFactor> factors;
}
