package com.carcaret;

import org.junit.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class JoinerTest {

    @Test
    public void joinTestEqualLength() {
        List<Entity1> e1 = new ArrayList<Entity1>() {{
            add(new Entity1(1L, "1"));
            add(new Entity1(2L, "2"));
            add(new Entity1(3L, "3"));
        }};

        List<Entity2> e2 = new ArrayList<Entity2>() {{
            add(new Entity2(1L, "1"));
            add(new Entity2(2L, "2"));
            add(new Entity2(3L, "3"));
        }};
        Joiner<Entity1, Entity2, Entity3> joiner = new Joiner<>(e1, e2);
        List<Entity3> e3 = joiner.join(merger);
        Assert.assertEquals(3, e3.size());
    }

    @Test
    public void joinTestFirstLonger() {
        List<Entity1> e1 = new ArrayList<Entity1>() {{
            add(new Entity1(1L, "1"));
            add(new Entity1(2L, "2"));
            add(new Entity1(3L, "3"));
        }};

        List<Entity2> e2 = new ArrayList<Entity2>() {{
            add(new Entity2(1L, "1"));
            add(new Entity2(2L, "2"));
        }};
        Joiner<Entity1, Entity2, Entity3> joiner = new Joiner<>(e1, e2);
        List<Entity3> e3 = joiner.join(merger);
        Assert.assertEquals(2, e3.size());
    }

    @Test
    public void joinTestSecondLonger() {
        List<Entity1> e1 = new ArrayList<Entity1>() {{
            add(new Entity1(1L, "1"));
            add(new Entity1(2L, "2"));
        }};

        List<Entity2> e2 = new ArrayList<Entity2>() {{
            add(new Entity2(1L, "1"));
            add(new Entity2(2L, "2"));
            add(new Entity2(3L, "3"));
        }};
        Joiner<Entity1, Entity2, Entity3> joiner = new Joiner<>(e1, e2);
        List<Entity3> e3 = joiner.join(merger);
        Assert.assertEquals(2, e3.size());
    }

    private BiFunction<Entity1, Entity2, Entity3> merger = (e1, e2) -> new Entity3(e1.getId(), e1.getField(), e2.getField());
}
