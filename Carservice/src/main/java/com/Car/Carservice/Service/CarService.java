package com.Car.Carservice.Service;

import com.Car.Carservice.Entity.Car;
import com.Car.Carservice.Mapper.CarMapping;
import com.Car.Carservice.Repository.CarRepository;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dto.ShortCarInfoDTO;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@Transactional
@JsonSerialize
public class CarService {

    private final CarRepository carRepository;
    private final CarMapping carMapping;
    private final KafkaTemplate<String, ShortCarInfoDTO> kafkaTemplate;

    //Конструктор класса сервиса
    @Autowired
    public CarService(CarRepository carRepository, CarMapping carMapping, KafkaTemplate<String, ShortCarInfoDTO> kafkaTemplate) {
        this.carRepository = carRepository;
        this.carMapping = carMapping;
        this.kafkaTemplate = kafkaTemplate;
    }


    @KafkaListener(topics = "JWTKafka", groupId = "JWT", containerFactory = "kafkaListenerContainerFactory")
    public void ListenJwt(String jwt){
        System.out.println("JWT: " + jwt);
    }

//    @KafkaListener(topics = "C")
    public void SendKafkaCar(int certainCarId) {

        try{
            kafkaTemplate.send("Cars", carMapping.toCarInfoDTO(carRepository.findById(certainCarId)));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //Метод для просмотра всего списка авто
    public List<ShortCarInfoDTO> GetAllCars() {
        return carMapping.toShortCarInfoDTO(carRepository.findAll());
    }

    //Найти авто по id
    public ShortCarInfoDTO GetCarById(int id) {
        return carMapping.toCarInfoDTO(carRepository.findById(id));
    }

    //Найти авто по производителю
    public List<ShortCarInfoDTO> GetCarByMake(String make) {
        return carMapping.toShortCarInfoDTO(carRepository.findByMake(make));
    }

    //Найти авто по модели авто
    public List<ShortCarInfoDTO> GetCarByModel(String model) {
        return carMapping.toShortCarInfoDTO(carRepository.findByModel(model));
    }

    //Найти авто по году выпуска
    public List<ShortCarInfoDTO> GetCarByYear(int year) {
        return carMapping.toShortCarInfoDTO(carRepository.findByYear(year));
    }

    //Сорт авто по лошадиным силам
    public List<ShortCarInfoDTO> GetCarByHorseP(int horseP) {
        return carMapping.toShortCarInfoDTO(carRepository.findByHorsep(horseP));
    }

    //Тип двигателя у авто
    public List<ShortCarInfoDTO> GetCarByEngineType(String engineType) {
        return carMapping.toShortCarInfoDTO(carRepository.findByEngineType(engineType));
    }

    //Обьем двигателя у авто
    public List<ShortCarInfoDTO> GetCarByEngineVolume(int engineVolume) {
        return carMapping.toShortCarInfoDTO(carRepository.findByEngineVolume(engineVolume));
    }

    //Найти авто по его статусу (занято, свободно)
    public List<ShortCarInfoDTO> GetCarByAvailable(boolean available) {
        return carMapping.toShortCarInfoDTO(carRepository.findByAvailability(available));
    }

    //Метод для создания авто
    public Car CreateCar(ShortCarInfoDTO shortCarInfoDTO) {
        Car car = new Car();
        carRepository.save(carMapping.toCar(shortCarInfoDTO));
        return car;
    }

    //Метод для удаления авто по его id
    public void DeleteById(int id) {
        carRepository.deleteById(id);
    }

    //Метод для изменения параметров авто
    public Car ChangeCar(int id, ShortCarInfoDTO shortCarInfoDTO) {
        Car CarToUpdate = carRepository.findById(id);

        if (shortCarInfoDTO.getMake() != null) {
            CarToUpdate.setMake(shortCarInfoDTO.getMake());
        }

        if (shortCarInfoDTO.getModel() != null) {
            CarToUpdate.setModel(shortCarInfoDTO.getModel());
        }

        if (shortCarInfoDTO.getYear() != null) {
            CarToUpdate.setYear(shortCarInfoDTO.getYear());
        }

        if (shortCarInfoDTO.getLicensePlate() != null) {
            CarToUpdate.setLicensePlate(shortCarInfoDTO.getLicensePlate());
        }

        if (shortCarInfoDTO.getAvailability() != null) {
            CarToUpdate.setAvailability(shortCarInfoDTO.getAvailability());
        }

        if (shortCarInfoDTO.getLocation() != null) {
            CarToUpdate.setLocation(shortCarInfoDTO.getLocation());
        }

        if (shortCarInfoDTO.getPhotoUrl() != null) {
            CarToUpdate.setPhotoUrl(shortCarInfoDTO.getPhotoUrl());
        }

        if (shortCarInfoDTO.getEngineType() != null) {
            CarToUpdate.setEngineType(shortCarInfoDTO.getEngineType());
        }

        if (shortCarInfoDTO.getNumberOfSeats() != null) {
            CarToUpdate.setNumberOfSeats(shortCarInfoDTO.getNumberOfSeats());
        }

        if (shortCarInfoDTO.getWeight() != null) {
            CarToUpdate.setWeight(shortCarInfoDTO.getWeight());
        }

        if (shortCarInfoDTO.getEngineVolume() != null) {
            CarToUpdate.setEngineVolume(shortCarInfoDTO.getEngineVolume());
        }

        if (shortCarInfoDTO.getMaxSpeed() != null) {
            CarToUpdate.setMaxSpeed(shortCarInfoDTO.getMaxSpeed());
        }

        if (shortCarInfoDTO.getGearboxType() != null) {
            CarToUpdate.setGearboxType(shortCarInfoDTO.getGearboxType());
        }

        if (shortCarInfoDTO.getDescribe() != null) {
            CarToUpdate.setDescribe(shortCarInfoDTO.getDescribe());
        }

        if (shortCarInfoDTO.getHorsep() != null) {
            CarToUpdate.setHorsep(shortCarInfoDTO.getHorsep());
        }

        return carRepository.save(CarToUpdate);

    }

    protected boolean canEqual(final Object other) {
        return other instanceof CarService;
    }

}

