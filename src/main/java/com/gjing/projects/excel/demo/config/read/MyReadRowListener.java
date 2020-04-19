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
    public boolean readRow(SingleHead singleHead, List<Object> otherValues, int rowIndex, boolean isHead, boolean isBody) {
        //为true时会读取结束
        return false;
    }

    @Override
    public Object readCell(Object cellValue, Field field, int rowIndex, int colIndex, boolean isHead, boolean isBody) {
        //每一个单元格读取成功触发该方法
        return cellValue;
    }

    @Override
    public void readFinish(ExcelReaderContext<SingleHead> context) {
        //全部读完时触发该方法
    }
}
