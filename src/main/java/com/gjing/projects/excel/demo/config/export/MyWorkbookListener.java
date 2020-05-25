package com.gjing.projects.excel.demo.config.export;

import cn.gjing.tools.excel.write.ExcelWriterContext;
import cn.gjing.tools.excel.write.listener.ExcelWorkbookWriteListener;
import org.apache.poi.ss.usermodel.*;

/**
 * Excel下载到浏览器之前，我进行对年龄求和
 *
 * @author Gjing
 **/
public class MyWorkbookListener implements ExcelWorkbookWriteListener {
    @Override
    public void flushBefore(ExcelWriterContext excelWriterContext) {
        Sheet sheet1 = excelWriterContext.getSheet();
//        String sumFormula = ExcelUtils.createSumFormula(1, 1, 1, sheet1.getLastRowNum());
        Row row = sheet1.createRow(sheet1.getLastRowNum() + 1);
        Cell cell = row.createCell(0);
        CellStyle cellStyle = excelWriterContext.getWorkbook().createCellStyle();
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillBackgroundColor(IndexedColors.PINK.getIndex());;//设置单元格背景色为骚粉
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cell.setCellValue("啦啦啦");
        cell.setCellStyle(cellStyle);
//        row.createCell(1).setCellFormula(sumFormula);
    }
}
