package com.gjing.projects.excel.demo.controller;

import cn.gjing.tools.excel.driven.ExcelWrite;
import cn.gjing.tools.excel.driven.ExcelWriteWrapper;
import cn.gjing.tools.excel.write.BigTitle;
import com.gjing.projects.excel.demo.entity.write.Book;
import com.gjing.projects.excel.demo.entity.write.Multi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Excel注解驱动模式导出控制器（需要Excel实体绑定）
 * <p>
 * 通过驱动模式进行导出，不需要再显性的通过ExcelFactory.xxx()调用方式来进行Excel文件导出,
 * 该模式只是更改了调用的方式，并没有改变原有的框架功能（如：功能性注解、监听器、回调器。。
 *
 * <p>
 * 返回值类型：
 * ---- List\<\?\> : 该集合为你设置的Excel实体数据集合，一般用于你只需要直接导出数据，不需要增加大标题、监听器等其他属性时
 * ---- BigTitle : 大标题，一般用于直接导出Excel实体的模板文件，只包括表头和大标题信息
 * ---- void : 空值，一般用于直接导出Excel实体的模板文件
 * ---- ExcelWriteWrapper : 包装器，可以通过该包装器添加监听器、大标题、下拉框内容等属性，且可以灵活控制大标题和数据的先后写出顺序
 *
 * <p>
 * 注意：1、驱动模式导出仅支持单Excel实体和单Sheet的导出，如果需要用到多Sheet或者多Excel实体的情况，还是请使用工厂创建模式{@link ExcelBindWriteController} {@link ExcelSimpleWriteController}
 * ---- 2、使用注解驱动模式时，需要在启动类上标注{@link cn.gjing.tools.excel.driven.EnableExcelDrivenMode 注解}
 * ---- 3、当使用{@link ExcelWriteWrapper} 作为方法返回值时，data()和write()为执行方法，Excel会根据执行方法的调用顺序先后执行
 *
 * @author Gjing
 **/
@Api(tags = "注解驱动模式Excel导出")
@RestController
@RequestMapping("/drive/write")
public class ExcelDrivenWriteController {

    @GetMapping("/write1")
    @ApiOperation(value = "单级表头导出")
    // 通过该注解声明这是一个导出方法，并指定Excel实体
    @ExcelWrite(mapping = Book.class)
    public List<Book> driveWrite1() {
        return Book.getData();
    }

    @GetMapping("/write2")
    @ApiOperation(value = "单级表头导出-->数据多次写出",notes = "多次写出数据的时候，只有第一次会写上表头，剩余的都不会，第一次如果不需要的话可以通过@ExcelWrite注解中的needValid参数控制")
    @ExcelWrite(mapping = Book.class)
    public ExcelWriteWrapper driveWrite2() {
        return ExcelWriteWrapper.build(Book.getData())
                .data(Book.getData())
                .key("1111111")
                .data(Book.getData());
    }

    @GetMapping("/write3")
    @ApiOperation("单级表头导出-->设置大标题")
    @ExcelWrite(mapping = Book.class)
    public ExcelWriteWrapper driveWrite3() {
        return ExcelWriteWrapper.build()
                .title(BigTitle.of("我是前面的大标题"))
                .data(null);
//                .title(BigTitle.of("我是后面的大标题"));
    }

    @GetMapping("/write5")
    @ApiOperation("单级表头导出-->忽略某个表头不导出")
    @ExcelWrite(mapping = Book.class, ignores = "图书性别")
    public List<Book> driveWrite5() {
        return Book.getData();
    }

    @GetMapping("/write6")
    @ApiOperation("单级表头模板导出")
    @ExcelWrite(mapping = Book.class)
    public void driveWrite6() {
    }

    @GetMapping("/write4")
    @ApiOperation("单级表头模板导出-->设置大标题")
    @ExcelWrite(mapping = Book.class)
    public BigTitle driveWrite4() {
        return BigTitle.of("我是大标题");
    }

    @GetMapping("/write7")
    @ApiOperation("单级表头导出-->方法设置忽略某个表头不导出")
    @ExcelWrite(mapping = Book.class)
    public ExcelWriteWrapper driveWrite7() {
        return ExcelWriteWrapper.build(Book.getData())
                .ignores("图书性别");
    }

    @GetMapping("/write8")
    @ApiOperation("多级表头导出")
    @ExcelWrite(mapping = Multi.class, multiHead = true)
    public List<Multi> driveWrite8() {
        return Multi.getData();
    }
}
