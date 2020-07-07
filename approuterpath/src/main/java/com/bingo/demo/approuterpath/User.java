package com.bingo.demo.approuterpath;

import android.content.Context;

import com.bingo.router.IRoute;
import com.bingo.router.Request;
import com.bingo.router.annotations.Parameter;
import com.bingo.router.annotations.PathClass;
import com.bingo.router.annotations.Route;

public class User {
    @PathClass("user/detail")
    public interface Detail {
        @Route(pathClass = Detail.class)
        Request getDetail(@Parameter String id);

        @Route(pathClass = Detail.class)
        IRoute getDetail(@Parameter String id, @Parameter HomeParams params);

        @Route(pathClass = Detail.class)
        void openDetail(Context context, @Parameter String id);

    }
}
