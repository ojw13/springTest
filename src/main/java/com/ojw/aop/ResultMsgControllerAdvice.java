package com.ojw.aop;

import com.ojw.annotation.ResultCode;
import com.ojw.java8.bean.vo.ResultVO;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;

/**
 * 定义范围异常捕获处理返回
 * @author ojw
 * @date 2020/5/8 0008
 */
@RestControllerAdvice(basePackages = {"com.ojw.controller"})
public class ResultMsgControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<Object> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e)throws NoSuchFieldException  {
//        // 从异常对象中拿到ObjectError对象
//        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
//        // 然后提取错误提示信息进行返回
//        return objectError.getDefaultMessage();

        // 从异常对象中拿到错误信息
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        // 参数的Class对象，等下好通过字段名称获取Field对象
        Class<?> parameterType = e.getParameter().getParameterType();
        // 拿到错误的字段名称
        String fieldName = e.getBindingResult().getFieldError().getField();
        Field field = parameterType.getDeclaredField(fieldName);
        // 获取Field对象上的自定义注解
        ResultCode annotation = field.getAnnotation(ResultCode.class);

        // 有注解的话就返回注解的响应信息
        if (annotation != null) {
            return new ResultVO<>(annotation.value(),annotation.message(),defaultMessage);
        }
        // 没有注解就提取错误提示信息进行返回统一错误码
        return new ResultVO<>();
    }
}
