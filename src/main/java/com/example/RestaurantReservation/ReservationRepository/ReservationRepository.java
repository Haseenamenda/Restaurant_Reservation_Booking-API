package com.example.RestaurantReservation.ReservationRepository;

import com.example.RestaurantReservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

     //Reservation updateById(Long id, Reservation reservation);
     List<Reservation> findAllByOrderByReservationDateDesc();

}
