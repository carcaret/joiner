package com.carcaret;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LeftJoinerTest {

    @Test
    public void leftJoinTestEqualLength() {
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
        Joiner<Entity3> joiner = new LeftJoiner<>(e1, e2);
        List<Entity3> e3 = joiner.joinInto(Entity3.class);
        Assert.assertEquals(3, e3.size());
    }

    @Test
    public void leftJoinTestFirstLonger() {
        List<Entity1> e1 = new ArrayList<Entity1>() {{
            add(new Entity1(1L, "1"));
            add(new Entity1(2L, "2"));
            add(new Entity1(3L, "3"));
        }};

        List<Entity2> e2 = new ArrayList<Entity2>() {{
            add(new Entity2(1L, "1"));
            add(new Entity2(2L, "2"));
        }};
        Joiner<Entity3> joiner = new LeftJoiner<>(e1, e2);
        List<Entity3> e3 = joiner.joinInto(Entity3.class);
        Assert.assertEquals(3, e3.size());
    }

    @Test
    public void leftJoinTestSecondLonger() {
        List<Entity1> e1 = new ArrayList<Entity1>() {{
            add(new Entity1(1L, "1"));
            add(new Entity1(2L, "2"));
        }};

        List<Entity2> e2 = new ArrayList<Entity2>() {{
            add(new Entity2(1L, "1"));
            add(new Entity2(2L, "2"));
            add(new Entity2(3L, "3"));
        }};
        Joiner<Entity3> joiner = new LeftJoiner<>(e1, e2);
        List<Entity3> e3 = joiner.joinInto(Entity3.class);
        Assert.assertEquals(2, e3.size());
    }
}
