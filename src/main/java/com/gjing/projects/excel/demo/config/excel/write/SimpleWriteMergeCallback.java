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
    public boolean mergeY(List<Object> objects, Field field, String headerName, int colIndex, int index) {
        // 我们这里对第三行数据开始才需要进行相同内容合并, index为数据索引，0开始
        return index > 1;
    }
}
