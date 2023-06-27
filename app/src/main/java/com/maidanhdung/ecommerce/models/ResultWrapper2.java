package com.maidanhdung.ecommerce.models;

public class ResultWrapper2 {
    public Ward[] results;

    public ResultWrapper2(Ward[] results) {
        this.results = results;
    }

    public Ward[] getResults() {
        return results;
    }

    public void setResults(Ward[] results) {
        this.results = results;
    }
}
