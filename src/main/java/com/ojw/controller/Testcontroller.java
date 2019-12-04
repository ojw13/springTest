package com.ojw.controller;

import com.ojw.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Ojw
 * @date 2019/12/4 0004
 */
@Controller
@RequestMapping("/test")
public class Testcontroller {

    @Autowired
    private TestService testService;

    @RequestMapping("/t1")
    @ResponseBody
    public String testList(){
        testService.findTestList();
        return testService.findTestList().toString();
    }
}
