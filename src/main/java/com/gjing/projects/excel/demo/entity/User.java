package com.gjing.projects.excel.demo.entity;

import cn.gjing.tools.excel.Excel;
import cn.gjing.tools.excel.ExcelField;
import cn.gjing.tools.excel.convert.ExcelDataConvert;
import cn.gjing.tools.excel.metadata.ExcelType;
import cn.gjing.tools.excel.read.valid.ExcelAssert;
import cn.gjing.tools.excel.write.valid.ExcelDropdownBox;
import cn.gjing.tools.excel.write.valid.ExcelNumericValid;
import cn.gjing.tools.excel.write.valid.ValidType;
import com.gjing.projects.excel.demo.enums.Gender;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Gjing
 **/
@Excel(type = ExcelType.XLSX)
@Data
@Entity
@Table(name = "customer")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ExcelField("姓名")
    @ExcelAssert(expr = "#userName != null")
    @Column(name = "user_name", columnDefinition = "varchar(20)")
    private String userName;

    @ExcelField(value = "年龄", format = "0")
    @ExcelNumericValid(validType = ValidType.INTEGER, expr1 = "100", errorContent = "年龄不能超过100")
    @Column(name = "user_age", columnDefinition = "tinyint(2)")
    private Integer userAge;

    @ExcelField("性别")
    @ExcelDropdownBox(combobox = {"男", "女"})
    @ExcelDataConvert(expr1 = "#gender.desc", expr2 = "T(com.gjing.projects.excel.demo.enums.Gender).of(#gender)")
    @Column(name = "gender", columnDefinition = "tinyint(2)")
    @Convert(converter = Gender.GenderConvert.class)
    private Gender gender;

    @ExcelField("爱好")
    @ExcelDropdownBox(link = "2")
    private String favorite;

    public User() {
    }

    public User(String userName, Integer userAge, Gender gender) {
        this.userAge = userAge;
        this.userName = userName;
        this.gender = gender;
    }
}

