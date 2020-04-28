package com.gjing.projects.excel.demo.config.export;

import cn.gjing.tools.excel.write.ExcelWriterContext;
import cn.gjing.tools.excel.write.listener.ExcelSheetWriteListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 自定义sheet监听器
 *
 * @author Gjing
 **/
@Slf4j
public class MySheetListener implements ExcelSheetWriteListener {
    @Override
    public void completeSheet(Sheet sheet, ExcelWriterContext excelWriterContext) {
        log.info("--------{}---------", "Sheet创建完毕" + sheet.getSheetName());
    }
}
