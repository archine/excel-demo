package com.gjing.projects.excel.demo.config.excel.read;

import cn.gjing.tools.excel.metadata.RowType;
import cn.gjing.tools.excel.read.listener.ExcelResultReadListener;
import cn.gjing.tools.excel.read.listener.ExcelRowReadListener;
import cn.gjing.tools.excel.read.resolver.ExcelBindReader;
import com.gjing.projects.excel.demo.entity.read.EmissionFactor;
import com.gjing.projects.excel.demo.entity.read.ExcelEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel绑定模式导入行监听器
 * <p>
 * 这里演示的场景为一个Excel文件，有三列表头，第二、第三列表头的数据是动态的，也就是说数据行数不是固定的，第一列表头的数据行数是根据后面两列表头的数行数据而定的
 * <p>
 * 提示: 如果导入的Excel文件行数很大，意味着会生成很多的Java对象，建议不要在调用导出方法时通过
 * 结果订阅器 {@link ExcelBindReader#subscribe(ExcelResultReadListener)}进行数据插入数据库，这样的话，每一行的对象都会
 * 缓存起来，直到读取完毕所有行，这时就可能出现内存溢出了。建议通过该监听器进行数据插入数据库，在监听器定义一个数据集合，每一次read()方法触发都将数据添加
 * 到我们定义的数据集合中，你可以在read()方法中判断集合的大小，超过多少就先进行数据库插入，然后清空集合用来缓存新读取的数据
 *
 * @author Gjing
 **/
public class RowReaderListener implements ExcelRowReadListener<ExcelEntity> {
    // 导入成功的excel数据
    private final List<ExcelEntity> data;
    // 每一行结束后记录下这行生成的对象
    private ExcelEntity excelEntity;
    // 因子列表
    private List<EmissionFactor> factorList;
    // 当前行生成的因子
    private EmissionFactor emissionFactor;

    public RowReaderListener() {
        this.data = new ArrayList<>();
    }

    /**
     * 读完一行时触发
     *
     * @param excelEntity 当前行生成的实体对象， 只在行类型为body时才有值，其他类型为null
     * @param row         当前行
     * @param rowIndex    当前行下标
     * @param rowType     行类型
     */
    @Override
    public void readRow(ExcelEntity excelEntity, Row row, int rowIndex, RowType rowType) {
        if (rowType == RowType.BODY) {
            // 如果全局的excelEntity对象为null，说明是数据的第一行，直接赋值即可
            if (this.excelEntity == null) {
                this.excelEntity = excelEntity;
                this.factorList = new ArrayList<>();
                this.factorList.add(this.emissionFactor);
                return;
            }
            // 如果取到的省份是null或者和上一行的省份一致，说明还是属于同一个省，就将当前行生成的因子加入到因子列表里
            if (excelEntity.getProvince() == null || this.excelEntity.getProvince().equals(excelEntity.getProvince())) {
                this.factorList.add(this.emissionFactor);
                return;
            }
            if (!this.excelEntity.getProvince().equals(excelEntity.getProvince())) {
                // 当前行生成的对象和之前的不一致了，说明换成其他省份了，那这里就要将以前行生成的数据添加到data里了
                this.excelEntity.setFactors(this.factorList);
                this.data.add(this.excelEntity);
                // 初始化因子列表和替换
                this.factorList = new ArrayList<>();
                this.factorList.add(this.emissionFactor);
                this.excelEntity = excelEntity;

            }
        }
    }

    /**
     * 读取成功每一个单元格都会触发
     *
     * @param cellValue 读到的单元格内容
     * @param cell      当前单元格
     * @param rowIndex  当前单元格所在的行 下标
     * @param colIndex  当前单元格的列下标
     * @param rowType   行类型
     * @return 单元格内容，如果不更改，直接返回参数中的 cellValue
     */
    @Override
    public Object readCell(Object cellValue, Cell cell, int rowIndex, int colIndex, RowType rowType) {
        if (rowType == RowType.BODY) {
            // 判断当前单元格所对应的字段是不是我要的
            if (colIndex == 1) {
                emissionFactor = new EmissionFactor();
                emissionFactor.setFactor((String) cellValue);
                return cellValue;
            }
            if (colIndex == 2) {
                emissionFactor.setMmol((BigDecimal) cellValue);
                return cellValue;
            }
        }
        return cellValue;
    }

    /**
     * 该方法会在导入的Excel文件所有数据读取完毕后触发，意味着导入结束了
     */
    @Override
    public void readFinish() {
        // 将后面的数据直接加入到数据
        this.excelEntity.setFactors(this.factorList);
        this.data.add(this.excelEntity);

        // 这里可以用来插入数据库
        System.out.println("读取到的所有数据:" + this.data);
    }

    /**
     * 该方法会在开始读取excel前进行触发
     */
    @Override
    public void readBefore() {
        System.out.println("准备开始读取excel..");
    }

    /**
     * 该方法会在每一行读取结束后，也就是 {@link #readRow}执行完毕后触发.
     * 一般不需要实现，除非有需要在执行到某一行不满足条件时退出，可以定义一个全局的bool变量，这里直接return这个变量,
     * 然后通过在 {@link #readRow} 方法进行控制这个变量
     *
     * @return True 会继续读取下一行，false会结束读取，直接触发{@link #readFinish()}
     */
    @Override
    public boolean continueRead() {
        return true;
    }
}
