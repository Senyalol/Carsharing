CREATE TABLE cars (
                      kafka_id SERIAL PRIMARY KEY,
                      car_id INT UNIQUE NOT NULL,
                      make VARCHAR(50) NOT NULL,
                      model VARCHAR(100) NOT NULL,
                      year INT,
                      license_plate VARCHAR(20) NOT NULL UNIQUE,
                      availability BOOLEAN,
                      location VARCHAR(100) NOT NULL
);