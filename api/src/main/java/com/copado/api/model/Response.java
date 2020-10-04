package com.copado.api.model;

import java.util.List;

public class Response {

    private String status;
    private List<Account> accounts;


    // CONSTRUCTOR

    public Response(String status) {
        this.status = status;
    }


    public Response(String status, List<Account> accounts) {
        this.status = status;
        this.accounts = accounts;
    }


    // PUBLIC

    public String getStatus() {
        return status;
    }


    public List<Account> getAccounts() {
        return accounts;
    }
}
