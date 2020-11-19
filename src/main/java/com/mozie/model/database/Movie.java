package com.mozie.model.database;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @Column(name = "id")
    @NonNull
    private String id;  // CC id

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "length")
    private int length;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "actors", length = 2000)
    private String actors;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(columnDefinition = "ENUM('UNRELEASED','RELEASED','ARCHIVED')")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        UNRELEASED,
        RELEASED,
        ARCHIVED
    }
}
