package com.ojw.bean.po;

import lombok.Data;
import lombok.ToString;

/**
 *  测试类
 *  test
 * @author ojw
 * @date 2019/12/4 0004
 */
@Data
@ToString
public class Test {
    private Integer testId;
    private String testName;
    private Integer testType;
    private String testContent;
    private String testCreateTime;
}
