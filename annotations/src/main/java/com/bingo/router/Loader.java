package com.bingo.router;

import java.util.Map;

public interface Loader<T> {
    void load(Map<String, T> map);
}
