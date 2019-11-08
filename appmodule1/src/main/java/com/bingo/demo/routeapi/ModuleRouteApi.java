package com.bingo.demo.routeapi;

import com.bingo.router.annotations.Parameter;
import com.bingo.router.annotations.Route;

public interface ModuleRouteApi {

    @Route("app/read")
    void goRead(@Parameter String title);

    @Route("test/user/detail")
    void goUserDetail(@Parameter long id);
}
