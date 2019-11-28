package com.bingo.libpublic.adapter_livedata;

public class Result<R> {
    private R body;
    private Throwable t;

    public Result(R body) {
        this.body = body;
    }

    public Result(Throwable t) {
        this.t = t;
    }

    public R getBody() {
        return body;
    }

    public Throwable getT() {
        return t;
    }
}
