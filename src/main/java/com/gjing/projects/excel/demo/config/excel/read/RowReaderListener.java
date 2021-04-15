package com.gjing.projects.excel.demo.config.excel.read;

import cn.gjing.tools.excel.metadata.RowType;
import cn.gjing.tools.excel.read.listener.ExcelRowReadListener;
import com.gjing.projects.excel.demo.entity.read.EmissionFactor;
import com.gjing.projects.excel.demo.entity.read.ExcelEntity;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel导入 行监听器
 * <p>
 * 这里演示的场景为一个Excel文件，有三列表头，第二、第三列表头的数据是动态的，也就是说数据行数不是固定的，第一列表头的数据行数是根据后面两列表头的数行数据而定的
 *
 * 提示: 如果导入的Excel文件行数很大，意味着会生成很多的Java对象，建议不要在调用导出方法时通过结果订阅器进行数据插入数据库，这样的话，每一行的对象都会
 *      缓存起来，直到读取完毕所有行，这时就可能出现内存溢出了。建议通过该监听器进行数据插入数据库，在监听器定义一个数据集合，每一次read()方法触发都将数据添加
 *      到我们定义的数据集合中，你可以在read()方法中判断集合的大小，超过多少就先进行数据库插入，然后清空集合用来缓存新读取的数据
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
     * 该方法会在读取成功每一行后触发，意味着这一行的所有单元格都读取完毕了(不读取映射实体的所有表头外的列)
     */
    @Override
    public boolean readRow(ExcelEntity excelEntity, List<?> otherValues, int rowIndex, RowType rowType) {
        if (rowType == RowType.BODY) {
            // 如果全局的excelEntity对象为null，说明是数据的第一行，直接赋值即可
            if (this.excelEntity == null) {
                this.excelEntity = excelEntity;
                this.factorList = new ArrayList<>();
                this.factorList.add(this.emissionFactor);
                return false;
            }
            // 如果取到的省份是null或者和上一行的省份一致，说明还是属于同一个省，就将当前行生成的因子加入到因子列表里
            if (excelEntity.getProvince() == null || this.excelEntity.getProvince().equals(excelEntity.getProvince())) {
                this.factorList.add(this.emissionFactor);
                return false;
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
        // 返回true的话会停止继续读取下一行，意味着导入到这一行就结束了
        return false;
    }

    /**
     * 该方法在读取成功每一个单元格触发，返回值为单元格内容，意味着该方法也可以实现数据转换器的功能对数据进行转换。
     */
    @Override
    public Object readCell(Object cellValue, Field field, int rowIndex, int colIndex, RowType rowType) {
        if (rowType == RowType.BODY) {
            // 判断当前单元格所对应的字段是不是我要的
            if ("factor".equals(field.getName())) {
                emissionFactor = new EmissionFactor();
                emissionFactor.setFactor((String) cellValue);
                return cellValue;
            }
            if ("mmol".equals(field.getName())) {
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
}
