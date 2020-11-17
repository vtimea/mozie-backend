package com.mozie.utils;

public class ErrorResponses {
    public static ErrorResponse NO_SUCH_USER(String userId) {
        return new ErrorResponse(10, "User (" + userId + ") doesn't exist.");
    }

    public static ErrorResponse NO_SUCH_TICKET_TYPE(int ticketType) {
        return new ErrorResponse(11, "Ticket type (" + ticketType + ") doesn't exist.");
    }

    public static ErrorResponse NO_SUCH_SEAT(int seatId) {
        return new ErrorResponse(12, "Seat (" + seatId + ") doesn't exist.");
    }

    public static ErrorResponse INVALID_AMOUNT_TICKET = new ErrorResponse(13, "Invalid amount or ticket types");

    public static ErrorResponse INVALID_TICKET_OR_SEATS = new ErrorResponse(14, "Invalid ticket types and seats.");

    public static ErrorResponse SEAT_UNAVAILABLE(int seatId) {
        return new ErrorResponse(15, "Seat(" + seatId + ") not available.");
    }
}

