package com.Car.Carservice.Controller;

import dto.ShortCarInfoDTO;
import com.Car.Carservice.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarControllers {

    private final CarService carService;

    @Autowired
    public CarControllers(CarService carService) {
        this.carService = carService;
    }

    //Просмотр всех авто (для всех)
    @GetMapping
    public List<ShortCarInfoDTO> getCars() {
        return carService.GetAllCars();
    }

    //@Transactional
    //@JsonSerialize
    @PostMapping("/kafka/{id}")
    public void sendKafkaCar(@PathVariable int id) {
        carService.SendKafkaCar(id);
    }

    //Найти авто по id (для всех)
    @GetMapping("/byId/{id}")
//    @PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_ADMIN')")
    public ShortCarInfoDTO getCarById(@PathVariable int id) {
        return carService.GetCarById(id);
    }

    //Найти по производителю (для всех)
    @GetMapping("/byMake/{make}")
//    @PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_ADMIN')")
    public List<ShortCarInfoDTO> getCarByMake(@PathVariable String make) {
        return carService.GetCarByMake(make);
    }

    //Найти по модели авто (для всех)
    @GetMapping("/byModel/{model}")
//    @PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_ADMIN')")
    public List<ShortCarInfoDTO> getCarByModel(@PathVariable String model) {
        return carService.GetCarByModel(model);
    }

    //Найти по кол-ву лошадиных сил (для всех)
    @GetMapping("/byHorsep/{horseP}")
//    @PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_ADMIN')")
    public List<ShortCarInfoDTO> getCarByHorseP(@PathVariable int horseP) {
        return carService.GetCarByHorseP(horseP);
    }

    //Найти по году выпуска (для всех)
    @GetMapping("/byYear/{year}")
//    @PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_ADMIN')")
    public List<ShortCarInfoDTO> getCarByYear(@PathVariable int year) {
        return carService.GetCarByYear(year);
    }

    //Найти по типу двигателя (для всех)
    @GetMapping("/byEngineT/{engineT}")
//    @PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_ADMIN')")
    public List<ShortCarInfoDTO> getCarByEngineT(@PathVariable String engineT) {
        return carService.GetCarByEngineType(engineT);
    }

    //Найти по обьему двигателя (для всех)
    @GetMapping("/byEngineV/{engineV}")
//    @PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_ADMIN')")
    public List<ShortCarInfoDTO> getCarByEngineV(@PathVariable int engineV) {
        return carService.GetCarByEngineVolume(engineV);
    }

    //Найти по статусу авто (занято, свободно) - (для всех)
    @GetMapping("/byStatus/{status}")
//    @PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_ADMIN')")
    public List<ShortCarInfoDTO> getCarByStatus(@PathVariable String status) {
        if("true".equals(status)){
            return carService.GetCarByAvailable(true);
        }

        return carService.GetCarByAvailable(false);
    }

    //Добавить авто (только админ)
    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void addCar(@RequestBody ShortCarInfoDTO car) {
        carService.CreateCar(car);
    }

    //Удалить авто (только админ)
    @DeleteMapping("/remove/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void RemoveCar(@PathVariable int id) {
        carService.DeleteById(id);
    }

    //Изменить параметры (только админ)
    @PatchMapping("/change/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void ChangeCar(@PathVariable int id, @RequestBody ShortCarInfoDTO car) {
        carService.ChangeCar(id, car);
    }

}
