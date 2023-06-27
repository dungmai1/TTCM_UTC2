package com.maidanhdung.ecommerce.models;

public class ResultWrapper {
    public Province[] results;
    public ResultWrapper(Province[] results) {
        this.results = results;
    }
    public Province[] getResults() {
        return results;
    }

    public void setResults(Province[] results) {
        this.results = results;
    }


}
