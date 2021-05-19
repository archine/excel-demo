package com.gjing.projects.excel.demo.config.excel.read;

import cn.gjing.tools.excel.ExcelField;
import cn.gjing.tools.excel.read.listener.ExcelEmptyReadListener;
import com.gjing.projects.excel.demo.entity.write.Book;

import java.lang.reflect.Field;

/**
 * 空值监听器，导入的时候如果用户未在设置了{@link ExcelField#required()}为true的表头下方的单元格填写内容,
 * 那么会触发该空值监听器
 *
 * @author Gjing
 **/
public class EmptyListener implements ExcelEmptyReadListener<Book> {
    @Override
    public boolean readEmpty(Book book, Field field, int rowIndex, int colIndex) {
        // 返回false表示当前这一行不在进行读了，可以开始读下一行了。当然你也可以直接在这里抛出一个你们的业务异常，因为必填项未填
        return false;
    }
}
