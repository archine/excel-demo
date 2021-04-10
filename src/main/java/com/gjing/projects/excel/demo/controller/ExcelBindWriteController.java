package com.gjing.projects.excel.demo.controller;

import cn.gjing.tools.excel.ExcelFactory;
import cn.gjing.tools.excel.metadata.listener.DefaultCascadingDropdownBoxListener;
import cn.gjing.tools.excel.write.BigTitle;
import com.gjing.projects.excel.demo.config.excel.write.CellWriteListener;
import com.gjing.projects.excel.demo.config.excel.write.MyStyleListener;
import com.gjing.projects.excel.demo.config.excel.write.RowWriteListener;
import com.gjing.projects.excel.demo.entity.write.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Excel绑定模式导出控制器(需要Excel实体绑定)
 * <p>
 * 通过工厂模式进行导出，也就是通过 ExcelFactory.createWriter().xx().flush();
 * 该方式可以同时导出数据到多个sheet，且可以切换Excel实体.
 *
 * <p>
 * 注意：1、在链式调用的时候，只有write()方法和writeTitle()方法为执行方法，其他方法为属性配置（比如添加监听器，开启多级表头等等）,
 * ----   所以要设置属性的话需要在执行方法调用前.方法最后一定要调用flush()方法进行数据刷新到文件
 * ---- 2、Excel会根据执行方法的调用顺序先后执行.
 *
 * @author Gjing
 **/
@Api(tags = "工厂模式导出Excel (绑定模式)")
@RestController
@RequestMapping("/bind/write")
public class ExcelBindWriteController {

    @GetMapping("/write1")
    @ApiOperation("单级表头导出")
    public void bindWrite(HttpServletResponse response) {
        // 需要指定Excel实体为 Book
        ExcelFactory.createWriter(Book.class, response)
                // 设置要导出的数据
                .write(Book.getData())
                // 数据刷新到Excel文件，必须要调用此方法，否则数据不会刷新到Excel文件中
                .flush();
    }

    @GetMapping("/write2")
    @ApiOperation(value = "单级表头导出-->数据多次写出", notes = "多次写入就是多次调用write方法。多次写入到同一个sheet时，每次都会写入表头，" +
            "如果接下来的次数不想生成表头的话，需要在调用write()方法时设置needHead参数为false")
    public void bindWrite2(HttpServletResponse response) {
        ExcelFactory.createWriter(Book.class, response)
                // 写入第一次数据
                .write(Book.getData())
                // 写入第二次数据
                .write(Book.getData())
                // 写入第三次数据，并且不需要表头
                .write(Book.getData(), false)
                .flush();
    }

    @GetMapping("/write3")
    @ApiOperation("单级表头导出-->写出数据到不同sheet")
    public void bindWrite3(HttpServletResponse response) {
        ExcelFactory.createWriter(Book.class, response)
                // 写出到默认的Sheet，默认Sheet名为Sheet1
                .write(Book.getData())
                // 写出数据到Sheet2，这里的Sheet名称随意，不存在会自动创建
                .write(Book.getData(), "Sheet2")
                .flush();
    }

    @GetMapping("/write4")
    @ApiOperation(value = "单级表头导出-->设置大标题", notes = "大标题和写出的数据在Excel文件中出现的顺序根据调用顺序来控制")
    public void bindWrite4(HttpServletResponse response) {
        ExcelFactory.createWriter(Book.class, response)
                // 通过该方法写出一个大标题
                .writeTitle(BigTitle.of("我是大标题"))
                .write(Book.getData())
                .flush();
    }

    @GetMapping("/write5")
    @ApiOperation(value = "单级表头导出-->不同Excel实体的数据导出到不同的sheet中", notes = "应用场景在同一个excel文件中，需要导出不同类型的数据")
    public void bindWrite5(HttpServletResponse response) {
        ExcelFactory.createWriter(Book.class, response)
                .write(Book.getData())
                // 由于要导出其他Excel实体的数据，那么这里需要重置一下Excel实体,
                .resetExcelClass(Water.class)
                .write(Water.getData(), "Sheet2")
                .flush();
    }

    @GetMapping("/write6")
    @ApiOperation("单级表头导出-->通过方法设置导出的Excel文件名")
    public void bindWrite6(HttpServletResponse response) {
        ExcelFactory.createWriter("书籍列表", Book.class, response)
                .write(Book.getData())
                .flush();
    }

    @GetMapping("/write7")
    @ApiOperation(value = "单级表头导出-->忽略某个表头不导出", notes = "应用场景在用户需要手动选择导出哪些表头，这时候就让前端将不需要导出的表头作为数组传过来")
    public void bindWrite7(HttpServletResponse response) {
        // 这里将Excel实体中 <图书性别> 这个表头忽略掉，不让他导出到excel文件中
        // 注意：如果表头是多级表头中的父级表头，那么他下面的子表头会一起忽略掉
        ExcelFactory.createWriter(Book.class, response, "图书性别")
                .write(Book.getData())
                .flush();
    }

    @GetMapping("/write8")
    @ApiOperation(value = "单级表头导出-->使用数据转换器", notes = "应用场景为需要更改某个字段的内容")
    public void bindWrite8(HttpServletResponse response) {
        // 目前有两种方式，分别是注解方式和实现接口
        ExcelFactory.createWriter(Book2.class, response)
                .write(Book2.getData())
                .flush();
    }

    @GetMapping("/write9")
    @ApiOperation(value = "单级表头导出-->列数据纵向合并", notes = "应用场景为某一列的相邻单元格内容一致时需要合并")
    public void bindWrite9(HttpServletResponse response) {
        ExcelFactory.createWriter(Book3.class, response)
                .write(Book3.getData())
                .flush();
    }

    @GetMapping("/write10")
    @ApiOperation(value = "单级表头导出-->设置行监听器", notes = "应用场景为当前一行导出完毕后需要执行拓展操作")
    public void bindWrite10(HttpServletResponse response) {
        ExcelFactory.createWriter(Book.class, response)
                // 由于是设置属性，所以要在执行方法前调用
                .addListener(new RowWriteListener())
                .write(Book.getData())
                .flush();
    }

    @GetMapping("/write11")
    @ApiOperation(value = "单级表头导出-->设置单元格监听器", notes = "应用场景为需要在单元格从创建后到赋值完毕后进行一些额外的操作")
    public void bindWrite11(HttpServletResponse response) {
        ExcelFactory.createWriter(Book.class, response)
                .addListener(new CellWriteListener())
                .write(Book.getData())
                .flush();
    }

    @GetMapping("/write12")
    @ApiOperation(value = "单级表头导出-->自定义样式监听器", notes = "应用场景为需要自定义自己的excel表格样式，包括表头的颜色、字体等")
    public void bindWrite12(HttpServletResponse response) {
        // 想要使用自己的样式监听器，就需要在创建写出器的时候将初始化默认样式更改为false，否则会出现单元格赋值完默认样式又会赋值你自定义的样式
        ExcelFactory.createWriter(Book.class, response, false)
                .addListener(new MyStyleListener())
                .write(Book.getData())
                .flush();
    }

    @GetMapping("/write_template1")
    @ApiOperation(value = "单级表头模板导出", notes = "模板导出就是用来给用户生成一个指定列和格式的Excel空文件，让用户填写数据后用来导入的。该文件只包含了表头和大标题(若需要)")
    public void writeTemplate1(HttpServletResponse response) {
        ExcelFactory.createWriter(Book.class, response)
                // 由于是模板文件，那么肯定是没数据的，所以数据设置为null即可
                .write(null)
                .flush();
    }

    @GetMapping("/write_template2")
    @ApiOperation(value = "单级表头模板导出-->增加数据校验注解", notes = "应用场景为导出模板后，但是要限制某列的单元格输入的数据规范")
    public void writeTemplate2(HttpServletResponse response) {
        ExcelFactory.createWriter(BookValid.class, response)
                // 该属性为是否开启导出模板时给Excel文件启用校验注解，默认false
                .valid(true)
                .write(null)
                .flush();
    }

    @GetMapping("/write_template3")
    @ApiOperation(value = "单级表头模板导出-->增加数据校验注解(下拉框的内容通过方法设置)", notes = "应用场景为，下拉框中的内容不是写死的，可能存为数据库里或者其他地方，" +
            "一开始不知道有什么值或者值的数量太多。使用方法设置下拉框的内容时，同样需要在Excel实体你要增加下拉框的那个字段上增加下拉框注解，只是你不需要在给下拉框注解内的combobox参数设置值，" +
            "就算设置了，也会被覆盖，因为方法的优先级高于注解")
    public void writeTemplate4(HttpServletResponse response) {
        // 这边模拟下拉框的内容, key为Excel实体的字段名称
        Map<String, String[]> boxValues = new HashMap<>(16);
        boxValues.put("gender", new String[]{"男", "女"});
        ExcelFactory.createWriter(BookValid.class, response)
                .valid(true)
                .write(null, boxValues)
                .flush();
    }

    @GetMapping("/write_template4")
    @ApiOperation(value = "单级表头模板导出-->级联下拉框", notes = "级联下拉框应用场景为 B列单元格的下拉框内容跟着A列单元格的值进行动态切换，比如女孩子和男孩子喜欢的东西都是不一样的，" +
            "那么当A列的单元格选择了男时，B列的单元格就只出现男生喜欢的东西。级联产生条件是在同一行，也就是A列第一行的单元格只能影响到B列第一行的单元格")
    public void writeTemplate5(HttpServletResponse response) {
        // 这里map的key就跟上面的方法手动设置下拉框值不一样了，这里的key为父级的内容
        Map<String, String[]> boxValues = new HashMap<>(16);
        boxValues.put("男", new String[]{"篮球", "电脑"});
        boxValues.put("女", new String[]{"买买买", "逛街"});
        ExcelFactory.createWriter(BookCascade.class, response)
                // 同样需要设置valid属性为true
                .valid(true)
                // 这里需要我们添加框架中提供的级联监听器到执行器
                .addListener(new DefaultCascadingDropdownBoxListener(boxValues))
                .write(null)
                .flush();
    }

    @GetMapping("/write_template5")
    @ApiOperation(value = "单级表头模板导出-->设置导出的Excel模板文件与当前Excel实体进行绑定", notes = "应用场景在防止用户在导入的时候，导入与Excel实体不匹配的Excel文件")
    public void writeTemplate9(HttpServletResponse response) {
        ExcelFactory.createWriter(Book.class, response)
                // 默认是开启的，所以不用手动设置，除非你想关闭他
                .bind(true)
                .write(null)
                .flush();
    }

    @GetMapping("/multi")
    @ApiOperation(value = "多级表头导出", notes = "多级表头除了在定义实体的时候有区别，其他操作都是一样的")
    public void multi(HttpServletResponse response) {
        ExcelFactory.createWriter(Multi.class, response)
                // 这里需要设置多级表头属性为true，否则是不会自动合并的
                .multiHead(true)
                .write(Multi.getData())
                .flush();
    }

    @GetMapping("/multi2")
    @ApiOperation(value = "多级表头导出-->忽略某个表头不导出", notes = "应用场景在用户需要手动选择导出哪些表头，这时候就让前端将不需要导出的表头作为数组传过来")
    public void multi2(HttpServletResponse response) {
        // 这里将Excel实体中 C类和半票 这两个表头忽略掉，不让他导出到excel文件中
        // 注意：如果表头是多级表头中的父级表头，那么他下面的子表头会一起忽略掉
        ExcelFactory.createWriter(Multi.class, response, "C类(张)", "半票")
                .multiHead(true)
                .write(Multi.getData())
                .flush();
    }
}
