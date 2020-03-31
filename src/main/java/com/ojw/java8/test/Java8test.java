package com.ojw.java8.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Administrator on 2020/3/31 0031.
 */
public class Java8test {

    @Test
    public void test1() throws Exception{
        TestBean testBean = TestBean.builder()
                .age(1)
                .name("张三")
                .sex("女")
                .uid(1)
                .build();
        System.out.println(testBean);

    }
    
    /**
     * 获取属性名数组
     * */
    private static Object getFiledName(Object o) throws Exception {
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
