package com.warm.demo.utils;

@FunctionalInterface
public  interface Lazy<T> {
    T get();
}