package com.gjing.projects.excel.demo.controller;

import cn.gjing.tools.excel.driven.ExcelRead;
import cn.gjing.tools.excel.driven.ExcelReadWrapper;
import cn.gjing.tools.excel.driven.ExcelWrite;
import cn.gjing.tools.excel.driven.ExcelWriteWrapper;
import cn.gjing.tools.excel.write.BigTitle;
import com.gjing.projects.excel.demo.config.export.MySheetListener;
import com.gjing.projects.excel.demo.entity.MultiHead;
import com.gjing.projects.excel.demo.entity.SingleHead;
import com.gjing.projects.excel.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author Gjing
 **/
@Api(tags = "注解驱动方式导入导出Excel案例")
@RestController
@RequestMapping("/drive")
public class ExcelDriveController {
    @Resource
    private UserService userService;

    @GetMapping("/export1")
    @ApiOperation("下载Excel模板")
    @ExcelWrite(mapping = SingleHead.class)
    public void excelDrive1() {
    }

    @GetMapping("/export2")
    @ApiOperation("导出带数据的excel")
    @ExcelWrite(mapping = SingleHead.class)
    public ExcelWriteWrapper excelDrive2() {
        return ExcelWriteWrapper.build(this.userService.userList());
    }

    @GetMapping("/export3")
    @ApiOperation("导出带大标题的excel")
    @ExcelWrite(mapping = SingleHead.class)
    public ExcelWriteWrapper excelDrive3() {
        return ExcelWriteWrapper.build()
                .title(new BigTitle(2, "啦啦啦"));
    }

    @GetMapping("/export4")
    @ApiOperation("直接返回大标题导出")
    @ExcelWrite(mapping = SingleHead.class)
    public BigTitle excelDrive4() {
        return new BigTitle(2, "啦啦啦");
    }

    @GetMapping("/export5")
    @ApiOperation("导出时增加监听器")
    @ExcelWrite(mapping = SingleHead.class)
    public ExcelWriteWrapper excelDrive5() {
        return ExcelWriteWrapper.build(userService.userList())
                .listener(new MySheetListener());
    }

    @GetMapping("/export6")
    @ApiOperation("直接返回数据方式进行导出")
    @ExcelWrite(mapping = SingleHead.class)
    public List<SingleHead> excelDrive16() {
        return this.userService.userList();
    }

    @GetMapping("/export7")
    @ApiOperation("导出有数据和大标题的")
    @ExcelWrite(mapping = SingleHead.class)
    public ExcelWriteWrapper export7() {
        return ExcelWriteWrapper.build(userService.userList())
                .title(new BigTitle(3, "导出啦啦"));
    }

    @GetMapping("/export8")
    @ApiOperation("导出多级表头的模板")
    @ExcelWrite(mapping = MultiHead.class, multiHead = true)
    public void export8() {

    }

    @GetMapping("/export9")
    @ApiOperation("导出模板并忽略某个表头")
    @ExcelWrite(mapping = SingleHead.class, ignores = "爱好")
    public void export9() {

    }

    //-------------导入--------------

    @PostMapping("/read1")
    @ApiOperation("导入excel")
    @ExcelRead(ignores = "性别")
    public ExcelReadWrapper<SingleHead> read1(MultipartFile file) throws IOException {
        return ExcelReadWrapper.build(SingleHead.class)
                .data(file)
                .subscribe(e -> this.userService.saveUsers(e));
    }

    @PostMapping("/read2")
    @ApiOperation("导入带大标题的excel")
    @ExcelRead(headerIndex = 2)
    public ExcelReadWrapper<SingleHead> read2(MultipartFile file) throws IOException {
        return ExcelReadWrapper.build(SingleHead.class)
                .data(file)
                .subscribe(e -> this.userService.saveUsers(e));
    }

    @PostMapping("/read3")
    @ApiOperation("导入多级表头的模板")
    @ExcelRead(headerIndex = 1)
    public ExcelReadWrapper<MultiHead> read3(MultipartFile file) throws IOException {
        return ExcelReadWrapper.build(MultiHead.class)
                .data(file)
                .subscribe(e -> System.out.println(e.toString()));
    }

    @PostMapping("/read4")
    @ApiOperation("导入excel并忽略某个表头")
    @ExcelRead(ignores = "爱好")
    public ExcelReadWrapper<SingleHead> read4(MultipartFile file) throws IOException {
        return ExcelReadWrapper.build(SingleHead.class)
                .data(file)
                .subscribe(System.out::println);
    }
}
