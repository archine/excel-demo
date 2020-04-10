package com.gjing.projects.excel.demo.config;

import cn.gjing.tools.excel.ExcelField;
import cn.gjing.tools.excel.write.style.ExcelStyleWriteListener;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Field;

/**
 * 自定义样式监听器
 * @author Gjing
 **/
public class MyStyleListener implements ExcelStyleWriteListener {
    private CellStyle cellStyle;
    private Workbook workbook;

    @Override
    public void init(Workbook workbook) {
        this.workbook = workbook;
        this.cellStyle = workbook.createCellStyle();
        //设置居中
        this.cellStyle.setAlignment(HorizontalAlignment.CENTER);
        this.cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    }

    @Override
    public void setTitleStyle(Cell cell) {
        CellStyle titleStyle = this.workbook.createCellStyle();
        titleStyle.setFillBackgroundColor(IndexedColors.LIGHT_YELLOW.index);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(titleStyle);
    }

    @Override
    public void setHeadStyle(Row row, Cell cell, ExcelField excelField, Field field, String s, int i, int i1) {
        cell.setCellStyle(this.cellStyle);
    }

    @Override
    public void setBodyStyle(Row row, Cell cell, ExcelField excelField, Field field, String s, int i, int i1) {

    }

    @Override
    public void completeCell(Sheet sheet, Row row, Cell cell, ExcelField excelField, Field field, String s, int i, int i1, boolean isHead, Object o) {
        //如果是表头我就设置每列的样式和宽度
        if (isHead) {
            sheet.setColumnWidth(i1, excelField.width());
            this.setHeadStyle(row, cell, excelField, field, s, i, i1);
        }
    }

    @Override
    public void completeRow(Sheet sheet, Row row, int i, boolean isHead) {

    }
}
