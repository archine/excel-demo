package com.gjing.projects.excel.demo.entity.write;

import cn.gjing.tools.excel.Excel;
import cn.gjing.tools.excel.ExcelField;
import cn.gjing.tools.excel.write.valid.ExcelDropdownBox;
import lombok.Data;

/**
 * 单级表头级联下拉框实体
 *
 * @author Gjing
 **/
@Data
@Excel("级联下拉框")
public class BookCascade {
    /**
     * 父列可以是一个下拉框，也可以让用户输入
     * 这里我们演示作为一个下拉框
     */
    @ExcelDropdownBox(combobox = {"男", "女"})
    @ExcelField("a列")
    private String a;

    /**
     * Excel列默认根据实体字段出现的顺序进行排序，如果当前Excel实体继承了父类，那么父类的Excel列会追加到当前Excel实体的列后面。
     * 如果设置了order参数，那么会按从小到大的顺序进行排序，我们这里都没有设置{@link ExcelField#order()}参数，
     * 所以走的是字段出现顺序，那么 a 列就是第一列了，由于Excel列下标是从0开始计算，所以 link 的父级列索引为0
     */
    @ExcelField("b列")
    @ExcelDropdownBox(link = "0")
    private String b;
}
