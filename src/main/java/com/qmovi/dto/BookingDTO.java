package com.qmovi.dto;

import jakarta.validation.constraints.NotNull;

public class BookingDTO {

    @NotNull(message = "Show ID is required")
    private Long showId;

    @NotNull(message = "Seat number is required")
    private Integer seatNumber;

    public BookingDTO() {
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }
}