package com.gjing.projects.excel.demo.controller;

import cn.gjing.tools.excel.ExcelFactory;
import cn.gjing.tools.excel.write.BigTitle;
import cn.gjing.tools.excel.write.valid.DefaultCascadingDropdownBoxListener;
import com.gjing.projects.excel.demo.config.read.MyReadRowListener;
import com.gjing.projects.excel.demo.config.export.MyStyleListener;
import com.gjing.projects.excel.demo.config.export.MyWorkbookListener;
import com.gjing.projects.excel.demo.entity.MultiHead;
import com.gjing.projects.excel.demo.entity.SingleHead;
import com.gjing.projects.excel.demo.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gjing
 **/
@RestController
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/users")
    @ApiOperation("添加模拟数据")
    @ApiImplicitParam(name = "number", value = "条数", dataType = "int", paramType = "query")
    public ResponseEntity<String> saveUsers(Integer number) {
        this.userService.saveUsers(number);
        return ResponseEntity.ok("添加成功");
    }

    @GetMapping("/user_template")
    @ApiOperation("下载模板")
    public void userTemplate(HttpServletResponse response) {
        ExcelFactory.createWriter(SingleHead.class, response)
                .enableValid()
                .write(null)
                .flush();
    }

    @GetMapping("/user_template2")
    @ApiOperation("多级表头模板(未开启)")
    public void userTemplate2(HttpServletResponse response) {
        ExcelFactory.createWriter(MultiHead.class, response)
                .write(null)
                .flush();
    }

    @GetMapping("/user_template3")
    @ApiOperation("多级表头模板(开启)")
    public void userTemplate3(HttpServletResponse response) {
        ExcelFactory.createWriter(MultiHead.class, response)
                .enableMultiHead()
                .write(null)
                .flush();
    }

    @GetMapping("/user_template4")
    @ApiOperation("下载带标题的模板")
    public void userTemplate4(HttpServletResponse response) {
        ExcelFactory.createWriter(SingleHead.class, response)
                .writeTitle(new BigTitle(2, "我是大标题"))
                .write(null)
                .flush();
    }

    @GetMapping("/user_template5")
    @ApiOperation("导出带有级联的模板")
    public void userTemplate5(HttpServletResponse response) {
        //传入二级下拉框的内容，key为你父级对应的值，以下设置了根据性别不同展示不同的爱好选项
        Map<String, String[]> boxValues = new HashMap<>(8);
        boxValues.put("男", new String[]{"游戏", "运动"});
        boxValues.put("女", new String[]{"逛街", "吃"});
        ExcelFactory.createWriter(SingleHead.class, response)
                .enableValid()
                //使用默认的级联下拉框监听器
                .addListener(new DefaultCascadingDropdownBoxListener(boxValues))
                .write(null)
                .flush();
    }

    @GetMapping("/user_template6")
    @ApiOperation("通过方法传递普通下拉框的内容")
    public void userTemplate6(HttpServletResponse response) {
        Map<String, String[]> genderMap = new HashMap<>(8);
        genderMap.put("gender", new String[]{"男", "女"});
        ExcelFactory.createWriter(SingleHead.class, response)
                .enableValid()
                //使用默认的级联下拉框监听器
                .write(null, genderMap)
                .flush();
    }

    @GetMapping("/user_export")
    @ApiOperation("导出全部数据")
    public void userExport(HttpServletResponse response) {
        ExcelFactory.createWriter(SingleHead.class, response)
                .addListener(new MyWorkbookListener())
                .write(this.userService.userList())
                .flush();
    }

    @GetMapping("/user_export2")
    @ApiOperation("分批导出数据")
    public void userExport2(HttpServletResponse response) {
        ExcelFactory.createWriter(SingleHead.class, response)
                .write(this.userService.userListPage(1))
                .write(this.userService.userListPage(2))
                .flush();
    }

    @GetMapping("/user_export3")
    @ApiOperation("导出数据到多个sheet")
    public void userExport3(HttpServletResponse response) {
        ExcelFactory.createWriter(SingleHead.class, response)
                .write(this.userService.userListPage(1))
                .write(this.userService.userListPage(2), "sheet2")
                .flush();
    }

    @GetMapping("/user_export4")
    @ApiOperation("自定义样式导出所有数据")
    public void userExport4(HttpServletResponse response) {
        //关闭初始化默认样式监听器
        ExcelFactory.createWriter(SingleHead.class, response, false)
                //加入自己定义的样式
                .addListener(new MyStyleListener())
                .write(this.userService.userList())
                .flush();
    }

    //-------------------------------导入--------------------------------------

    @PostMapping("/user_import")
    @ApiOperation("导入普通模板")
    public void userImport(MultipartFile file) throws IOException {
        ExcelFactory.createReader(file, SingleHead.class)
                .subscribe(e -> this.userService.saveUsers(e))
                .read()
                .end();
    }

    @PostMapping("/user_import2")
    @ApiOperation("导入带有大标题的模板")
    public void userImport2(MultipartFile file) throws IOException {
        ExcelFactory.createReader(file, SingleHead.class)
                .subscribe(e -> this.userService.saveUsers(e))
                //由于上面导出大标题模板的方法设置了大标题占用两行，
                // 所以这里列表头的下标为2，因为Excel下标是从0开始算的
                .read(2)
                .end();
    }

    @PostMapping("/user_import3")
    @ApiOperation("导入复杂表头的模板")
    public void userImport3(MultipartFile file) throws IOException {
        ExcelFactory.createReader(file, MultiHead.class)
                .subscribe(System.out::println)
                //因为表头有两级，实际表头是最下面一级，所以指定为1
                //由于Excel下标是从0开始计算的，所以是1
                .read(1)
                .end();
    }

    @PostMapping("/user_import4")
    @ApiOperation("添加监听器导入")
    public void userImport4(MultipartFile file) throws IOException {
        ExcelFactory.createReader(file, SingleHead.class)
                .addListener(new MyReadRowListener())
                .read()
                .end();
    }
}
