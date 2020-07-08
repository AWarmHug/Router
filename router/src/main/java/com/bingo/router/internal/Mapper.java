package com.bingo.router.internal;

import androidx.annotation.Nullable;

interface Mapper<K, V> {

    @Nullable
    V get(K k);

}
