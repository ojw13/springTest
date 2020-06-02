package com.ojw.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2020/5/9 0009.
 */
@Retention(RetentionPolicy.RUNTIME)
// 表明该注解只能放在类的字段上
@Target({ElementType.FIELD})
public @interface ResultCode {
    // 响应码code
    int value() default 100000;
    // 响应信息msg
    String message() default  "参数校验错误";
}
