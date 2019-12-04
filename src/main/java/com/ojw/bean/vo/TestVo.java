package com.ojw.bean.vo;

import lombok.Data;
import lombok.ToString;

/**
 *
 * @author Ojw
 * @date 2019/12/4 0004
 */
@Data
@ToString
public class TestVo {
    private Integer testId;
    private String testName;
    private Integer testType;
    private String testContent;
    private String testCreateTime;
}
