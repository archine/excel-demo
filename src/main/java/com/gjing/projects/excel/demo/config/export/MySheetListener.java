package com.gjing.projects.excel.demo.config.export;

import cn.gjing.tools.excel.write.ExcelWriterContext;
import cn.gjing.tools.excel.write.listener.ExcelSheetWriteListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义sheet监听器
 *
 * @author Gjing
 **/
@Slf4j
public class MySheetListener implements ExcelSheetWriteListener {
    @Override
    public void completeSheet(ExcelWriterContext excelWriterContext) {
        log.info("--------{}---------", "Sheet创建完毕");
    }
}
