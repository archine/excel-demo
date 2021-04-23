package com.gjing.projects.excel.demo.entity.write;

import cn.gjing.tools.excel.Excel;
import cn.gjing.tools.excel.ExcelField;
import cn.gjing.tools.excel.metadata.ExcelType;
import cn.gjing.tools.excel.read.valid.ExcelAssert;
import cn.gjing.tools.excel.write.valid.*;
import com.gjing.projects.excel.demo.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

import static cn.gjing.tools.excel.write.valid.OperatorType.LESS_THAN;
import static cn.gjing.tools.excel.write.valid.ValidType.TEXT_LENGTH;

/**
 * 单级表头实体，添加校验注解
 *
 * @author Gjing
 **/
@Data
@Excel(value = "书籍", type = ExcelType.XLS)
@NoArgsConstructor
@AllArgsConstructor
public class BookValid {
    /**
     * 给这一列增加数字文本校验，表示这一列的每个单元格输入的内容必须满足我的校验规则，否则Excel文件会弹框提醒.
     */
    @ExcelNumericValid(operatorType = LESS_THAN, validType = TEXT_LENGTH, expr1 = "10", errorContent = "书籍名称不能超过10位")
    @ExcelField("名称")
    private String bookName;

    /**
     * 给这一列增加数据重复校验，表示这一列的每个单元格内容不允许重复，一旦出现重复Excel文件会弹框提醒
     */
    @ExcelField(value = "价格", format = "0.00")
    @ExcelRepeatValid(errorContent = "不允许输入同样的价格")
    // 添加导入的数据断言，输入的价格必须要大于0，否则数据没达到要求，抛出断言异常
    @ExcelAssert(expr = "#price.doubleValue()>0", message = "价格必须大于0")
    private BigDecimal price;

    /**
     * 给这一列增加下拉框，表示这一列只能选择或者输入我下拉框中的选项，如果输入其他内容，Excel文件会弹框提醒
     */
    @ExcelField("图书性别")
    @ExcelDropdownBox(combobox = {"男", "女"})
    private Gender gender;

    /**
     * 给这一列增加上时间校验，当用户输入的时间范围不在指定的范围内，Excel文件会弹框提醒
     */
    @ExcelDateValid(operatorType = OperatorType.BETWEEN, expr1 = "1980-01-01", expr2 = "2100-01-01", errorContent = "出版日期只允许时间范围在1980-01-01到2100-01-01之间")
    @ExcelField(value = "出版日期", format = "yyyy-MM-dd")
    private Date createDate;
}
