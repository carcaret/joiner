package com.carcaret;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Merger<U, V, K> {

    private final Class<K> clazz;

    public Merger(Class<K> clazz) {
        this.clazz = clazz;
    }

    public K merge(U e1, V e2) {
        if(e1 == null) {
            throw new NullPointerException("Fist element in merger cannot be null");
        }
        K instance = ReflectionUtil.newInstance(clazz);
        instance = fill(instance, e1);
        instance = fill(instance, e2);
        return instance;
    }

    private K fill(K target, Object source) {
        if(source == null) {
            return target;
        }
        Field[] fields = ReflectionUtil.fields(source);
        if(fields != null && fields.length > 0) {
            Arrays.stream(fields).forEach(field -> {
                Object value = ReflectionUtil.get(source, field.getName());
                ReflectionUtil.set(target, field.getName(), value);
            });
        }
        return target;
    }
}
