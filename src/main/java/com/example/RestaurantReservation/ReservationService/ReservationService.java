package com.example.RestaurantReservation.ReservationService;
import com.example.RestaurantReservation.DTO.ReservationRequesValidation;
import com.example.RestaurantReservation.Reservation;
import com.example.RestaurantReservation.ReservationRepository.ReservationRepository;
import jakarta.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationService implements IReservationService {
    @Autowired
    private final ReservationRepository reservationRepository;
    //private final Validator validator;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
        //ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        //this.validator = factory.getValidator();
        // since I am sing @Valid in controller
        // it automatically validates Reservation data. So no need of above two lines
    }

    @Override
    public Reservation createReservation(ReservationRequesValidation request) {
        Reservation reservation = new Reservation();
        reservation.setCustomerName(request.getCustomerName());
        reservation.setCustomerEmail(request.getCustomerEmail());
        reservation.setGuests(request.getGuests());
        reservation.setReservationDate(request.getReservationDate());
        reservation.setSpecialRequest(request.getSpecialRequest());

        validateBusinessRules(reservation);

       return reservationRepository.save(reservation);

    }

    @Transactional(readOnly = true)
    @Override
    public List<Reservation> findAllByOrderByReservationDateDesc() {
        List<Reservation> res = reservationRepository.findAllByOrderByReservationDateDesc();
        return res;
        //throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public Reservation updateReservation(Long id, ReservationRequesValidation request){
        Reservation existing =  reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation with ID: " + id + " not found"));

        existing.setCustomerName(request.getCustomerName());
        existing.setCustomerEmail(request.getCustomerEmail());
        existing.setReservationDate(request.getReservationDate());
        existing.setGuests(request.getGuests());
        existing.setSpecialRequest(request.getSpecialRequest());

        validateBusinessRules(existing);

        return reservationRepository.save(existing);
    }

    @Override
    public boolean deleteReservation(Long id) {
        if(reservationRepository.existsById(id)){
            reservationRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }

    }
//    private void validateReservation(Reservation reservation) {
//        Set<ConstraintViolation<Reservation>> violations = validator.validate(reservation);
//        if (!violations.isEmpty()) {
//            throw new IllegalArgumentException(
//                    violations.stream()
//                            .map(ConstraintViolation::getMessage)
//                            .collect(Collectors.joining(", "))
//            );
//        }
//    }
    //I am using @Valid So no need of this

    private void validateBusinessRules(Reservation reservation) {
        //reservation date cannot be past
        if(reservation.getReservationDate().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("reservationDate must be a Future date");
        }
        //No reservations on Mondays
        if(reservation.getReservationDate().getDayOfWeek() == DayOfWeek.MONDAY){
            throw new IllegalArgumentException("No reservations on Mondays");
        }
        //Reservations must be between 10AM and 10PM
        LocalDateTime reservationTime = reservation.getReservationDate();
        LocalTime resTime = reservationTime.toLocalTime();

        LocalTime open = LocalTime.of(10, 0);
        LocalTime close = LocalTime.of(22,0);
        if(resTime.isBefore(open) || resTime.isAfter(close)){
            throw new IllegalArgumentException("reservations must be between 10AM and 10PM");
        }

        //special request is needed for groups larger than 6 guests
        if(reservation.getGuests() >= 6 && reservation.getSpecialRequest() == null){
            throw new IllegalArgumentException("special request is required for groups larger than 6 guests");
        }
    }
}