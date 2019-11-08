package com.bingo.router.annotations.model;

import com.bingo.router.annotations.PathClass;

import org.junit.Assert;

public class UtilsTest {

    @org.junit.Test
    public void pathByPathClass() {
        String path = Utils.pathByPathClass(A.B.class);
        Assert.assertEquals(path, "a/b");
    }

    @PathClass("a")
    public class A {
        @PathClass("/b")
        public class B {

        }
    }

}