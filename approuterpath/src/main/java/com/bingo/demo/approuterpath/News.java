package com.bingo.demo.approuterpath;

import com.bingo.router.annotations.PathClass;

@PathClass("news")
public class News {

    @PathClass("news/home")
    public static class Home{

    }

}
