package com.warm.demo.routeapi;

import com.warm.router.annotations.Parameter;
import com.warm.router.annotations.Route;

public interface ModuleRouteApi {

    @Route("app/read")
    void goRead(@Parameter String title);

    @Route("test/user/detail")
    void goUserDetail(@Parameter long id);
}
