package com.gjing.projects.excel.demo.controller;

import cn.gjing.tools.excel.driven.ExcelRead;
import cn.gjing.tools.excel.driven.ExcelReadWrapper;
import com.gjing.projects.excel.demo.config.excel.read.RowReaderListener;
import com.gjing.projects.excel.demo.entity.read.ExcelEntity;
import com.gjing.projects.excel.demo.entity.read.Multi2;
import com.gjing.projects.excel.demo.entity.write.Book;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Excel注解驱动模式导入控制器 (需要Excel实体绑定)
 * <p>
 * 通过驱动模式进行导入，不需要再显性的通过ExcelFactory.xxx()调用方式来进行Excel文件导入,
 * 该模式只是更改了调用的方式，并没有改变原有的框架功能（如：数据断言、数据转换器、监听器。。
 * <p>
 * 注意: 导入的Excel文件类型(xls,xlsx)一定要用Excel实体中指定的一致，否则会出现错误
 * <p>
 * 返回值类型：
 * ---- ExcelReadWrapper : 包装器，可以通过该包装器添加文件、监听器
 *
 * <p>
 * 真实表头开始下标计算公式: 大标题占用行数 + 表头级数 - 1
 *
 * @author Gjing
 **/
@Api(tags = "注解驱动模式Excel导入")
@RestController
@RequestMapping("/read/drive")
public class ExcelDriveReaderController {

    /**
     * 这里演示导入的话，文件可以使用{@link ExcelBindWriteController#bindWrite(HttpServletResponse)}方法导出的文件进行导入测试
     *
     * @param file 文件
     */
    @PostMapping("/read1")
    @ApiOperation("单级表头导入")
    @ExcelRead(ignores = "图书性别")
    public ExcelReadWrapper<Book> driveRead1(@RequestPart MultipartFile file) {
        return ExcelReadWrapper.build(Book.class)
                // 通过结果监听器将数据输出
                .subscribe(System.out::println)
                .data(file);
    }

    /**
     * 这里演示导入的话，文件可以使用{@link ExcelBindWriteController#bindWrite4(HttpServletResponse)}}方法导出的文件进行导入测试.
     *
     * @param file 文件
     */
    @PostMapping("/read2")
    @ApiOperation(value = "单级表头导入--带大标题", notes = "同样需要真实表头开始下标")
    @ExcelRead(headerIndex = 2, ignores = "图书性别")
    public ExcelReadWrapper<Book> driveRead2(@RequestPart MultipartFile file) {
        return ExcelReadWrapper.build(Book.class)
                .subscribe(System.out::println)
                .data(file);
    }

    /**
     * 这里演示导入的话，文件可以使用{@link ExcelBindWriteController#multi(HttpServletResponse)}}方法导出的文件进行导入测试.
     *
     * @param file 文件
     */
    @PostMapping("/read3")
    @ApiOperation(value = "多级表头导入", notes = "同样需要真实表头开始下标")
    @ExcelRead(headerIndex = 2)
    public ExcelReadWrapper<Multi2> driveRead3(@RequestPart MultipartFile file) {
        return ExcelReadWrapper.build(Multi2.class)
                .data(file)
                .subscribe(System.out::println);

    }

    /**
     * 该方法用来导入测试的Excel文件在项目的根目录中，名为test.xlsx
     *
     * @param file 文件
     */
    @PostMapping("/read4")
    @ApiOperation(value = "单级表头导入-->添加监听器")
    @ExcelRead
    public ExcelReadWrapper<ExcelEntity> driveRead4(@RequestPart MultipartFile file) {
        return ExcelReadWrapper.build(ExcelEntity.class)
                .data(file)
                // 我们这里就不用结果订阅监听器了，而是通过行监听器的readFinish进行值的输出
                .listener(new RowReaderListener());
    }

    /**
     * 这里演示导入的话，文件可以使用{@link ExcelBindWriteController#bindWrite(HttpServletResponse)}方法导出的文件进行导入测试
     *
     * @param file 文件
     */
    @PostMapping("/read5")
    @ApiOperation("单级表头导入-->开启文件匹配检查")
    @ExcelRead(check = true, ignores = "图书性别")
    public ExcelReadWrapper<Book> driveRead5(@RequestPart MultipartFile file) {
        return ExcelReadWrapper.build(Book.class)
                // 通过结果监听器将数据输出
                .subscribe(System.out::println)
//                .check("1111")
                .data(file);
    }
}
