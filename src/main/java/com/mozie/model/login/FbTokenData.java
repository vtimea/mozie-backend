package com.mozie.model.login;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FbTokenData {
    @JsonProperty("app_id")
    private String appId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("application")
    private String application;

    @JsonProperty("data_access_expires_at")
    private Integer dataAccessExpiresAt;

    @JsonProperty("expires_at")
    private Integer expiredAt;

    @JsonProperty("is_valid")
    private Boolean isValid;

    @JsonProperty("scopes")
    private List<String> scopes = null;

    @JsonProperty("granularScopes")
    private List<GranularScope> granularScopes = null;

    @JsonProperty("user_id")
    private String userId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public Integer getDataAccessExpiresAt() {
        return dataAccessExpiresAt;
    }

    public void setDataAccessExpiresAt(Integer dataAccessExpiresAt) {
        this.dataAccessExpiresAt = dataAccessExpiresAt;
    }

    public Integer getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Integer expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public List<GranularScope> getGranularScopes() {
        return granularScopes;
    }

    public void setGranularScopes(List<GranularScope> granularScopes) {
        this.granularScopes = granularScopes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
