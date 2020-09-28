package com.mozie.model.api.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GranularScope {
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private String scope;
    private List<String> targetIds = null;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<String> getTargetIds() {
        return targetIds;
    }

    public void setTargetIds(List<String> targetIds) {
        this.targetIds = targetIds;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
