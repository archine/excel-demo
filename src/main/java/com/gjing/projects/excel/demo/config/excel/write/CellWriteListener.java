package com.gjing.projects.excel.demo.config.excel.write;

import cn.gjing.tools.excel.ExcelField;
import cn.gjing.tools.excel.metadata.RowType;
import cn.gjing.tools.excel.util.ExcelUtils;
import cn.gjing.tools.excel.write.listener.ExcelCellWriteListener;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.lang.reflect.Field;

/**
 * 单元格写出监听器
 * <p>
 * 这里模拟业务场景，每一个单元格写出完成后，都往下合并一行
 *
 * @author Gjing
 **/
public class CellWriteListener implements ExcelCellWriteListener {
    @Override
    public void completeCell(Sheet sheet, Row row, Cell cell, ExcelField excelField, Field field, int index, int colIndex, RowType rowType) {
        // 只对正文数据行操作
        if (rowType == RowType.BODY) {
            // 如果是第一列，说明是新的一行数据，所以当前这一行下面是没有其他行了，所以我们这里要创建
            if (colIndex == 0) {
                Row newRow = sheet.createRow(sheet.getPhysicalNumberOfRows());
                ExcelUtils.merge(sheet, colIndex, colIndex, row.getRowNum(), newRow.getRowNum());
                return;
            }
            // 不是第一列的话就直接获取下面的一行就好了
            Row newRow = sheet.getRow(sheet.getLastRowNum());
            ExcelUtils.merge(sheet, colIndex, colIndex, row.getRowNum(), newRow.getRowNum());
        }
    }

    @Override
    public Object assignmentBefore(Sheet sheet, Row row, Cell cell, ExcelField excelField, Field field, int index, int colIndex, RowType rowType, Object value) {
        // 这里是单元格赋值之前的操作，需要返回单元格内容，也就是说，这里是单元格赋值之前最后一次的数据操作，该方法触发在数据转换器之后
        // 注意：当你返回null时，那么将不会对此单元格进行赋值。这里就涉及到一个应用场景，当这个单元格的内容是个集合的时候，这时候就不要帮我自动赋值了，我需要自己对这个集合进行操作
        //      比如集合每一条数据都创建新的一行进行插入
        // 这里我们就直接返回值了， 不做其他操作了
        return value;
    }
}
