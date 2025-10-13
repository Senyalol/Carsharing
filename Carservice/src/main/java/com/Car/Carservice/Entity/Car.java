package com.Car.Carservice.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

//import java.util.LinkedHashSet;
//import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cars_id_gen")
    @SequenceGenerator(name = "cars_id_gen", sequenceName = "cars_car_id_seq", allocationSize = 1)
    @Column(name = "car_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "make", nullable = false, length = 50)
    private String make;

    @Size(max = 100)
    @NotNull
    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Column(name = "year")
    private Integer year;

    @Size(max = 20)
    @NotNull
    @Column(name = "license_plate", nullable = false, length = 20)
    private String licensePlate;

    @Column(name = "availability")
    private Boolean availability;

    @Size(max = 100)
    @NotNull
    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Size(max = 300)
    @Column(name = "photo_url", length = 300)
    private String photoUrl;

    @Size(max = 40)
    @Column(name = "engine_type", length = 40)
    private String engineType;

    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "engine_volume")
    private Integer engineVolume;

    @Column(name = "max_speed")
    private Integer maxSpeed;

    @Size(max = 100)
    @Column(name = "gearbox_type", length = 100)
    private String gearboxType;

    @Size(max = 1000)
    @Column(name = "describe", length = 1000)
    private String describe;

    @Column(name = "horsep")
    private Integer horsep;

    @Column(name = "price_per_hour")
    private Double pricePerHour;

//    @OneToMany(mappedBy = "car")
//    private Set<Reservation> reservations = new LinkedHashSet<>();

}