package com.gjing.projects.excel.demo.config.export;

import cn.gjing.tools.excel.write.callback.ExcelAutoMergeCallback;
import com.gjing.projects.excel.demo.entity.SingleHead;

import java.lang.reflect.Field;

/**
 * @author Gjing
 **/
public class SingleHeadCallback implements ExcelAutoMergeCallback<SingleHead> {
    @Override
    public boolean mergeY(SingleHead singleHead, Field field, String key, int colIndex, int index) {
        boolean flag;
        if (index == 0) {
            flag = true;
        } else {
            flag = singleHead.getUserAge() == 0;
        }
        return flag;
    }
}
