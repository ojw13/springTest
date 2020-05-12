package com.ojw.controller;

import com.ojw.java8.bean.TestBean;
import com.ojw.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 *
 * @author Ojw
 * @date 2019/12/4 0004
 */
@RestController
@RequestMapping("/test")
public class Testcontroller {

    @Autowired
    private TestService testService;

    @RequestMapping("/t1")
    public String testList(@RequestBody @Valid TestBean testBean, BindingResult bindingResult)throws Exception{
        // 如果有参数校验失败，会将错误信息封装成对象组装在BindingResult里
        for (ObjectError error : bindingResult.getAllErrors()) {
            return error.getDefaultMessage();
        }
        throw new Exception("aldhfjaisdfhaskdj");
//        return "成功";
    }
}
