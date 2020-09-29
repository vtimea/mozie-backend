package com.mozie.model.database;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cinemas")
public class Cinema {
    @Id
    @Column(name = "id")
    @NonNull
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    public Cinema() {
    }

    public Cinema(@NonNull String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
