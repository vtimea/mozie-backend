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

    public String getPictureUrl() {
        return profilePath;
    }

    public void setPictureUrl(String profilePath) {
        this.profilePath = profilePath;
    }
}
