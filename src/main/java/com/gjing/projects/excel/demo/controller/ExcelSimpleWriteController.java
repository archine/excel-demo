package com.gjing.projects.excel.demo.controller;

import cn.gjing.tools.excel.ExcelFactory;
import cn.gjing.tools.excel.metadata.ExcelFieldProperty;
import cn.gjing.tools.excel.metadata.ExcelType;
import cn.gjing.tools.excel.write.BigTitle;
import com.gjing.projects.excel.demo.config.excel.write.RowWriteListener;
import com.gjing.projects.excel.demo.config.excel.write.SimpleWriteMergeCallback;
import com.gjing.projects.excel.demo.enums.Gender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Excel简单模式导出控制器(主要用于没有固定表头的情况)
 * <p>
 * 通过工厂模式进行导出，也就是通过 ExcelFactory.createSimpleWriter().xx().flush();
 * 使用方式和绑定模式{@link ExcelBindWriteController} 没有区别哈，唯一区别的就是简单模式创建的是一个SimpleWriter.
 *
 * <p>
 * 注意：1、在链式调用的时候，只有write()方法和writeTitle()方法为执行方法，其他方法为属性配置（比如添加监听器，开启多级表头，设置表头等等），
 * ----   所以要设置属性的话需要在执行方法调用前.方法最后一定要调用flush()方法进行数据刷新到文件
 * ---- 2、Excel会根据执行方法的调用顺序先后执行。
 * ---- 3、简单模式不支持设置数据转换器和数据校验器
 *
 * @author Gjing
 **/
@Api(tags = "工厂模式导出Excel (无绑定模式)")
@RestController
@RequestMapping("/simple/write")
public class ExcelSimpleWriteController {

    @GetMapping("/write1")
    @ApiOperation("单级表头导出")
    public void simpleWrite1(HttpServletResponse response) {
        ExcelFactory.createSimpleWriter("书籍列表", response, ExcelType.XLS)
                // 设置表头
                .head(this.getSingleHead())
                // 写出数据，数据顺序要与表头的顺序一一对应
                .write(this.getData())
                .flush();
    }

    @GetMapping("/write2")
    @ApiOperation("单级表头导出-->数据多次写出")
    public void simpleWrite2(HttpServletResponse response) {
        ExcelFactory.createSimpleWriter("书籍列表", response, ExcelType.XLS)
                .head(this.getSingleHead())
                .write(this.getData())
                // 第二次写入同一个Sheet不需要表头
                .write(this.getData(), false)
                // 写入到第二个sheet
                .write(this.getData(), "Sheet2")
                .flush();
    }

    @GetMapping("/write3")
    @ApiOperation("单级表头导出-->设置大标题")
    public void simpleWrite3(HttpServletResponse response) {
        ExcelFactory.createSimpleWriter("书籍列表", response, ExcelType.XLS)
                .head(this.getSingleHead())
                .writeTitle(BigTitle.of("我是前面的大标题"))
                .write(this.getData())
                // 这里我们设置成3行，也就是说大标题将占用三行
                .writeTitle(BigTitle.of("我是后面的大标题", 3))
                .flush();
    }

    @GetMapping("/write4")
    @ApiOperation("单级表头导出-->设置行监听器")
    public void simpleWrite4(HttpServletResponse response) {
        ExcelFactory.createSimpleWriter("书籍列表", response, ExcelType.XLSX)
                .head(this.getSingleHead())
                // 将监听器加入，这里我们继续使用绑定模式导出时写的行监听器
                .addListener(new RowWriteListener())
                .write(this.getData())
                .flush();
    }

    @GetMapping("/write5")
    @ApiOperation(value = "单级表头导出-->设置列数据自动合并", notes = "应用场景为某一列的相邻单元格内容一致时需要合并")
    public void simpleWrite5(HttpServletResponse response) {
        // 由于这里要给指定Excel列增加数据合并回调了，因此就不能通过直接传表头了，改成传ExcelFieldProperty
        ExcelFactory.createSimpleWriter("书籍列表", response, ExcelType.XLSX)
                // 这里通过head2进行设置表头属性
                .head2(this.getSingleHeadProperty())
                // 传递进去
                .write(this.getData())
                .flush();
    }

    @GetMapping("/write6")
    @ApiOperation("多级表头导出")
    public void simpleWrite6(HttpServletResponse response) {
        ExcelFactory.createSimpleWriter("旅行社出游情况", response, ExcelType.XLSX)
                .head(this.getMultiHead())
                // 和工厂的绑定模式一样，需要设置多级表头属性, 当然如果
                .multiHead()
                .write(this.getData2())
                .flush();
    }

    /**
     * 获取单级表头模拟数据
     *
     * @return 表头
     */
    private List<String[]> getSingleHead() {
        List<String[]> arr = new ArrayList<>();
        arr.add(new String[]{"名称"});
        arr.add(new String[]{"价格"});
        arr.add(new String[]{"图书性别"});
        return arr;
    }

    /**
     * 获取通过ExcelFiledProperty包装起来的模拟表头数据
     *
     * @return ExcelFieldProperty
     */
    private List<ExcelFieldProperty> getSingleHeadProperty() {
        List<ExcelFieldProperty> properties = new ArrayList<>();
        properties.add(ExcelFieldProperty.builder()
                .value(new String[]{"名称"})
                .build());
        properties.add(ExcelFieldProperty.builder()
                .value(new String[]{"价格"})
                .build());
        properties.add(ExcelFieldProperty.builder()
                .value(new String[]{"图书性别"})
                .autoMerge(true)
                .mergeCallback(SimpleWriteMergeCallback.class)
                .build());
        return properties;
    }

    /**
     * 获取多级级表头模拟数据
     *
     * @return 表头
     */
    private List<String[]> getMultiHead() {
        List<String[]> arr = new ArrayList<>();
        arr.add(new String[]{"旅行社", "旅行社", "旅行社"});
        arr.add(new String[]{"国内", "A类(张)", "全票"});
        arr.add(new String[]{"国内", "A类(张)", "半票"});
        return arr;
    }

    /**
     * 获取模拟数据
     *
     * @return 数据
     */
    private List<List<Object>> getData() {
        List<List<Object>> data = new ArrayList<>();
        data.add(Arrays.asList("小鸭子的故事", 20.12, Gender.WO_MAN));
        data.add(Arrays.asList("格林童话", 58.2, Gender.MAN));
        data.add(Arrays.asList("狼来了", 12.22, Gender.MAN));
        data.add(Arrays.asList("三国演义", 25.8, Gender.MAN));
        return data;
    }

    /**
     * 获取模拟数据
     *
     * @return 数据
     */
    private List<List<Object>> getData2() {
        List<List<Object>> data = new ArrayList<>();
        data.add(Arrays.asList("团队A", 8, 12));
        data.add(Arrays.asList("团队B", 12, 6));
        return data;
    }

}
