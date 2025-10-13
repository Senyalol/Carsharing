package com.Reservations.ReservationsService.Service;

import com.Reservations.ReservationsService.Entity.Car;
import com.Reservations.ReservationsService.Entity.Reservation;
import com.Reservations.ReservationsService.Entity.User;
import com.Reservations.ReservationsService.Repository.CarRepository;
import com.Reservations.ReservationsService.Repository.UserRepository;
import dto.ShortCarInfoDTO;
import com.Reservations.ReservationsService.DTO.ShortReservationInfoDTO;
import com.Reservations.ReservationsService.Repository.ReservationRepository;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dto.ShortUserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@JsonSerialize
public class ReservationService {

    private final Map<Integer, Car> receivedKafkaCars = new HashMap<>();
    private final Map<Integer, User> receivedKafkaUsers = new HashMap<>();
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, CarRepository carRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    private ShortUserInfoDTO convertUserToDTO(User user) {

        ShortUserInfoDTO userDTO = new ShortUserInfoDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setFirstname(user.getFirstname());
        user.setLastname(user.getLastname());
        user.setPassportCode(user.getPassportCode());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setDriverLicense(user.getDriverLicense());
        user.setImguser(user.getImguser());

        return userDTO;
    }

    private ShortCarInfoDTO convertToDTOCar(Car car){

       ShortCarInfoDTO dto = new ShortCarInfoDTO();

        dto.setId(car.getId());
        dto.setMake(car.getMake());
        dto.setModel(car.getModel());
        dto.setYear(car.getYear());
        dto.setLicensePlate(car.getLicensePlate());
        dto.setAvailability(car.getAvailability());
        dto.setLocation(car.getLocation());
        dto.setPhotoUrl(car.getPhotoUrl());
        dto.setEngineType(car.getEngineType());
        dto.setNumberOfSeats(car.getNumberOfSeats());
        dto.setWeight(car.getWeight());
        dto.setEngineVolume(car.getEngineVolume());
        dto.setMaxSpeed(car.getMaxSpeed());
        dto.setGearboxType(car.getGearboxType());
        dto.setDescribe(car.getDescribe());
        dto.setHorsep(car.getHorsep());
        dto.setPricePerHour(car.getPricePerHour());

        return dto;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void saveReceivedUserKafka(User user) {
        userRepository.saveAndFlush(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void saveRecievedCarKafka(Car car){
        carRepository.saveAndFlush(car);
//        System.out.println(carDTO);
//        int CarId = carDTO.getId();
//
//        if(!carRepository.existsById(CarId)) {
//
//            try {

//                Car tempCar = new Car();
//
//                tempCar.setId(carDTO.getId());
//                tempCar.setMake(carDTO.getMake());
//                tempCar.setModel(carDTO.getModel());
//                tempCar.setYear(carDTO.getYear());
//                tempCar.setLicensePlate(carDTO.getLicensePlate());
//                tempCar.setAvailability(carDTO.getAvailability());
//                tempCar.setLocation(carDTO.getLocation());
//                tempCar.setPhotoUrl(carDTO.getPhotoUrl());
//                tempCar.setEngineType(carDTO.getEngineType());
//                tempCar.setNumberOfSeats(carDTO.getNumberOfSeats());
//                tempCar.setWeight(carDTO.getWeight());
//                tempCar.setEngineVolume(carDTO.getEngineVolume());
//                tempCar.setMaxSpeed(carDTO.getMaxSpeed());
//                tempCar.setGearboxType(carDTO.getGearboxType());
//                tempCar.setDescribe(carDTO.getDescribe());
//                tempCar.setHorsep(carDTO.getHorsep());
//                tempCar.setPricePerHour(carDTO.getPricePerHour());

//                carRepository.save(tempCar);
//
//                System.out.println("The car has been successfully received!");
//
//            } catch (Exception e) {
//                System.out.println("Error data in object" + e.getMessage());
//            }
//
//        }
//
//        else{
//            System.out.println("The car already exists!");
//        }
    }

    //Сделать бронь
    @Transactional
    public Reservation addReservation(ShortReservationInfoDTO shortReservationInfoDTO) {
        Reservation reservation = new Reservation();

       // reservation.setId(shortReservationInfoDTO.getId());

//        for(User user: receivedKafkaUsers.values()){
//            if(user.getId() == shortReservationInfoDTO.getUser_id()){
//                reservation.setUser(user);
//            }
//        }
//
//        for(Car car: receivedKafkaCars.values()){
//            if(car.getId() == shortReservationInfoDTO.getCar_id()){
//                reservation.setCar(car);
//            }
//        }

        int UserId = shortReservationInfoDTO.getUser_id();
        reservation.setUser(userRepository.findById(UserId).get());

        int CarId = shortReservationInfoDTO.getCar_id();
        reservation.setCar(carRepository.findById(CarId).get());

        reservation.setStartTime(shortReservationInfoDTO.getStartTime());
        reservation.setEndTime(shortReservationInfoDTO.getEndTime());
        reservation.setStatus(shortReservationInfoDTO.getStatus());

        reservationRepository.save(reservation);

        return reservation;
    }

    //Список всех бронирований
    public List<ShortReservationInfoDTO> getAllReservations() {
        List<Reservation> reservationList = reservationRepository.findAll();

        List<ShortReservationInfoDTO> shortReservationInfoDTOList = new ArrayList<>(reservationList.size());

        for(Reservation reservation : reservationList) {
            shortReservationInfoDTOList.add(convertToReservationDTO(reservation));
        }

        return shortReservationInfoDTOList;
    }

    //Найти бронь по id брони
    public ShortReservationInfoDTO findReservationById(int id) {
        return convertToReservationDTO(reservationRepository.findById(id));
    }

    //Найти все брони конкретного пользователя
    public List<ShortReservationInfoDTO> findReservationByUserId(int id) {
        return convertToReservationDTOList(reservationRepository.findByUserId(id));
    }

    //Найти всех людей , которые бронировали конкретный автомобиль
    public List<ShortReservationInfoDTO> findReservationByCarId(int id) {
        return convertToReservationDTOList(reservationRepository.findByCarId(id));
    }

    //Найти по статусу автомобили
    public List<ShortReservationInfoDTO> findReservationByStatus(String status) {

        if(status.equals("true")){
            return convertToReservationDTOList(reservationRepository.findByStatus(true));
        }
        else {
            return convertToReservationDTOList(reservationRepository.findByStatus(false));
        }


    }

    //Метод для удаления брони
    @Transactional
    public void deleteReservationById(int id) {
        reservationRepository.deleteById(id);
    }

    //Метод для редактирования полей брони
    @Transactional
    public Reservation changeReservationStatus(int id, ShortReservationInfoDTO shortReservationInfoDTO) {
        Reservation certainReservation = reservationRepository.findById(id);

//        if(shortReservationInfoDTO.getUser_id() != null){
//            User user = userRepository.findById(shortReservationInfoDTO.getUser_id()).orElseThrow();
//            certainReservation.setUser(user);
//        }
//        if(shortReservationInfoDTO.getCar_id() != null){
//            Car car = carRepository.findById(shortReservationInfoDTO.getCar_id()).orElseThrow();
//            certainReservation.setCar(car);
//        }
        if(shortReservationInfoDTO.getStartTime() != null){
            certainReservation.setStartTime(shortReservationInfoDTO.getStartTime());
        }
        if(shortReservationInfoDTO.getEndTime() != null){
            certainReservation.setEndTime(shortReservationInfoDTO.getEndTime());
        }
        if(shortReservationInfoDTO.getStatus() != null){
            certainReservation.setStatus(shortReservationInfoDTO.getStatus());
        }
        reservationRepository.save(certainReservation);
        return certainReservation;

    }

    //Метод из маппера (из сущности в DTO)
    private ShortReservationInfoDTO convertToReservationDTO(Reservation reservation) {

        ShortReservationInfoDTO shortReservationInfoDTO = new ShortReservationInfoDTO();

        shortReservationInfoDTO.setId(reservation.getId());
        shortReservationInfoDTO.setUser_id(reservation.getUser().getId());
        shortReservationInfoDTO.setCar_id(reservation.getCar().getId());
        shortReservationInfoDTO.setStartTime(reservation.getStartTime());
        shortReservationInfoDTO.setEndTime(reservation.getEndTime());
        shortReservationInfoDTO.setStatus(reservation.getStatus());

        return shortReservationInfoDTO;
    }

    //Метод из маппера (из листа сущностей в лист DTO)
    private List<ShortReservationInfoDTO> convertToReservationDTOList(List<Reservation> reservationList) {
        List<ShortReservationInfoDTO> shortReservationInfoDTOList = new ArrayList<>(reservationList.size());

        for(Reservation reservation : reservationList) {
            shortReservationInfoDTOList.add(convertToReservationDTO(reservation));
        }

        return shortReservationInfoDTOList;

    }

}
