package com.example.RestaurantReservation.DTO;


import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class ReservationRequesValidation {

    @NotBlank(message = "Customer name is required")
    @Size(min = 1, max = 100, message = "Name must be in 1 to 100 characters")
    private String customerName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String customerEmail;

    @NotNull(message = "Guests is required")
    @Min(value = 1, message = "Minimum 1 guest required")
    @Max(value = 12, message = "maximum 12 guests allowed")
    private Integer guests;

    @NotNull(message = "Reservation date is required")
    private LocalDateTime reservationDate;

    private String specialRequest;

    //getters and setters

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public int getGuests() { return guests; }

    public void setGuests(int guests) {this.guests = guests;}

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }
}
