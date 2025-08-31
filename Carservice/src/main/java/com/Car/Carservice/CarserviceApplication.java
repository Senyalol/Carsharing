package com.Car.Carservice;

import com.Car.Carservice.Entity.Car;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackageClasses = Car.class)
public class CarserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarserviceApplication.class, args);
	}

}
