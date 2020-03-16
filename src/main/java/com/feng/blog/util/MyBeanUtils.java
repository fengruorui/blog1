package com.feng.blog.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng on 2020/3/13.
 */
public class MyBeanUtils {
    /**
     * 获取所有的属性值为空属性名数组
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source){
        BeanWrapper beanWrapper=new BeanWrapperImpl(source);
        PropertyDescriptor[] pds=beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames= new ArrayList<>();
        for (PropertyDescriptor pd : pds) {
            String propertyNames=pd.getName();
            if (beanWrapper.getPropertyValue(propertyNames) == null){
                nullPropertyNames.add(propertyNames);
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);

    }
}
