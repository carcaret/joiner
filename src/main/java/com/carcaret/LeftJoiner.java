package com.carcaret;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class LeftJoiner<U, V, K> implements Joiner<K> {

    private final Iterable<U> e1;
    private final Iterable<V> e2;

    public LeftJoiner(Iterable<U> e1, Iterable<V> e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public List<K> joinInto(Class<K> clazz) {
        Map<Object, V> map = toIdMap(e2);
        return join(map, clazz);
    }

    private List<K> join(Map<Object, V> map, Class<K> clazz) {
        Merger<U, V, K> merger = new Merger<>(clazz);
        return stream(e1)
                .map(e -> {
                    Object key = ReflectionUtil.getId(e);
                    V other = map.get(key);
                    return merger.merge(e, other);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private <T> Map<Object, T> toIdMap(Iterable<T> it) {
        return stream(it)
                .map(e -> new EqualityWrapper<>(e, ReflectionUtil::getId))
                .distinct()
                .map(EqualityWrapper::unwrap)
                .collect(Collectors.toMap(ReflectionUtil::getId, Function.identity()));
    }

    private <T> Stream<T> stream(Iterable<T> it) {
        return StreamSupport.stream(it.spliterator(), false);
    }
}
