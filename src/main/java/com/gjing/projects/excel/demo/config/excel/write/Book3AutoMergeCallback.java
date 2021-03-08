package com.gjing.projects.excel.demo.config.excel.write;

import cn.gjing.tools.excel.write.callback.ExcelAutoMergeCallback;
import com.gjing.projects.excel.demo.entity.write.Book3;

import java.lang.reflect.Field;

/**
 * 自动合并回调，泛型为映射实体
 *
 * @author Gjing
 **/
public class Book3AutoMergeCallback implements ExcelAutoMergeCallback<Book3> {

    @Override
    public boolean mergeY(Book3 book3, Field field, String headerName, int colIndex, int index) {
        // 只有书籍价格小于20才需要合并
        return book3.getPrice().doubleValue() < 20;
    }
}
