package com.gjing.projects.excel.demo.config.excel.write;

import cn.gjing.tools.excel.write.callback.ExcelAutoMergeCallback;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 无绑定模式导出的回调类
 *
 * @author Gjing
 **/
public class SimpleWriteMergeCallback implements ExcelAutoMergeCallback<List<Object>> {
    @Override
    public boolean mergeY(List<Object> objects, Field field, int colIndex, int index) {
        // 第三条数据开始才需要合并，因为index是从0开始计算的，所以这里是大于1
        return index > 1;
    }
}
