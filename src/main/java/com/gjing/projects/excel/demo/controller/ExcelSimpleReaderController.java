package com.gjing.projects.excel.demo.controller;

import cn.gjing.tools.excel.ExcelFactory;
import com.gjing.projects.excel.demo.config.excel.read.SimpleReadRowListener;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Excel简单模式导入控制器(不需要Excel实体绑定)
 * <p>
 * 通过工厂模式进行导入，也就是通过 ExcelFactory.createSimpleReader().xx().finish();
 * 该方式可以读取多个sheet.
 *
 * <p>
 * 注意：1、在链式调用的时候，只有read()方法为执行方法，其他方法为属性配置（比如添加监听器),
 * ----   所以要设置属性的话需要在执行方法调用前。方法最后一定要调用finish()方法进行释放资源
 * ---- 2、Excel会根据执行方法的调用顺序先后执行.
 * ---- 3、导入时设置忽略表头，意味着该表头不会进行读取
 * ---- 4、导入的Excel文件类型会自动辨别并寻找对应的解析器，如果是流的话，需要手动指定
 *
 * <p>
 * 真实表头开始下标计算公式: 大标题占用行数 + 表头级数 - 1
 *
 * @author Gjing
 **/
@RestController
@Api(tags = "工厂模式导入Excel (简单模式)")
@RequestMapping("/simple/read")
public class ExcelSimpleReaderController {
    /**
     * 这里演示导入的话，文件可以使用{@link ExcelBindWriteController#bindWrite(HttpServletResponse)}方法导出的文件进行导入测试
     *
     * @param file 文件
     */
    @PostMapping("/read1")
    @ApiOperation("单级表头导入")
    public void read1(@RequestPart MultipartFile file) {
        ExcelFactory.createSimpleReader(file)
                // 由于简单类型不会自动将每一行转换成Java对象，所以必须要使用行监听器去做自己的逻辑
                .addListener(new SimpleReadRowListener())
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
        // 忽略某个表头所在整列的单元格不读取
        ExcelFactory.createSimpleReader(file, "图书性别")
                .addListener(new SimpleReadRowListener())
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
        ExcelFactory.createSimpleReader(file)
                .addListener(new SimpleReadRowListener())
                .read(2)
                .finish();
    }
}
