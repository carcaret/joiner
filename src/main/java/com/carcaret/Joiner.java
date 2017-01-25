package com.carcaret;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Joiner<T, U, V> {

    private final Iterable<T> e1;
    private final Iterable<U> e2;

    public Joiner(Iterable<T> e1, Iterable<U> e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public List<V> join(BiFunction<T, U, V> merger) {
        Map<Object, T> map = toIdMap(e1);
        return join(map, merger);
    }

    private List<V> join(Map<Object, T> map, BiFunction<T, U, V> merger) {
        return stream(e2)
                .map(e -> {
                    Object key = ReflectionUtil.getId(e);
                    T other = map.get(key);
                    if(other != null) {
                        return merger.apply(other, e);
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
