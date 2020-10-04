package com.copado.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

    private final String id;
    private String name;
    private String type;
    public String message;

    // CONSTRUCTOR

    public Account(@JsonProperty("Id") String id,
                   @JsonProperty("Name")String name,
                   @JsonProperty("Type")String type) {
        this(id, name, type, null);
    }


    public Account(String id, String name, String type, String message) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.message = message;
    }


    //PUBLIC

    public boolean hasError() {
        return name.contains("Fake");
    }


    public Account process() {
        message = (hasError()) ? "Fake account" : "Looks good";

        return new Account(id, name, type, message);
    }


    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getType() {
        return type;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setType(String type) {
        this.type = type;
    }
}
