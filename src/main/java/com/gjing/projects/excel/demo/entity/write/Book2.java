package com.gjing.projects.excel.demo.entity.write;

import cn.gjing.tools.excel.Excel;
import cn.gjing.tools.excel.ExcelField;
import cn.gjing.tools.excel.convert.ExcelDataConvert;
import com.gjing.projects.excel.demo.config.excel.write.Book2Convert;
import com.gjing.projects.excel.demo.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 单级表头实体, 使用数据转换器
 *
 * @author Gjing
 **/
@Data
@Excel("书籍")
@NoArgsConstructor
@AllArgsConstructor
public class Book2 {
    @ExcelField("名称")
    private String bookName;

    /**
     * 使用转换器接口
     *
     * 通过convert参数设置数据转换器，将所有价格都改为8.8
     */
    @ExcelField(value = "价格", format = "0.00", convert = Book2Convert.class)
    private BigDecimal price;

    /**
     * 这里使用注解方式的数据转换器
     *
     * excel列是个枚举类型，所以这通过枚举进行数据转换
     */
    @ExcelField(value = "图书性别")
    @ExcelDataConvert(expr1 = "#gender.desc", expr2 = "T(com.gjing.projects.excel.demo.enums.Gender).of(#gender)")
    private Gender gender;

    /**
     * 获取模拟数据
     *
     * @return List<Book>
     */
    public static List<Book2> getData() {
        List<Book2> books = new ArrayList<>();
        Book2 book1 = new Book2("童话世界", new BigDecimal("20.2"), Gender.MAN);
        Book2 book2 = new Book2("脑筋急转弯", new BigDecimal("33.4"), Gender.WO_MAN);
        Book2 book3 = new Book2("鲁滨逊漂流记", new BigDecimal("18.8"), Gender.MAN);
        books.add(book1);
        books.add(book2);
        books.add(book3);
        return books;
    }
}
