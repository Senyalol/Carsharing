package com.Car.Carservice.Mapper;

import com.Car.Carservice.DTO.ShortCarInfoDTO;
import com.Car.Carservice.Entity.Car;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
//import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CarMapping {

    // List<Car> toCars(List<ShortCarInfoDTO> shortCarInfoDTOs);

    default ShortCarInfoDTO toCarInfoDTO(Car car) {
        if ( car == null ) {
            return null;
        }

        ShortCarInfoDTO shortCarInfoDTO = new ShortCarInfoDTO();
        shortCarInfoDTO.setId(car.getId());
        shortCarInfoDTO.setMake(car.getMake());
        shortCarInfoDTO.setModel(car.getModel());
        shortCarInfoDTO.setYear(car.getYear());
        shortCarInfoDTO.setLicensePlate( car.getLicensePlate() );
        shortCarInfoDTO.setAvailability(car.getAvailability());
        shortCarInfoDTO.setLocation( car.getLocation() );
        shortCarInfoDTO.setPhotoUrl(car.getPhotoUrl());
        shortCarInfoDTO.setEngineType(car.getEngineType());
        shortCarInfoDTO.setNumberOfSeats( car.getNumberOfSeats() );
        shortCarInfoDTO.setWeight(car.getWeight());
        shortCarInfoDTO.setEngineVolume( car.getEngineVolume() );
        shortCarInfoDTO.setMaxSpeed( car.getMaxSpeed() );
        shortCarInfoDTO.setGearboxType(car.getGearboxType());
        shortCarInfoDTO.setDescribe( car.getDescribe() );
        shortCarInfoDTO.setHorsep( car.getHorsep() );
        shortCarInfoDTO.setPricePerHour( car.getPricePerHour() );

        return shortCarInfoDTO;
    }

    default List<ShortCarInfoDTO> toShortCarInfoDTO(List<Car> cars) {
        if ( cars == null ) {
            return null;
        }

        List<ShortCarInfoDTO> list = new ArrayList<ShortCarInfoDTO>( cars.size() );
        for ( Car car : cars ) {
            list.add( toCarInfoDTO( car ) );
        }

        return list;
    }

    default List<Car> toCars(List<ShortCarInfoDTO> shortCarInfoDTOs) {
        if ( shortCarInfoDTOs == null ) {
            return null;
        }

        List<Car> list = new ArrayList<Car>( shortCarInfoDTOs.size() );
        for ( ShortCarInfoDTO shortCarInfoDTO : shortCarInfoDTOs ) {
            list.add( toCar( shortCarInfoDTO ) );
        }

        return list;
    }

    default Car toCar(ShortCarInfoDTO shortCarInfoDTO) {
        if ( shortCarInfoDTO == null ) {
            return null;
        }

        Car car = new Car();
        car.setId(shortCarInfoDTO.getId());
        car.setMake( shortCarInfoDTO.getMake() );
        car.setModel( shortCarInfoDTO.getModel() );
        car.setYear( shortCarInfoDTO.getYear() );
        car.setLicensePlate( shortCarInfoDTO.getLicensePlate() );
        car.setAvailability( shortCarInfoDTO.getAvailability() );
        car.setLocation( shortCarInfoDTO.getLocation() );
        car.setPhotoUrl( shortCarInfoDTO.getPhotoUrl() );
        car.setEngineType( shortCarInfoDTO.getEngineType() );
        car.setNumberOfSeats( shortCarInfoDTO.getNumberOfSeats() );
        car.setWeight( shortCarInfoDTO.getWeight() );
        car.setEngineVolume( shortCarInfoDTO.getEngineVolume() );
        car.setMaxSpeed( shortCarInfoDTO.getMaxSpeed() );
        car.setGearboxType( shortCarInfoDTO.getGearboxType() );
        car.setDescribe( shortCarInfoDTO.getDescribe() );
        car.setHorsep( shortCarInfoDTO.getHorsep() );
        car.setPricePerHour( shortCarInfoDTO.getPricePerHour() );

        return car;
    }

}
