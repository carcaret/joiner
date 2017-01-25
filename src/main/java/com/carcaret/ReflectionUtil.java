package com.carcaret;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionUtil {

    public static Object getId(Object object) {
        if(object == null) {
            throw new NullPointerException("Entity cannot be null");
        }
        Field[] fields = Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .toArray(Field[]::new);
        if(fields.length == 0) {
            throw new RuntimeException("No field annotated");
        }
        if(fields.length > 1) {
            throw new RuntimeException("Only one field can be annotated");
        }
        Field field  = fields[0];
        String getter = "get" + StringUtil.capitalFirst(field.getName());
        if(!methodExists(object, getter)) {
            throw new RuntimeException("No getter");
        }
        Method method;
        try {
            method = object.getClass().getMethod(getter);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("No getter with no arguments", e);
        }
        Object result;
        try {
            result = method.invoke(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Getter not visible", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Exception in method", e);
        }
        return result;
    }

    private static boolean methodExists(Object object, String method) {
        return Arrays.stream(object.getClass().getDeclaredMethods())
             .anyMatch(m -> m.getName().equals(method));
    }
}
