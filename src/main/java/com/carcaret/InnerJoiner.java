package com.carcaret;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class InnerJoiner<U, V, K> implements Joiner<K> {

    private final Iterable<U> e1;
    private final Iterable<V> e2;

    public InnerJoiner(Iterable<U> e1, Iterable<V> e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public List<K> joinInto(Class<K> clazz) {
        Map<Object, U> map = toIdMap(e1);
        return join(map, clazz);
    }

    private List<K> join(Map<Object, U> map, Class<K> clazz) {
        Merger<U, V, K> merger = new Merger<>(clazz);
        return stream(e2)
                .map(e -> {
                    Object key = ReflectionUtil.getId(e);
                    U other = map.get(key);
                    if(other != null) {
                        return merger.merge(other, e);
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private <K> Map<Object, K> toIdMap(Iterable<K> it) {
        return stream(it)
                .map(e -> new EqualityWrapper<>(e, ReflectionUtil::getId))
                .distinct()
                .map(EqualityWrapper::unwrap)
                .collect(Collectors.toMap(ReflectionUtil::getId, Function.identity()));
    }

    private <K> Stream<K> stream(Iterable<K> it) {
        return StreamSupport.stream(it.spliterator(), false);
    }
}
