package com.Reservations.ReservationsService.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

@Service
@Transactional
@JsonSerialize
public class ReservationService {

    private Car tempMemmoryCar;
    private User tempMemmoryUser;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    //Получатель автомобиля , записывает автомобиль из другого сервиса в репозиторий
    //На сервисе с авто необходимо повозиться с контроллером
    @KafkaListener(topics = "Cars", groupId = "CarsharingC", containerFactory = "carKafkaListnerContainerFactory")
    public Car ListenCarKafka(String message) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        ShortCarInfoDTO carDTO = mapper.readValue(message, ShortCarInfoDTO.class);

        try{

            tempMemmoryCar.setId(carDTO.getId());
            tempMemmoryCar.setMake(carDTO.getMake());
            tempMemmoryCar.setModel(carDTO.getModel());
            tempMemmoryCar.setYear(carDTO.getYear());
            tempMemmoryCar.setLicensePlate(carDTO.getLicensePlate());
            tempMemmoryCar.setAvailability(carDTO.getAvailability());
            tempMemmoryCar.setLocation(carDTO.getLocation());
            tempMemmoryCar.setPhotoUrl(carDTO.getPhotoUrl());
            tempMemmoryCar.setEngineType(carDTO.getEngineType());
            tempMemmoryCar.setNumberOfSeats(carDTO.getNumberOfSeats());
            tempMemmoryCar.setWeight(carDTO.getWeight());
            tempMemmoryCar.setEngineVolume(carDTO.getEngineVolume());
            tempMemmoryCar.setMaxSpeed(carDTO.getMaxSpeed());
            tempMemmoryCar.setGearboxType(carDTO.getGearboxType());
            tempMemmoryCar.setDescribe(carDTO.getDescribe());
            tempMemmoryCar.setHorsep(carDTO.getHorsep());
            tempMemmoryCar.setPricePerHour(carDTO.getPricePerHour());

            System.out.println("The car has been successfully received!");
            return tempMemmoryCar;
        }

        catch (Exception e){
            System.out.println("Error data in object" + e.getMessage());
        }

        return null;
    }

    @KafkaListener(topics = "Users", groupId = "CarsharingU", containerFactory = "userKafkaListnerContainerFactory")
    public User ListenUserKafka(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ShortUserInfoDTO receivedDTO = mapper.readValue(message, ShortUserInfoDTO.class);

        try {

            tempMemmoryUser.setId(receivedDTO.getId());
            tempMemmoryUser.setUsername(receivedDTO.getUsername());
            tempMemmoryUser.setFirstname(receivedDTO.getFirstname());
            tempMemmoryUser.setLastname(receivedDTO.getLastname());
            tempMemmoryUser.setPassportCode(receivedDTO.getPassportCode());
            tempMemmoryUser.setPhoneNumber(receivedDTO.getPhoneNumber());
            tempMemmoryUser.setPassword(receivedDTO.getPassword());
            tempMemmoryUser.setDriverLicense(receivedDTO.getDriverLicense());
            tempMemmoryUser.setImguser(receivedDTO.getImguser());

            System.out.println("The user has been successfully received!");
            return tempMemmoryUser;

        }
        catch (Exception e){
            System.out.println("Error data in object" + e.getMessage());
        }

        return null;
    }

    //Сделать бронь
    public Reservation addReservation(ShortReservationInfoDTO shortReservationInfoDTO) {
        Reservation reservation = new Reservation();
        reservation.setId(shortReservationInfoDTO.getId());

        reservation.setUser(tempMemmoryUser);

        reservation.setCar(tempMemmoryCar);

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
