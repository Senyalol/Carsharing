package com.Reservations.ReservationsService.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dto.ShortCarInfoDTO;
import com.Reservations.ReservationsService.DTO.ShortReservationInfoDTO;
import com.Reservations.ReservationsService.Entity.Car;
import com.Reservations.ReservationsService.Entity.Reservation;
import com.Reservations.ReservationsService.Entity.User;
import com.Reservations.ReservationsService.Repository.ReservationRepository;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dto.ShortUserInfoDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@JsonSerialize
public class ReservationService {

    private final Map<Integer,Car> receivedKafkaCars = new HashMap<>();
    private final Map<Integer,User> receivedKafkaUsers = new HashMap<>();
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    //Получатель автомобиля , записывает автомобиль из другого сервиса в репозиторий
    //На сервисе с авто необходимо повозиться с контроллером
    @KafkaListener(topics = "Cars", groupId = "CarsharingC", containerFactory = "carKafkaListnerContainerFactory")
    public void ListenCarKafka(ShortCarInfoDTO carDTO) throws JsonProcessingException {

        System.out.println(carDTO);

        try{

            Car tempCar = new Car();

            tempCar.setId(carDTO.getId());
            tempCar.setMake(carDTO.getMake());
            tempCar.setModel(carDTO.getModel());
            tempCar.setYear(carDTO.getYear());
            tempCar.setLicensePlate(carDTO.getLicensePlate());
            tempCar.setAvailability(carDTO.getAvailability());
            tempCar.setLocation(carDTO.getLocation());
            tempCar.setPhotoUrl(carDTO.getPhotoUrl());
            tempCar.setEngineType(carDTO.getEngineType());
            tempCar.setNumberOfSeats(carDTO.getNumberOfSeats());
            tempCar.setWeight(carDTO.getWeight());
            tempCar.setEngineVolume(carDTO.getEngineVolume());
            tempCar.setMaxSpeed(carDTO.getMaxSpeed());
            tempCar.setGearboxType(carDTO.getGearboxType());
            tempCar.setDescribe(carDTO.getDescribe());
            tempCar.setHorsep(carDTO.getHorsep());
            tempCar.setPricePerHour(carDTO.getPricePerHour());

            receivedKafkaCars.put(carDTO.getId(),tempCar);

            System.out.println("The car has been successfully received!");

        }

        catch (Exception e){
            System.out.println("Error data in object" + e.getMessage());
        }

    }

    @KafkaListener(topics = "Users", groupId = "CarsharingU", containerFactory = "userKafkaListnerContainerFactory")
    public void ListenUserKafka(ShortUserInfoDTO received) throws JsonProcessingException {


        System.out.println(received);

        try {

            User tempUser = new User();

            tempUser.setId(received.getId());
            tempUser.setUsername(received.getUsername());
            tempUser.setFirstname(received.getFirstname());
            tempUser.setLastname(received.getLastname());
            tempUser.setPassportCode(received.getPassportCode());
            tempUser.setPhoneNumber(received.getPhoneNumber());
            tempUser.setPassword(received.getPassword());
            tempUser.setDriverLicense(received.getDriverLicense());
            tempUser.setImguser(received.getImguser());

            receivedKafkaUsers.put(received.getId(),tempUser);


            System.out.println("The user has been successfully received!");

        }
        catch (Exception e){
            System.out.println("Error data in object" + e.getMessage());
        }

    }

    //Сделать бронь
    public Reservation addReservation(ShortReservationInfoDTO shortReservationInfoDTO) {
        Reservation reservation = new Reservation();
        reservation.setId(shortReservationInfoDTO.getId());


        reservation.setUser(receivedKafkaUsers.get(shortReservationInfoDTO.getUser_id()));

        reservation.setCar(receivedKafkaCars.get(shortReservationInfoDTO.getCar_id()));

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
    public void deleteReservationById(int id) {
        reservationRepository.deleteById(id);
    }

    //Метод для редактирования полей брони
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
