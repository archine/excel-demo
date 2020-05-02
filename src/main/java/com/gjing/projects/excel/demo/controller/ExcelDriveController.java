package com.gjing.projects.excel.demo.controller;

import cn.gjing.tools.excel.driven.ExcelRead;
import cn.gjing.tools.excel.driven.ExcelReadWrapper;
import cn.gjing.tools.excel.driven.ExcelWrite;
import cn.gjing.tools.excel.driven.ExcelWriteWrapper;
import cn.gjing.tools.excel.write.BigTitle;
import com.gjing.projects.excel.demo.config.export.MySheetListener;
import com.gjing.projects.excel.demo.entity.SingleHead;
import com.gjing.projects.excel.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author Gjing
 **/
@Api(tags = "注解驱动方式导入导出Excel案例")
@RestController
public class ExcelDriveController {
    @Resource
    private UserService userService;

    @GetMapping("/excel_drive1")
    @ApiOperation("下载Excel模板")
    @ExcelWrite(mapping = SingleHead.class)
    public void excelDrive1() {
    }

    @GetMapping("/excel_drive2")
    @ApiOperation("导出带数据的excel")
    @ExcelWrite(mapping = SingleHead.class)
    public ExcelWriteWrapper excelDrive2() {
        return new ExcelWriteWrapper().data(userService.userList());
    }

    @GetMapping("/excel_drive3")
    @ApiOperation("导出带大标题的excel")
    @ExcelWrite(mapping = SingleHead.class)
    public ExcelWriteWrapper excelDrive3() {
        return new ExcelWriteWrapper()
                .title(new BigTitle(2, "啦啦啦"));
    }

    @GetMapping("/excel_drive4")
    @ApiOperation("导出时增加监听器")
    @ExcelWrite(mapping = SingleHead.class)
    public ExcelWriteWrapper excelDrive4() {
        return new ExcelWriteWrapper(userService.userList())
                .addListener(new MySheetListener());
    }

    //-------------导入--------------

    @PostMapping("/excel_drive5")
    @ApiOperation("导入excel")
    @ExcelRead(mapping = SingleHead.class)
    public ExcelReadWrapper<SingleHead> excelDrive5(MultipartFile file) throws IOException {
        return new ExcelReadWrapper<SingleHead>(file)
                .subscribe(e -> this.userService.saveUsers(e));
    }

    @PostMapping("/excel_drive6")
    @ApiOperation("导入带大标题的excel")
    @ExcelRead(mapping = SingleHead.class, headerIndex = 2)
    public ExcelReadWrapper<SingleHead> excelDrive6(MultipartFile file) throws IOException {
        return new ExcelReadWrapper<SingleHead>(file)
                .subscribe(e -> this.userService.saveUsers(e));
    }
}
