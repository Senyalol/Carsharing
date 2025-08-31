//package com.Car.Carservice.Entity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.Instant;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "reservations")
//public class Reservation {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservations_id_gen")
//    @SequenceGenerator(name = "reservations_id_gen", sequenceName = "reservations_reservation_id_seq", allocationSize = 1)
//    @Column(name = "reservation_id", nullable = false)
//    private Integer id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "car_id")
//    private Car car;
//
//    @NotNull
//    @Column(name = "start_time", nullable = false)
//    private Instant startTime;
//
//    @NotNull
//    @Column(name = "end_time", nullable = false)
//    private Instant endTime;
//
//    @Column(name = "status")
//    private Boolean status;
//
//}