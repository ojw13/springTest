package com.ojw.bean.vo;

import com.ojw.annotation.ResultCode;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author ojw
 * @date 2020/3/31 0031
 */
@Data
@ToString
public class TestBean {
//    @NotNull(message = "用户id不能为空")
//    @ResultCode
//    private Integer uid;
//    @Pattern(regexp = "([A-Za-z0-9]{4,20})|([\\u4e00-\\u9fa5]{2,10})|([\\u4e00-\\u9fa5][\\w\\W]{2})",message = "名称不能太短，三个汉字或者五个英文")
//    @ResultCode(value = 4003,message = "参数错误")
//    private String name;
//    @Pattern(regexp = "男|女",message = "用户性别只能是男和女")
//    private String sex;
//    @Max(value = 150,message = "年龄最大值是150")
//    @Min(value = 1,message = "年龄最小是1")
    private Integer age;
    private Integer page = 1;
    private Integer pageSize = 10;
}
