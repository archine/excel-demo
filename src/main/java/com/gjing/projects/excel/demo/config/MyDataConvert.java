package com.gjing.projects.excel.demo.config;

import cn.gjing.tools.excel.convert.DataConvert;
import com.gjing.projects.excel.demo.entity.SingleHead;
import com.gjing.projects.excel.demo.enums.Gender;

import java.lang.reflect.Field;

/**
 * @author Gjing
 **/
public class MyDataConvert implements DataConvert<SingleHead> {

    @Override
    public Object toEntityAttribute(Object o, Field field) {
        return Gender.of(o.toString());
    }

    @Override
    public Object toExcelAttribute(SingleHead singleHead, Object value, Field field) {
        return (int) value * 10;
    }
}
