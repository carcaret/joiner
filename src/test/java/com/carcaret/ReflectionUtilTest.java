package com.carcaret;

import org.junit.Assert;
import org.junit.Test;


public class ReflectionUtilTest {

    @Test
    public void getAnnotatedField() {
        Entity1 entity1 = new Entity1(1L, "1");
        Object result = ReflectionUtil.getId(entity1);
        Assert.assertTrue(result instanceof Long);
        Assert.assertEquals(1L, result);
    }
}
