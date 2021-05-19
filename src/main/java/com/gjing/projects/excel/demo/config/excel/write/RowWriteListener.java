package com.gjing.projects.excel.demo.config.excel.write;

import cn.gjing.tools.excel.metadata.RowType;
import cn.gjing.tools.excel.write.listener.ExcelRowWriteListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 行写出监听器
 * <p>
 * 这里的案例场景为，当这一行是表头时，往后在追加一个自定义表头
 *
 * @author Gjing
 **/
@Slf4j
public class RowWriteListener implements ExcelRowWriteListener {
    @Override
    public void completeRow(Sheet sheet, Row row, Object o, int index, RowType rowType) {

        log.info("当前行类型: {}, 当前写完第{}条的数据", rowType.name(), index);
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

    @Override
    public void createBefore(Sheet sheet, int index, RowType rowType) {
        log.info("当前行类型: {}, 当前即将为第{}条数据创建行", rowType.name(), index);
    }
}
