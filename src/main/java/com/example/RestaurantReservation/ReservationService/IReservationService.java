package com.example.RestaurantReservation.ReservationService;

import com.example.RestaurantReservation.DTO.ReservationRequesValidation;
import com.example.RestaurantReservation.Reservation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IReservationService {

        Reservation createReservation(ReservationRequesValidation request);

        //List<Reservation> getAllReservations();

    @Transactional(readOnly = true)
    List<Reservation> findAllByOrderByReservationDateDesc();

    Optional<Reservation> getReservationById(Long id);

        Reservation updateReservation(Long id, ReservationRequesValidation request);

        boolean deleteReservation(Long id);

}
