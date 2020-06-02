package com.java8.test;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ojw
 * @date 2020/3/31 0031
 */
public class Java8test {

    @Test
    public void test1() throws Exception{
        LocalDateTime ldt = LocalDateTime.now(ZoneOffset.of("+8"));
        System.out.println(LocalDateTime.now().minusMinutes(-5).toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println(System.currentTimeMillis());
        System.out.println(LocalDate.now().toString());


    }
    
    /**
     *  必须得有get  set 方法  lomBook 不行
     * 获取class 属性信息
     * */
    private static Map<String, Object> getFiledName(Object o) throws Exception {
        Map<String,Object> map = new HashMap<>();
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        Object[] fieldValues=new Object[fields.length];
        Object[] fieldType=new Object[fields.length];
        for(int i=0;i<fields.length;i++){
            fieldType[i]=fields[i].getType();
            fieldNames[i]=fields[i].getName();
            Method method = o.getClass().getMethod("get"+fieldNames[i].substring(0, 1).toUpperCase()+fieldNames[i].substring(1).toString(),new Class[] {});
            fieldValues[i]=method.invoke(o, new Object[] {});
            System.out.println(fieldType[i]);
            System.out.println(fieldNames[i]);
            System.out.println(fieldValues[i]);
        }
        map.put("fieldType",fieldType);
        map.put("fieldNames",fieldNames);
        map.put("fieldValues",fieldValues);
        return map;
    }
}
