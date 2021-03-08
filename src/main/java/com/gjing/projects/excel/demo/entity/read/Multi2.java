package com.gjing.projects.excel.demo.entity.read;

import cn.gjing.tools.excel.Excel;
import cn.gjing.tools.excel.ExcelField;
import lombok.Data;

/**
 * 这里我们在导入时候，我们只读取这两个Excel列
 * 有些时候我们Excel文件中含有好几列的真实表头的名字是重复的，所以我们通过表头名称拼接上具体哪一列，也就是Excel文件打开后最上方的ABCD，一定要大写，否则匹配不到.
 * 由于是导入，所以不需要和导出一样写多级表头了，只需要写真实表头即可
 *
 * @author Gjing
 **/
@Data
@Excel
public class Multi2 {
    @ExcelField("旅行社")
    private String tour;

    @ExcelField("半票D")
    private Integer ticketA;
}
