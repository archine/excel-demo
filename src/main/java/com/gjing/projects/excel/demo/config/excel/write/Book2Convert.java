package com.gjing.projects.excel.demo.config.excel.write;

import cn.gjing.tools.excel.convert.DataConvert;
import com.gjing.projects.excel.demo.entity.write.Book2;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * 实现DataConvert接口，泛型为Excel实体
 *
 * 注意：同一个转换器对象，在一次完整的ExcelFactory.xx().flush()链路中 只会实例化一次，转换器不存在的时候才会创建，存在的话会复用
 *
 * @author Gjing
 **/
public class Book2Convert implements DataConvert<Book2> {
    @Override
    public Object toEntityAttribute(Object o, Field field) {
        // 将单元格里的价钱都乘以10
        return new BigDecimal(o.toString()).multiply(BigDecimal.valueOf(10));
    }

    @Override
    public Object toExcelAttribute(Book2 book2, Object o, Field field) {
        // 将所有书籍的价格都改为8.8
        return 8.8;
    }
}
