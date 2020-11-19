package com.mozie.model.database;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @Column(name = "id")
    @NonNull
    private int id;

    @Column(name = "col")
    private int col;

    @Column(name = "row_")
    private int row;

    @Column(name = "available")
    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    @Column(name = "room")
    private int room;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }
}
