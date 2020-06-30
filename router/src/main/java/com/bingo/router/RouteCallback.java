package com.bingo.router;

public interface RouteCallback {

    void onNoFound(Request request);

    void onSuccess(Request request);

    void onFail(Request request, Exception e);

}
