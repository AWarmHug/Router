package com.bingo.demo.approuterpath;

import com.bingo.router.annotations.PathClass;

public interface News {

    @PathClass("/news/home")
    public static interface Home{

    }

}
