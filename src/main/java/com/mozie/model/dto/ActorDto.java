package com.mozie.model.dto;

public class ActorDto {
    String name;
    String profilePath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictueUrl() {
        return profilePath;
    }

    public void setPictueUrl(String profilePath) {
        this.profilePath = profilePath;
    }
}
