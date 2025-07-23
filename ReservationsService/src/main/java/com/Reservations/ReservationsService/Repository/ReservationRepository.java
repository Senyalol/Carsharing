package com.Reservations.ReservationsService.Repository;

import com.Reservations.ReservationsService.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Reservation findById(int id);
    List<Reservation> findByUserId(int userId);
    List<Reservation> findByCarId(int carId);
    List<Reservation> findByStatus(boolean status);

}
