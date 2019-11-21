package com.bingo.demo.model;

import java.util.List;

public class Gank {

    /**
     * error : false
     * results : []
     */

    private boolean error;
    private List<Result> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
