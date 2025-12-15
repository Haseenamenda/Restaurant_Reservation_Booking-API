package com.example.RestaurantReservation.ReservationControlller;
import com.example.RestaurantReservation.DTO.ReservationRequesValidation;
import com.example.RestaurantReservation.Reservation;
import com.example.RestaurantReservation.ReservationService.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {
    @Autowired
    private final ReservationService reservationService;


    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationRequesValidation request) {
        Reservation saved = reservationService.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        //we have different types to return Response with Response code
        // return ResponseEntity.status(HttpStatus.CREATED).body();
        //return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.findAllByOrderByReservationDateDesc());
        //throw new UnsupportedOperationException("Not implemented");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable Long id) {
        Optional<Reservation> res = reservationService.getReservationById(id);
        if(res.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(res.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation with ID " + id + " not found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable Long id, @Valid @RequestBody ReservationRequesValidation request) {

            Reservation updated = reservationService.updateReservation(id, request);
            return ResponseEntity.status(200).body(updated);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        boolean res = reservationService.deleteReservation(id);
        if(res){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation with ID" + id + "not found");
    }
}