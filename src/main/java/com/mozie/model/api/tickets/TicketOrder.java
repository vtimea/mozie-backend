package com.mozie.model.api.tickets;

import java.util.List;

public class TicketOrder {
    private String userId;
    private List<Integer> ticketTypes;
    private List<Integer> seats;
    private int sumAmount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Integer> getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(List<Integer> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }

    public int getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(int sumAmount) {
        this.sumAmount = sumAmount;
    }
}
