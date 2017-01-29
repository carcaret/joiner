package com.carcaret;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Merger<U, V, K> {

    private final Class<K> clazz;

    public Merger(Class<K> clazz) {
        this.clazz = clazz;
    }

    public K merge(U e1, V e2) {
        K instance = ReflectionUtil.newInstance(clazz);
        instance = fill(instance, e1);
        instance = fill(instance, e2);
        return instance;
    }

    private K fill(K target, Object source) {
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
