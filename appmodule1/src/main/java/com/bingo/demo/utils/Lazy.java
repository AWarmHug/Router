package com.bingo.demo.utils;

@FunctionalInterface
public interface Lazy<T> {
    T get();
}