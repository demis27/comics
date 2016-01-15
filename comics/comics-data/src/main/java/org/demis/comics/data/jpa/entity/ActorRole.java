package org.demis.comics.data.jpa.entity;

public enum ActorRole {

    W ("W"), // Writer
    A ("A"); // Artist

    private String code;

    ActorRole(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
