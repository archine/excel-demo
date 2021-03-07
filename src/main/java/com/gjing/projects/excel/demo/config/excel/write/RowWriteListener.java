package com.gjing.projects.excel.demo.config.excel.write;

import cn.gjing.tools.excel.metadata.RowType;
import cn.gjing.tools.excel.write.listener.ExcelRowWriteListener;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 行写出监听器
 *
 * 这里的案例场景为，当这一行是表头时，往后在追加一个自定义表头
 * @author Gjing
 **/
public class RowWriteListener implements ExcelRowWriteListener {
    @Override
    public void completeRow(Sheet sheet, Row row, Object o, int index, RowType rowType) {
        if (rowType == RowType.HEAD) {
            // 往后追加创建一个单元格
            Cell addCell = row.createCell(row.getLastCellNum());
            // 设置新创建的这个单元格所在列的宽度
            sheet.setColumnWidth(addCell.getColumnIndex(), 5120);
            // 设置单元格内容
            addCell.setCellValue("自定义表头");
            // 该单元格沿用前面的单元格样式
            addCell.setCellStyle(row.getCell(0).getCellStyle());
        }
    }
}
