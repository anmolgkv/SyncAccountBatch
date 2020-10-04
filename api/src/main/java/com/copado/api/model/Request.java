package com.copado.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Request {

    private String orgId;
    private String userId;
    private List<Account> accounts;


    // CONSTRUCTOR

    public Request(String orgId, String userId) {
        this(orgId, userId, null);
    }


    public Request(@JsonProperty("orgId") String orgId, @JsonProperty("userId") String userId,  @JsonProperty(value = "accounts", required = false) List<Account> accounts) {
        this.orgId = orgId;
        this.userId = userId;
        this.accounts = accounts;
    }


    // PUBLIC

    public String getTaskId() {
        return orgId + '-' + userId;
    }


    public List<Account> getAccounts() {
        return accounts;
    }
}
