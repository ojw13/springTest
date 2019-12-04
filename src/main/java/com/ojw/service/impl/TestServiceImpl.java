package com.ojw.service.impl;

import com.ojw.bean.po.Test;
import com.ojw.mapper.TestMapper;
import com.ojw.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2019/12/4 0004.
 */
@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Resource
    private TestMapper testMapper;

    /**
     * 测试
     * @return
     */
    @Override
    public List<Test> findTestList(){
        return testMapper.findTestList();
    }
}
