package com.mozie.model.login;

import java.util.HashMap;
import java.util.Map;

public class FbToken {
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private FbTokenData data;

    public FbTokenData getData() {
        return data;
    }

    public void setData(FbTokenData data) {
        this.data = data;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
