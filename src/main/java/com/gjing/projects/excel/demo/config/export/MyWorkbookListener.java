package com.gjing.projects.excel.demo.config.export;

import cn.gjing.tools.excel.util.ExcelUtils;
import cn.gjing.tools.excel.write.ExcelWriterContext;
import cn.gjing.tools.excel.write.listener.ExcelWorkbookWriteListener;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Excel下载到浏览器之前，我进行对年龄求和
 *
 * @author Gjing
 **/
public class MyWorkbookListener implements ExcelWorkbookWriteListener {
    @Override
    public void flushBefore(ExcelWriterContext excelWriterContext) {
        Sheet sheet1 = excelWriterContext.getSheet();
        String sumFormula = ExcelUtils.createSumFormula(1, 1, 1, sheet1.getLastRowNum());
        Row row = sheet1.createRow(sheet1.getLastRowNum() + 1);
        row.createCell(1).setCellFormula(sumFormula);
    }
}
