package com.bingo.demo.approuterpath;

import com.bingo.router.IRoute;
import com.bingo.router.Request;
import com.bingo.router.annotations.Parameter;
import com.bingo.router.annotations.PathClass;
import com.bingo.router.annotations.Route;

public interface User {
    @PathClass("/user/detail")
    public interface Detail {
        @Route(pathClass = Detail.class)
        Request getDetail(@Parameter("id") String id);

        @Route(pathClass = Detail.class)
        IRoute getDetail(@Parameter String id, @Parameter HomeParams params);

        @Route(pathClass = Detail.class)
        void openDetail(@Parameter String id);

    }
}
