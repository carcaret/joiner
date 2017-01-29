package com.carcaret;

import java.util.Optional;

public class ReflectionWrapper {

    public final Object entity;

    public ReflectionWrapper(Object entity) {
        this.entity = entity;
    }

    public Object get(String field) {
        if(entity == null) {
            return Optional.empty();
        }
        return ReflectionUtil.get(entity, field);
    }
}
