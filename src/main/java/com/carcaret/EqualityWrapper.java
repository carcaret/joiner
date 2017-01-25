package com.carcaret;

import java.util.Objects;
import java.util.function.Function;

public class EqualityWrapper<T, U> {
    private T t;
    private Function<T, U> equalityFunction;

    public EqualityWrapper(T t, Function<T, U> equalityFunction) {
        this.t = t;
        this.equalityFunction = equalityFunction;
    }

    public T unwrap() {
        return this.t;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        @SuppressWarnings("unchecked")
        EqualityWrapper<T, U> that = (EqualityWrapper<T, U>) o;
        return Objects.equals(equalityFunction.apply(this.t), that.equalityFunction.apply(that.t));
    }

    @Override
    public int hashCode() {
        return Objects.hash(equalityFunction.apply(this.t));
    }
}
