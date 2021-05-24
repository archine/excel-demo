package com.gjing.projects.excel.demo.controller;

import cn.gjing.tools.excel.ExcelFactory;
import com.gjing.projects.excel.demo.config.excel.read.EmptyListener;
import com.gjing.projects.excel.demo.config.excel.read.RowReaderListener;
import com.gjing.projects.excel.demo.entity.read.ExcelEntity;
import com.gjing.projects.excel.demo.entity.read.Multi2;
import com.gjing.projects.excel.demo.entity.write.Book;
import com.gjing.projects.excel.demo.entity.write.Book2;
import com.gjing.projects.excel.demo.entity.write.BookValid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Excel绑定模式导入控制器(需要Excel实体绑定)
 * <p>
 * 通过工厂模式进行导入，也就是通过 ExcelFactory.createReader().xx().finish();
 * 该方式可以读取多个sheet.
 *
 * <p>
 * 注意：1、在链式调用的时候，只有read()方法为执行方法，其他方法为属性配置（比如添加监听器, 设置真实表头下标、设置是否检查模板匹配),
 * ----   所以要设置属性的话需要在执行方法调用前。方法最后一定要调用finish()方法进行释放资源
 * ---- 2、Excel会根据执行方法的调用顺序先后执行.
 * ---- 3、导入时设置忽略表头，意味着该表头不会进行读取
 * ---- 4、导入的Excel文件类型(xls,xlsx)一定要用Excel实体中指定的一致，否则会出现错误
 *
 * <p>
 * 真实表头开始下标计算公式: 大标题占用行数 + 表头级数 - 1
 *
 * @author Gjing
 **/
@Api(tags = "工厂模式导入Excel (绑定模式)")
@RestController
@RequestMapping("/bind/read")
public class ExcelBindReaderController {

    /**
     * 这里演示导入的话，文件可以使用{@link ExcelBindWriteController#bindWrite(HttpServletResponse)}方法导出的文件进行导入测试
     *
     * @param file 文件
     */
    @PostMapping("/read1")
    @ApiOperation("单级表头导入")
    public void read1(@RequestPart MultipartFile file) {
        // 由于Book这个Excel实体没有增加数据转换器，枚举会转换失败，所以我们这里先忽略
        ExcelFactory.createReader(file, Book.class, "图书性别")
                // 这是一个结果订阅监听器，一旦配置了该监听器，会在excel所有数据导入完成后将生成的数据传输过来，你可以通过该监听器进行数据库插入.
                // 我们这里只做普通输出
                .subscribe(System.out::println)
                .read()
                .finish();
    }

    /**
     * 这里演示导入的话，文件可以使用{@link ExcelBindWriteController#bindWrite4(HttpServletResponse)}}方法导出的文件进行导入测试.
     *
     * @param file 文件
     */
    @PostMapping("/read2")
    @ApiOperation(value = "单级表头导入-->带大标题", notes = "这里由于文件带有大标题，因此我们需要指定一下真实表头的开始下标，大标题默认是占用2行，因此套用公式：2+1-1=2")
    public void read2(@RequestPart MultipartFile file) {
        // 由于Excel实体没有增加数据转换器，枚举会转换失败，所以我们这里先忽略
        ExcelFactory.createReader(file, Book.class, "图书性别")
                .subscribe(System.out::println)
                // 设置真实表头开始下标
                .read(2)
                .finish();
    }

    /**
     * 这里演示导入的话，文件可以使用{@link ExcelBindWriteController#multi(HttpServletResponse)}}方法导出的文件进行导入测试.
     *
     * @param file 文件
     */
    @PostMapping("/read3")
    @ApiOperation(value = "多级表头导入", notes = "由于是多级表头，最下面一级为真实表头, 我们测试的Excel实体为3级且没有设置大标题，所以我们继续套用公式：0+3-1=2")
    public void read3(@RequestPart MultipartFile file) {
        ExcelFactory.createReader(file, Multi2.class)
                .subscribe(System.out::println)
                .read(2)
                .finish();
    }

    /**
     * 这里演示导入的话，文件可以使用{@link ExcelBindWriteController#writeTemplate2(HttpServletResponse)}方法导出的模板文件填写一些模拟数据进行导入测试.
     * <p>
     * 当设置了断言的那一列用户填写的数据不满足断言表达式，将抛出{@link cn.gjing.tools.excel.exception.ExcelAssertException}
     *
     * @param file 文件
     */
    @PostMapping("/read4")
    @ApiOperation(value = "单级表头导入-->添加数据断言", notes = "由于下拉框、时间校验、文本校验这些在Excel文件中同一个单元格中只能同时生效一个，因此在碰到某一列的单元格需要校验多种情况的时候就可以使用数据断言啦")
    public void read4(@RequestPart MultipartFile file) {
        // 由于Excel实体没有增加数据转换器，枚举会转换失败，所以我们这里先忽略
        ExcelFactory.createReader(file, BookValid.class, "图书性别")
                .subscribe(System.out::println)
                .read()
                .finish();
    }

    /**
     * 这里演示导入的话，文件可以使用{@link ExcelBindWriteController#bindWrite8(HttpServletResponse)}方法导出的文件进行导入测试.
     *
     * @param file 文件
     */
    @PostMapping("/read5")
    @ApiOperation(value = "单级表头导入-->添加数据转换器", notes = "应用在单元格的内容不符合我的要求，需要进行转换")
    public void read5(@RequestPart MultipartFile file) {
        ExcelFactory.createReader(file, Book2.class)
                .subscribe(System.out::println)
                .read()
                .finish();
    }

    /**
     * 这里演示导入的话，文件可以使用{@link ExcelBindWriteController#writeTemplate9(HttpServletResponse)}方法导出的文件进行导入测试.
     *
     * @param file 文件
     */
    @PostMapping("/read6")
    @ApiOperation(value = "单级表头导入-->打开文件匹配检查", notes = "主要用于防止用户导入不是我们系统导出去的Excel文件或者导入的文件不是与当前Excel实体绑定的")
    public void read6(@RequestPart MultipartFile file) {
        // 由于Excel实体没有增加数据转换器，所以枚举会转换失败，所以我们这里先忽略
        ExcelFactory.createReader(file, Book.class, "图书性别")
                .subscribe(System.out::println)
                // 开启文件检查
                .check()
                .read()
                .finish();
    }

    /**
     * 该方法用来导入测试的Excel文件在项目的根目录中，名为test.xlsx
     *
     * @param file 文件
     */
    @PostMapping("/read7")
    @ApiOperation(value = "单级表头导入-->增加监听器", notes = "应用在需要导入的同时需要执行自己的业务逻辑")
    public void read7(@RequestPart MultipartFile file) {
        // 我们这里就不用结果订阅监听器了，而是通过行监听器的readFinish进行值的输出
        ExcelFactory.createReader(file, ExcelEntity.class)
                .addListener(new RowReaderListener())
                .read()
                .finish();
    }

    /**
     * 这里演示导入的话，文件可以使用{@link ExcelBindWriteController#writeTemplate1(HttpServletResponse)}方法导出的文件进行导入测试.
     *
     * @param file 文件
     */
    @PostMapping("/read8")
    @ApiOperation(value = "单级表头导入-->增加空值监听器")
    public void read8(@RequestPart MultipartFile file) {
        // 由于Excel实体没有增加数据转换器，所以枚举会转换失败，所以我们这里先忽略
        ExcelFactory.createReader(file, Book.class, "图书性别")
                .subscribe(System.out::println)
                .addListener(new EmptyListener())
                .read()
                .finish();
    }
}
