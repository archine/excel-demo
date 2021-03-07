package com.gjing.projects.excel.demo.entity.read;

import cn.gjing.tools.excel.Excel;
import cn.gjing.tools.excel.ExcelField;
import lombok.Data;

/**
 * 这里我们在导入多级表头的时候，我们假设只需要这两个数据。
 * 有些时候我们多级表头里面含有好几列的表头名字是重复的，所以我们通过表头名称拼接上具体哪一列，也就是Excel文件打开后最上方的ABCD，一定要大写，否则匹配不到.
 * 我们由于是导入，所以不需要和导出一样写多级表头了，只需要写excel模板中的最下面一级表头即可，因为最下面一级表头才是真实的表头
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
