CREATE TABLE Cars (
                      car_id SERIAL PRIMARY KEY,
                      make VARCHAR(50) NOT NULL,
                      model VARCHAR(100) NOT NULL,
                      year INT,
                      license_plate VARCHAR(20) NOT NULL UNIQUE,
                      availability BOOLEAN,
                      location VARCHAR(100) NOT NULL
);

CREATE SCHEMA car_schema;

ALTER TABLE Cars SET SCHEMA car_schema;