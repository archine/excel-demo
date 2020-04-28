package com.gjing.projects.excel.demo.config.read;

import cn.gjing.tools.excel.read.ExcelReaderContext;
import cn.gjing.tools.excel.read.listener.ExcelRowReadListener;
import com.gjing.projects.excel.demo.entity.SingleHead;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 自定义行读取监听器
 * @author Gjing
 **/
@Slf4j
public class MyReadRowListener implements ExcelRowReadListener<SingleHead> {
    @Override
    public boolean readRow(SingleHead singleHead, List<Object> otherValues, int rowIndex, boolean isHead, boolean isBody) {
        //为true时会读取结束
        if (isBody) {
            log.info("------读取到行{}-------", rowIndex);
        }
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
        log.info("------触发导入结束------");
    }

    @Override
    public void readBefore(ExcelReaderContext<SingleHead> context) {
        log.info("------导入即将开始-------");
    }
}
