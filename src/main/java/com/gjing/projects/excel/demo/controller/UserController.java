package com.gjing.projects.excel.demo.controller;

import cn.gjing.tools.excel.ExcelFactory;
import cn.gjing.tools.excel.write.BigTitle;
import com.gjing.projects.excel.demo.config.MyWorkbookListener;
import com.gjing.projects.excel.demo.entity.MultiHead;
import com.gjing.projects.excel.demo.entity.User;
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
        ExcelFactory.createWriter(User.class, response)
                .enableValid()
                .write(null)
                .flush();
    }

    @GetMapping("/multi_head_template")
    @ApiOperation("多级表头(未开启)")
    private void multiHeadTemplate(HttpServletResponse response) {
        ExcelFactory.createWriter(MultiHead.class, response)
                .write(null)
                .flush();
    }

    @GetMapping("/multi_head_template2")
    @ApiOperation("多级表头(开启)")
    private void multiHeadTemplate2(HttpServletResponse response) {
        ExcelFactory.createWriter(MultiHead.class, response)
                .enableMultiHead()
                .write(null)
                .flush();
    }

    @GetMapping("/user_template_title")
    @ApiOperation("下载带标题的模板")
    public void userTemplateTitle(HttpServletResponse response) {
        ExcelFactory.createWriter(User.class, response)
                .writeTitle(new BigTitle(2, "我是大标题"))
                .write(null)
                .flush();
    }

    @GetMapping("/user_export")
    @ApiOperation("导出全部数据")
    public void userExport(HttpServletResponse response) {
        ExcelFactory.createWriter(User.class, response)
                .addListener(new MyWorkbookListener())
                .write(this.userService.userList())
                .flush();
    }

    @GetMapping("/user_export_multi")
    @ApiOperation("分批导出数据")
    public void userExportMulti(HttpServletResponse response) {
        ExcelFactory.createWriter(User.class, response)
                .write(this.userService.userListPage(1))
                .write(this.userService.userListPage(2))
                .flush();
    }

    @GetMapping("/user_export_sheet")
    @ApiOperation("导出数据到多个sheet")
    public void userExportSheet(HttpServletResponse response) {
        ExcelFactory.createWriter(User.class, response)
                .write(this.userService.userListPage(1))
                .write(this.userService.userListPage(2), "sheet2")
                .flush();
    }


    //-------------------------------导入--------------------------------------

    @PostMapping("/user_import")
    @ApiOperation("导入普通模板")
    public void userImport(MultipartFile file) throws IOException {
        ExcelFactory.createReader(file, User.class)
                .subscribe(e -> this.userService.saveUsers(e))
                .read()
                .end();
    }

    @PostMapping("/user_import_title")
    @ApiOperation("导入带有大标题的模板")
    public void userImportTitle(MultipartFile file) throws IOException {
        ExcelFactory.createReader(file, User.class)
                .subscribe(e -> this.userService.saveUsers(e))
                //由于上面导出大标题模板的方法设置了大标题占用两行，
                // 所以这里列表头的下标为2，因为Excel下标是从0开始算的
                .read(2)
                .end();
    }

    @PostMapping("/multi_head_import")
    @ApiOperation("导入复杂表头的模板")
    public void multiHeadImport(MultipartFile file) throws IOException {
        ExcelFactory.createReader(file, MultiHead.class)
                .subscribe(System.out::println)
                .read(1)
                .end();
    }
}
