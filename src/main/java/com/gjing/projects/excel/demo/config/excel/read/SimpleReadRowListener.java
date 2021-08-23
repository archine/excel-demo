package com.gjing.projects.excel.demo.config.excel.read;

import cn.gjing.tools.excel.metadata.RowType;
import cn.gjing.tools.excel.read.listener.ExcelRowReadListener;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Excel简单类型的导入行监听器
 * <p>
 * 由于简单类型的导入，不会自动生成Java实体对象，因此需要我们自己手动在监听器中进行逻辑的编写
 * <p>
 * 提示: 如果导入的Excel文件行数很大，意味着会生成很多的Java对象，建议不要在调用导出方法时通过结果订阅器进行数据插入数据库，这样的话，每一行的对象都会
 * 缓存起来，直到读取完毕所有行，这时就可能出现内存溢出了。建议通过该监听器进行数据插入数据库，在监听器定义一个数据集合，每一次read()方法触发都将数据添加
 * 到我们定义的数据集合中，你可以在read()方法中判断集合的大小，超过多少就先进行数据库插入，然后清空集合用来缓存新读取的数据
 *
 * @author Gjing
 **/
public class SimpleReadRowListener implements ExcelRowReadListener<Object> {
    /**
     * 读完一行时触发
     * 我们这里就打印一些符号，用于标识这一行读完了
     *
     * @param o        由于不会自动生成Java实体对象，因此这个参数一直为null, 至于泛型就随便了，一般 object 即可
     * @param row      当前行
     * @param rowIndex 当前行下标
     * @param rowType  行类型
     */
    @Override
    public void readRow(Object o, Row row, int rowIndex, RowType rowType) {
        System.out.println("-----------------------------------------------------");
    }

    /**
     * 读取成功每一个单元格都会触发
     *
     * @param cellValue 读到的单元格内容
     * @param cell 当前单元格
     * @param rowIndex 当前单元格所在的行 下标
     * @param colIndex 当前单元格的列下标
     * @param rowType 行类型
     * @return 单元格内容，如果没有更改，直接返回参数中的 cellValue
     */
    @Override
    public Object readCell(Object cellValue, Cell cell, int rowIndex, int colIndex, RowType rowType) {
        System.out.println("读取到数据: " + cellValue);
        return cellValue;
    }

    @Override
    public void readFinish() {
        ExcelRowReadListener.super.readFinish();
    }

    @Override
    public boolean continueRead() {
        return false;
    }
}
