package com.bingo.demo.model;

import java.util.List;

public class Gank<R> {

    /**
     * error : false
     * results : []
     */

    private boolean error;
    private R results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public R getResults() {
        return results;
    }

    public void setResults(R results) {
        this.results = results;
    }
}
