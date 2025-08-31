//package com.Car.Carservice.Entity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.LinkedHashSet;
//import java.util.Set;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "users")
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_gen")
//    @SequenceGenerator(name = "users_id_gen", sequenceName = "users_user_id_seq", allocationSize = 1)
//    @Column(name = "user_id", nullable = false)
//    private Integer id;
//
//    @Size(max = 40)
//    @NotNull
//    @Column(name = "username", nullable = false, length = 40)
//    private String username;
//
//    @Size(max = 100)
//    @NotNull
//    @Column(name = "firstname", nullable = false, length = 100)
//    private String firstname;
//
//    @Size(max = 100)
//    @NotNull
//    @Column(name = "lastname", nullable = false, length = 100)
//    private String lastname;
//
//    @Size(max = 150)
//    @NotNull
//    @Column(name = "passport_code", nullable = false, length = 150)
//    private String passportCode;
//
//    @Size(max = 15)
//    @Column(name = "phone_number", length = 15)
//    private String phoneNumber;
//
//    @Size(max = 255)
//    @NotNull
//    @Column(name = "password", nullable = false)
//    private String password;
//
//    @Size(max = 11)
//    @NotNull
//    @Column(name = "driver_license", nullable = false, length = 11)
//    private String driverLicense;
//
//    @Size(max = 250)
//    @Column(name = "imguser", length = 250)
//    private String imguser;
//
//    @OneToMany(mappedBy = "user")
//    private Set<Reservation> reservations = new LinkedHashSet<>();
//
//    @OneToMany(mappedBy = "user")
//    private Set<UserRole> userRoles = new LinkedHashSet<>();
//
//}