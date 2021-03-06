package com.gjing.projects.excel.demo.config.excel.write;

import cn.gjing.tools.excel.metadata.ExcelColor;
import cn.gjing.tools.excel.metadata.ExcelFieldProperty;
import cn.gjing.tools.excel.metadata.aware.ExcelWorkbookAware;
import cn.gjing.tools.excel.write.BigTitle;
import cn.gjing.tools.excel.write.listener.ExcelStyleWriteListener;
import org.apache.poi.ss.usermodel.*;

/**
 * 自定义样式监听器，应用场景主要是看我默认给的样式不爽，觉得太丑，哈哈哈~~~
 * <p>
 * 这里我们给表头、正文都设置一个自己想要的样式
 * <p>
 * 注意：这里只是演示，具体的你们要自己根据需要来哦。
 * <p>
 * 提示：这里给表头和正文都只创建了一个样式，你们可以在实际使用的时候给每一列单独创建一个样式，这样可以更好的实现多姿多彩
 *
 * @author Gjing
 **/
public class MyStyleListener implements ExcelStyleWriteListener, ExcelWorkbookAware {
    private Workbook workbook;
    private CellStyle headStyle;
    private CellStyle bodyStyle;

    @Override
    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public void setTitleStyle(BigTitle bigTitle, Cell cell) {
        // 这里不演示创建大标题的样式啦，跟表头和正文都是一样的
    }

    @Override
    public void setHeadStyle(Row row, Cell cell, ExcelFieldProperty excelFieldProperty, int index, int colIndex) {
        if (this.headStyle == null) {
            this.headStyle = this.workbook.createCellStyle();
            // 设置背景颜色为天蓝色
            this.headStyle.setFillForegroundColor(ExcelColor.SKY_BLUE.index);
            this.headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // 设置字体上下左右居中，超出单元格宽度自动换行
            this.headStyle.setAlignment(HorizontalAlignment.CENTER);
            this.headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            this.headStyle.setWrapText(true);
        }
        // 给当前单元格增加上样式
        cell.setCellStyle(this.headStyle);
    }

    @Override
    public void setBodyStyle(Row row, Cell cell, ExcelFieldProperty excelFieldProperty, int index, int colIndex) {
        if (this.bodyStyle == null) {
            this.bodyStyle = this.workbook.createCellStyle();
            // 设置字体上下左右居中，超出单元格宽度自动换行
            this.bodyStyle.setAlignment(HorizontalAlignment.CENTER);
            this.bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            this.bodyStyle.setWrapText(true);
        }
        // 给当前单元格增加上样式
        cell.setCellStyle(this.bodyStyle);
    }
}
