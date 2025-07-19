package com.Car.Carservice.Repository;

import com.Car.Carservice.Entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    Car findById(int id);
    List<Car> findByMake(String make);
    List<Car> findByModel(String model);
    List<Car> findByYear(int year);
    List<Car> findByHorsep(int horsePower);
    List<Car> findByEngineType(String engineType);
    List<Car> findByEngineVolume(int engineVolume);
    List<Car> findByAvailability(boolean availability);

}
