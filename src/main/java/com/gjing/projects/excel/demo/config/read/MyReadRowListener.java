package com.gjing.projects.excel.demo.config.read;

import cn.gjing.tools.excel.read.ExcelReaderContext;
import cn.gjing.tools.excel.read.listener.ExcelRowReadListener;
import com.gjing.projects.excel.demo.entity.SingleHead;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 自定义行读取监听器
 * @author Gjing
 **/
public class MyReadRowListener implements ExcelRowReadListener<SingleHead> {
    @Override
    public boolean readRow(SingleHead singleHead, List<String> headNames, int rowIndex, boolean isHead, boolean hasNext) {
        System.out.println("读完一行");
        return false;
    }

    @Override
    public Object readCell(Object cellValue, Field field, int rowIndex, int colIndex, boolean isHead) {
        System.out.println("读到了一格，并进行完了数据校验");
        return cellValue;
    }

    @Override
    public void readFinish(ExcelReaderContext<SingleHead> context) {
        System.out.println("数据全部导入完毕了");
    }
}
