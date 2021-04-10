package com.gjing.projects.excel.demo.entity.write;

import cn.gjing.tools.excel.Excel;
import cn.gjing.tools.excel.ExcelField;
import cn.gjing.tools.excel.metadata.ExcelType;
import com.gjing.projects.excel.demo.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 单级表头实体
 *
 * @author Gjing
 **/
@Data
@Excel(value = "书籍",type = ExcelType.XLS)
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    // 设置该表头字段 required属性为true，意味着名称为必填
    @ExcelField(value = "名称",required = true)
    private String bookName;

    @ExcelField(value = "价格", format = "0.00")
    private BigDecimal price;

    @ExcelField("图书性别")
    private Gender gender;
    /**
     * 获取模拟数据
     *
     * @return List<Book>
     */
    public static List<Book> getData() {
        List<Book> books = new ArrayList<>();
        Book book1 = new Book("童话世界", new BigDecimal("20.2"), Gender.MAN);
        Book book2 = new Book("脑筋急转弯", new BigDecimal("33.4"), Gender.WO_MAN);
        Book book3 = new Book("鲁滨逊漂流记", new BigDecimal("18.8"), Gender.MAN);
        books.add(book1);
        books.add(book2);
        books.add(book3);
        return books;
    }
}
