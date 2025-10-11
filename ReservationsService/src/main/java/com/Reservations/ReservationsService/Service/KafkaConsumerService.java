package com.Reservations.ReservationsService.Service;

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
import com.Reservations.ReservationsService.Entity.Car;

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

         if(!carRepository.existsById(CarId)) {

             try {

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

                 carRepository.save(tempCar);

                 System.out.println("The car has been successfully received!");

             } catch (Exception e) {
                 System.out.println("Error data in object" + e.getMessage());
             }

         }

         else{
             System.out.println("The car already exists!");
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

}