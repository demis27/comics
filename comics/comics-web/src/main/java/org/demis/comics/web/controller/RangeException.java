package org.demis.comics.web.controller;

public class RangeException extends Exception {

    private final String reason;

    public RangeException(String reason) {
        this.reason = reason;
    }


    public String getReason() {
        return reason;
    }


}