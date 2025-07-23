package com.Reservations.ReservationsService.Controller;

import com.Reservations.ReservationsService.DTO.ShortReservationInfoDTO;
import com.Reservations.ReservationsService.Entity.Reservation;
import com.Reservations.ReservationsService.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ShortReservationInfoDTO> getAllBookings(){
        return reservationService.getAllReservations();
    }

    @GetMapping("/findId/{id}")
    public ShortReservationInfoDTO findById(@PathVariable int id){
        return reservationService.findReservationById(id);
    }

    @GetMapping("/findByUser/{id}")
    public List<ShortReservationInfoDTO> findByUser(@PathVariable int id){
        return reservationService.findReservationByUserId(id);
    }

    @GetMapping("/findByCar/{id}")
    public List<ShortReservationInfoDTO> findByCar(@PathVariable int id){
        return reservationService.findReservationByCarId(id);
    }

    @GetMapping("/findByStatus/{status}")
    public List<ShortReservationInfoDTO> findByStatus(@PathVariable String status){
        return reservationService.findReservationByStatus(status);
    }

    @PostMapping("/reservation")
    public Reservation bookingCar(@RequestBody ShortReservationInfoDTO reservationDTO){
        return reservationService.addReservation(reservationDTO);
    }

    @PatchMapping("/change/{id}")
    public Reservation changeReservation(@PathVariable int id, @RequestBody ShortReservationInfoDTO reservationDTO){
        return reservationService.changeReservationStatus(id, reservationDTO);
    }

    @DeleteMapping("/remove/{id}")
    public void removeReservation(@PathVariable int id){
        reservationService.deleteReservationById(id);
    }

}