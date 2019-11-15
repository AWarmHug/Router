package com.bingo.demo.routeapi;

import com.bingo.router.Request;
import com.bingo.router.annotations.Parameter;
import com.bingo.router.annotations.Route;

public interface ModuleRouteApi2 {

    @Route("app/read")
    Request goRead(@Parameter String title);

    @Route("test/user/detail")
    Request goUserDetail(@Parameter long id);

}
