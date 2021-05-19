package com.gjing.projects.excel.demo.entity.write;

import cn.gjing.tools.excel.Excel;
import cn.gjing.tools.excel.ExcelField;
import cn.gjing.tools.excel.metadata.ExcelType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 多级表头实体
 * 数组的每一个下标都为一级表头，数组中的最后一个元素为真实表头
 *
 * 注意: Excel实体中所有Excel列的表头级数必须一致
 *
 * @author Gjing
 **/
@Data
@Excel(value = "旅行社出游情况",type = ExcelType.XLS)
@NoArgsConstructor
@AllArgsConstructor
public class Multi {
    @ExcelField(value = {"旅行社", "旅行社", "旅行社"})
    private String tour;

    @ExcelField(value = {"国内", "门票消费金额", "门票消费金额"}, format = "0.00")
    private BigDecimal inlandMoney;

    @ExcelField({"国内", "A类(张)", "全票"})
    private Integer ticketA;

    @ExcelField({"国内", "A类(张)", "半票"})
    private Integer ticketAA;

    @ExcelField({"国内", "B类(张)", "全票"})
    private Integer ticketB;

    @ExcelField({"国内", "B类(张)", "半票"})
    private Integer ticketBB;

    @ExcelField(value = {"境外", "门票消费金额", "门票消费金额"}, format = "0.00")
    private BigDecimal foreignMoney;

    @ExcelField({"境外", "C类(张)", "全票"})
    private Integer ticketC;

    @ExcelField({"境外", "C类(张)", "半票"})
    private Integer ticketCC;

    @ExcelField({"境外", "D类(张)", "全票"})
    private Integer ticketD;

    @ExcelField({"境外", "D类(张)", "半票"})
    private Integer ticketDD;

    /**
     * 获取模拟数据
     *
     * @return List<Book>
     */
    public static List<Multi> getData() {
        List<Multi> multiList = new ArrayList<>();
        multiList.add(new Multi("团队A", new BigDecimal("7000.98"), 12, 22, 11, 33, new BigDecimal("10000.99"), 22, 23, 12, 5));
        multiList.add(new Multi("团队B", new BigDecimal("6288"), 7, 16, 21, 9, new BigDecimal("2789"), 9, 10, 4, 5));
        return multiList;
    }
}
