package com.javalab.contacts.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class CustomReflectionUtil {

    private static final Logger logger = LogManager.getLogger(CustomReflectionUtil.class);

    private CustomReflectionUtil(){
    }

    public static Map<String,Object> getObjectFieldsWithValues(Object object){
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<String,Object> objectFieldValues = new HashMap<>();
        for (Field field : fields){
            field.setAccessible(true);
            try {
                objectFieldValues.put(field.getName(),field.get(object));
            } catch (IllegalAccessException e) {
                logger.error("{}",e.getMessage());
            }
        }
        return objectFieldValues;
    }
}
