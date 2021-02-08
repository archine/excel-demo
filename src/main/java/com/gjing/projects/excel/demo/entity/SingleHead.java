package com.gjing.projects.excel.demo.entity;

import cn.gjing.tools.excel.Excel;
import cn.gjing.tools.excel.ExcelField;
import cn.gjing.tools.excel.convert.ExcelDataConvert;
import cn.gjing.tools.excel.metadata.ExcelType;
import cn.gjing.tools.excel.read.valid.ExcelAssert;
import cn.gjing.tools.excel.write.merge.Merge;
import cn.gjing.tools.excel.write.valid.ExcelDropdownBox;
import cn.gjing.tools.excel.write.valid.ExcelNumericValid;
import cn.gjing.tools.excel.write.valid.ExcelRepeatValid;
import cn.gjing.tools.excel.write.valid.ValidType;
import com.gjing.projects.excel.demo.config.export.SingleHeadCallback;
import com.gjing.projects.excel.demo.enums.Gender;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static cn.gjing.tools.excel.write.valid.OperatorType.LESS_THAN;
import static cn.gjing.tools.excel.write.valid.ValidType.TEXT_LENGTH;

/**
 * @author Gjing
 **/
@Excel(value = "Excel模板",type = ExcelType.XLSX)
@Data
@Entity
@Table(name = "customer")
public class SingleHead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ExcelRepeatValid
    @ExcelField(value = "姓名", autoMerge = @Merge(enable = true))
    @ExcelNumericValid(validType = TEXT_LENGTH, operatorType = LESS_THAN, expr1 = "4", errorContent = "姓名字数小于4")
    @ExcelAssert(expr = "#userName != null")
    @Column(name = "user_name", columnDefinition = "varchar(20)")
    private String userName;

    @ExcelField(value = "年龄", format = "0", autoMerge = @Merge(enable = true))
    @ExcelDataConvert(expr1 = "#userAge * 10")
    @ExcelNumericValid(validType = ValidType.INTEGER, expr1 = "100", errorContent = "年龄不能超过100")
    @Column(name = "user_age", columnDefinition = "tinyint(2)")
    private Integer userAge;

    @ExcelField(value = "性别",autoMerge = @Merge(enable = true,callback = SingleHeadCallback.class))
    @ExcelDropdownBox(combobox = {"男", "女"})
    @ExcelDataConvert(expr1 = "#gender.desc", expr2 = "T(com.gjing.projects.excel.demo.enums.Gender).of(#gender)")
    @Column(name = "gender", columnDefinition = "tinyint(2)")
    @Convert(converter = Gender.GenderConvert.class)
    private Gender gender;

    @ExcelField(value = "生日",format = "yyyy-MM-dd")
    private Date birthday;

    @ExcelDropdownBox(link = "2")
    private String favorite;

    public SingleHead() {
    }

    public SingleHead(String userName, Integer userAge, Gender gender) {
        this.userAge = userAge;
        this.userName = userName;
        this.gender = gender;
    }
}

