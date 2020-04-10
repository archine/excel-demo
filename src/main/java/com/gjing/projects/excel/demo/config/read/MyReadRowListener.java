package com.gjing.projects.excel.demo.config.read;

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
    public void readCell(SingleHead singleHead, Object cellValue, Field field, int rowIndex, int colIndex, boolean isHead) {
        System.out.println("读完一格");
    }
}
