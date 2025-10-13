package com.Reservations.ReservationsService.Service;

import com.Reservations.ReservationsService.Entity.Car;
import com.Reservations.ReservationsService.Entity.User;
import com.Reservations.ReservationsService.Repository.CarRepository;
import com.Reservations.ReservationsService.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dto.ShortCarInfoDTO;
import dto.ShortUserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@JsonSerialize
public class KafkaConsumerService {

    private final ReservationService reservationService;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Autowired
    public KafkaConsumerService(ReservationService reservationService, CarRepository carRepository, UserRepository userRepository) {
        this.reservationService = reservationService;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "Users", groupId = "CarsharingU", containerFactory = "userKafkaListnerContainerFactory")
    public void ListenUserKafka(ShortUserInfoDTO received) throws JsonProcessingException {

        System.out.println(received);

        try {

//            User tempUser = new User();
//
//            tempUser.setId(received.getId());
//            tempUser.setUsername(received.getUsername());
//            tempUser.setFirstname(received.getFirstname());
//            tempUser.setLastname(received.getLastname());
//            tempUser.setPassportCode(received.getPassportCode());
//            tempUser.setPhoneNumber(received.getPhoneNumber());
//            tempUser.setPassword(received.getPassword());
//            tempUser.setDriverLicense(received.getDriverLicense());
//            tempUser.setImguser(received.getImguser());

            int id = received.getId();
            Optional<User> tempUser = userRepository.findById(id);

            if(tempUser.isPresent()) {

                User userToUpdate = tempUser.get();
                updateUser(received, userToUpdate);
                reservationService.saveReceivedUserKafka(userToUpdate);
                System.out.println("User updated successfully!");

            }

            else{

                User newUser = new User();
                updateUser(received, newUser);
                reservationService.saveReceivedUserKafka(newUser);
                System.out.println("New user created successfully!");

            }

//            reservationService.saveReceivedUserKafka(tempUser);

            System.out.println("The user has been successfully received!");

        }
        catch (Exception e){
            System.out.println("Error data in object" + e.getMessage());
        }

    }

    //Получатель автомобиля , записывает автомобиль из другого сервиса в репозиторий
    //На сервисе с авто необходимо повозиться с контроллером
    @KafkaListener(topics = "Cars", groupId = "CarsharingC", containerFactory = "carKafkaListnerContainerFactory")
    public void ListenCarKafka(ShortCarInfoDTO carDTO) throws JsonProcessingException {

        System.out.println(carDTO);
        int CarId = carDTO.getId();

            try {

                 if(carRepository.findById(CarId).isPresent()) {

                    Car updatebleCar = carRepository.findById(CarId).get();
                    updateCar(carDTO,updatebleCar);
                    reservationService.saveRecievedCarKafka(updatebleCar);
                    System.out.println("The car has been successfully saved!");
                 }

                 else{


                     Car newCar = new Car();
                     updateCar(carDTO, newCar);
                     reservationService.saveRecievedCarKafka(newCar);
                     System.out.println("New Car created successfully!");
                 }

             } catch (Exception e) {
                 System.out.println("Error data in object" + e.getMessage());
             }


    }


    private void updateUser(ShortUserInfoDTO dto, User user) {

        //user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setPassportCode(dto.getPassportCode());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(dto.getPassword());
        user.setDriverLicense(dto.getDriverLicense());
        user.setImguser(dto.getImguser());

    }

    private void updateCar(ShortCarInfoDTO dto,  Car car){

        car.setMake(dto.getMake());
        car.setModel(dto.getModel());
        car.setYear(dto.getYear());
        car.setLicensePlate(dto.getLicensePlate());
        car.setAvailability(dto.getAvailability());
        car.setLocation(dto.getLocation());
        car.setPhotoUrl(dto.getPhotoUrl());
        car.setEngineType(dto.getEngineType());
        car.setNumberOfSeats(dto.getNumberOfSeats());
        car.setWeight(dto.getWeight());
        car.setEngineVolume(dto.getEngineVolume());
        car.setMaxSpeed(dto.getMaxSpeed());
        car.setGearboxType(dto.getGearboxType());
        car.setDescribe(dto.getDescribe());
        car.setHorsep(dto.getHorsep());
        car.setPricePerHour(dto.getPricePerHour());

    }

}