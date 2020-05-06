package com.maps.model;

public enum  MapType {
    G_MONEY("G_MONEY");

    final private String code;

    private MapType(String code) { 
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
