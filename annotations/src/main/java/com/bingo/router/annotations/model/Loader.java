package com.bingo.router.annotations.model;

import java.util.Map;

public interface Loader<T> {
    void load(Map<String, T> map);
}
