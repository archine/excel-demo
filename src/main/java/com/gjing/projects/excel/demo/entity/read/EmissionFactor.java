package com.gjing.projects.excel.demo.entity.read;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 排放因子
 *
 * @author Gjing
 **/
@Getter
@Setter
@ToString
public class EmissionFactor {
    /**
     * 因子
     */
    private String factor;

    /**
     * 浓度
     */
    private BigDecimal mmol;
}
