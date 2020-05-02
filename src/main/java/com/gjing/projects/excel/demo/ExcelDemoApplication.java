package com.gjing.projects.excel.demo;

import cn.gjing.tools.excel.driven.EnableExcelDrivenMode;
import cn.gjing.tools.swagger.core.EnableSingleDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Gjing
 */
@EnableExcelDrivenMode
@SpringBootApplication
@EnableSingleDoc
public class ExcelDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExcelDemoApplication.class, args);
    }

}
