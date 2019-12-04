package com.ojw.mapper;

import com.ojw.bean.po.Test;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * @author Ojw
 * @date 2019/12/4 0004
 */
@Mapper
public interface TestMapper {

    @Select("select * from test limit 100")
    List<Test> findTestList();
}
