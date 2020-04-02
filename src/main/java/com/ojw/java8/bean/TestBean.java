package com.ojw.java8.bean;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author ojw
 * @date 2020/3/31 0031
 */
@Data
@ToString
@Builder
public class TestBean {
    private Integer uid;
    private String name;
    private String sex;
    private Integer age;
    private Integer page = 1;
    private Integer pageSize = 10;
}
