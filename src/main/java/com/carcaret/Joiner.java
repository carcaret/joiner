package com.carcaret;

import java.util.List;

@FunctionalInterface
public interface Joiner<K> {

    List<K> joinInto(Class<K> clazz);
}
