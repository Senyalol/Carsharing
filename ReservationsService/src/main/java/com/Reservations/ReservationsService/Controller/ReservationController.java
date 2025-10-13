package com.Reservations.ReservationsService.Controller;

import com.Reservations.ReservationsService.DTO.ShortReservationInfoDTO;
import com.Reservations.ReservationsService.Entity.Reservation;
import com.Reservations.ReservationsService.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/booking")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //Показать все записи
    @GetMapping
    //@PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_ADMIN')")
    public List<ShortReservationInfoDTO> getAllBookings(){
        return reservationService.getAllReservations();
    }


    //Показать запись по его id
    @GetMapping("/findId/{id}")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ShortReservationInfoDTO findById(@PathVariable int id){
        return reservationService.findReservationById(id);
    }

    //Показати все записи пользователя
    @GetMapping("/findByUser/{id}")
    //@PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_ADMIN')")
    public List<ShortReservationInfoDTO> findByUser(@PathVariable int id){
        return reservationService.findReservationByUserId(id);
    }

    //Показать все записи где указан конкретный автомобиль
    @GetMapping("/findByCar/{id}")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<ShortReservationInfoDTO> findByCar(@PathVariable int id){
        return reservationService.findReservationByCarId(id);
    }

    //Показать все записи в соответствии со статусом
    @GetMapping("/findByStatus/{status}")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<ShortReservationInfoDTO> findByStatus(@PathVariable String status){
        return reservationService.findReservationByStatus(status);
    }

    //Сделать запись
    @PostMapping("/reservation")
    //@PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_ADMIN')")
    public Reservation bookingCar(@RequestBody ShortReservationInfoDTO reservationDTO){
        return reservationService.addReservation(reservationDTO);
    }

    //Редактировать запись
    @PatchMapping("/change/{id}")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Reservation changeReservation(@PathVariable int id, @RequestBody ShortReservationInfoDTO reservationDTO){
        return reservationService.changeReservationStatus(id, reservationDTO);
    }

    //Удалить запись
    @DeleteMapping("/remove/{id}")
    //@PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_ADMIN')")
    public void removeReservation(@PathVariable int id){
        reservationService.deleteReservationById(id);
    }

}