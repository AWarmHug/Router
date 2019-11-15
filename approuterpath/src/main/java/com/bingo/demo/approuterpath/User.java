package com.bingo.demo.approuterpath;

import com.bingo.router.Request;
import com.bingo.router.annotations.Parameter;
import com.bingo.router.annotations.PathClass;
import com.bingo.router.annotations.Route;

@PathClass("user")
public class User {
    @PathClass("user/detail")
    public interface Detail {
        @Route(pathClass = Detail.class)
        Request getDetail(@Parameter long id);
    }
}
