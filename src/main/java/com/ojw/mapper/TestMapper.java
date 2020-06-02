package com.ojw.mapper;

import com.ojw.bean.po.Test;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 *
 * @author Ojw
 * @date 2019/12/4 0004
 */
@Mapper
public interface TestMapper {

    List<Test> findAll();
}
