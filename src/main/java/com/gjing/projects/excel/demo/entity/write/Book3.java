package com.gjing.projects.excel.demo.entity.write;

import cn.gjing.tools.excel.Excel;
import cn.gjing.tools.excel.ExcelField;
import cn.gjing.tools.excel.write.merge.Merge;
import com.gjing.projects.excel.demo.config.excel.write.Book3AutoMergeCallback;
import com.gjing.projects.excel.demo.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 单级表头实体, 自动纵向合并
 *
 * @author Gjing
 **/
@Data
@Excel("书籍")
@NoArgsConstructor
@AllArgsConstructor
public class Book3 {
    @ExcelField(value = "名称",autoMerge = @Merge(enable = true))
    private String bookName;

    /**
     * 通过autoMerge参数为该Excel列开启自动合并，默认策略为只要值相同就合并
     */
    @ExcelField(value = "价格", format = "0.00", autoMerge = @Merge(enable = true))
    private BigDecimal price;

    /**
     * 更改回调策略，根据业务判断是否合并
     */
    @ExcelField(value = "图书性别", autoMerge = @Merge(enable = true, callback = Book3AutoMergeCallback.class))
    private Gender gender;

    /**
     * 获取模拟数据
     *
     * @return List<Book>
     */
    public static List<Book3> getData() {
        List<Book3> books = new ArrayList<>();
        Book3 book1 = new Book3("童话世界", new BigDecimal("20.2"), Gender.MAN);
        Book3 book2 = new Book3("脑筋急转弯", new BigDecimal("20.22"), Gender.WO_MAN);
        Book3 book3 = new Book3("鲁滨逊漂流记", new BigDecimal("18.8"), Gender.WO_MAN);
        Book3 book4 = new Book3("小鸭子的故事", new BigDecimal("18.8"), Gender.WO_MAN);
        Book3 book5 = new Book3("小鸭子的故事", new BigDecimal("21.2"), Gender.WO_MAN);
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        return books;
    }
}
