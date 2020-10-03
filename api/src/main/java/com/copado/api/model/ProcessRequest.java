package com.copado.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class ProcessRequest {
    @NotBlank
    private String orgId;
    @NotBlank
    private String userId;


    public ProcessRequest(@JsonProperty("orgId") String orgId,
                          @JsonProperty("userId") String userId) {
        this.orgId = orgId;
        this.userId = userId;
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
}
