package com.gjing.projects.excel.demo.entity.write;

import cn.gjing.tools.excel.Excel;
import cn.gjing.tools.excel.ExcelField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 单级表头实体2
 * 用于测试将不同类型数据导出到一个excel文件中的不同sheet
 * @author Gjing
 **/
@Data
@Excel("饮料")
@NoArgsConstructor
@AllArgsConstructor
public class Water {
    @ExcelField("名称")
    private String bookName;

    @ExcelField(value = "价格", format = "0.00")
    private BigDecimal price;

    /**
     * 获取模拟数据
     *
     * @return List<Book>
     */
    public static List<Water> getData() {
        List<Water> waters = new ArrayList<>();
        Water water1 = new Water("可乐", new BigDecimal("3"));
        Water water2 = new Water("雪碧", new BigDecimal("2.5"));
        waters.add(water1);
        waters.add(water2);
        return waters;
    }
}
