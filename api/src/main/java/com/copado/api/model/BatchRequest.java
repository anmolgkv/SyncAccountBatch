package com.copado.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class BatchRequest {
    @Size(min = 18, max = 18, message = "Invalid Id")
    private String orgId;

    @Size(min = 18, max = 18, message = "Invalid Id")
    private String userId;

    @NotEmpty @Valid
    private List<Account> accounts;

    public BatchRequest(@JsonProperty("orgId") String orgId,
                        @JsonProperty("userId") String userId,
                        @JsonProperty("accounts") List<Account> accounts) {
        this.orgId = orgId;
        this.userId = userId;
        this.accounts = accounts;
    }

    public String getTaskId() {
        return orgId + '-' + userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
