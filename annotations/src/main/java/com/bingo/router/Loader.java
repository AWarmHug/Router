package com.bingo.router;

import java.util.Map;

public interface Loader<K,V> {
    void load(Map<K, V> map);
}
