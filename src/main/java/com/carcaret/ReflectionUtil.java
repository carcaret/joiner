package com.carcaret;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class ReflectionUtil {

    static Object get(Object entity, String fieldName) {
        if(entity == null) {
            throw new NullPointerException("Entity cannot be null");
        }
        if(StringUtil.isNullOrEmpty(fieldName)) {
            throw new NullPointerException("Field cannot be null nor empty");
        }
        Optional<Field> field = field(entity, fieldName);
        if(!field.isPresent()) {
            throw new IllegalArgumentException("Field not found");
        }
        return getValue(entity, field.get());
    }

    static void set(Object entity, String fieldName, Object fieldValue) {
        if(entity == null) {
            throw new NullPointerException("Entity cannot be null");
        }
        if(StringUtil.isNullOrEmpty(fieldName)) {
            throw new NullPointerException("Field cannot be null nor empty");
        }
        Optional<Field> field = field(entity, fieldName);
        if(!field.isPresent()) {
            throw new IllegalArgumentException("Field not found");
        }
        setValue(entity, field.get(), fieldValue);
    }

    static Object getId(Object entity) {
        if(entity == null) {
            throw new NullPointerException("Entity cannot be null");
        }
        Field[] fields = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .toArray(Field[]::new);
        if(fields.length == 0) {
            throw new RuntimeException("No field annotated");
        }
        if(fields.length > 1) {
            throw new RuntimeException("Only one field can be annotated");
        }
        Field field  = fields[0];
        return getValue(entity, field);
    }

    static <K> K newInstance(Class<K> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("No empty-arg constructor", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Constructor not visible", e);
        }
    }

    static Field[] fields(Object entity) {
        if(entity == null) {
            throw new NullPointerException("Entity cannot be null");
        }
        return entity.getClass().getDeclaredFields();
    }

    private static Optional<Field> field(Object entity, String name) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.getName().equals(name))
                .findAny();
    }

    private static Object getValue(Object entity, Field field) {
        String getter = "get" + StringUtil.capitalFirst(field.getName());
        if(!methodExists(entity, getter)) {
            throw new RuntimeException("No getter");
        }
        Method method;
        try {
            method = entity.getClass().getMethod(getter);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("No getter with no arguments", e);
        }
        return invoke(entity, method, (Object[]) null);
    }

    private static void setValue(Object entity, Field field, Object value) {
        String setter = "set" + StringUtil.capitalFirst(field.getName());
        if(!methodExists(entity, setter)) {
            throw new RuntimeException("No setter");
        }
        Method method;
        try {
            method = entity.getClass().getMethod(setter, value.getClass());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("No setter", e);
        }
        invoke(entity, method, value);
    }

    private static boolean methodExists(Object object, String method) {
        return Arrays.stream(object.getClass().getDeclaredMethods())
             .anyMatch(m -> m.getName().equals(method));
    }

    private static Object invoke(Object entity, Method method, Object... args) {
        try {
            return method.invoke(entity, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Method not visible", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Exception in method", e);
        }
    }
}
