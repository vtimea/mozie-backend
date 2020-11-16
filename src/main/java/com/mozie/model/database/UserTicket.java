package com.mozie.model.database;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "tickets")
public class UserTicket {
    @Id
    @Column(name = "id")
    @NonNull
    private int id;

    @OneToOne
    @JoinColumn(name = "seat", nullable = false)
    private Seat seat;

    @Column(name = "purchase_timestamp")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime purchasedOn;

    @ManyToOne
    @JoinColumn(name = "ticket_type", nullable = false)
    private TicketType ticketType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private DbTransaction dbTransaction;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public LocalDateTime getPurchasedOn() {
        return purchasedOn;
    }

    public void setPurchasedOn(LocalDateTime purchasedOn) {
        this.purchasedOn = purchasedOn;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DbTransaction getTransaction() {
        return dbTransaction;
    }

    public void setTransaction(DbTransaction dbTransaction) {
        this.dbTransaction = dbTransaction;
    }
}
