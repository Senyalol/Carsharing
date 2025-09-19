CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(40) NOT NULL UNIQUE,
                       firstname VARCHAR(100) NOT NULL,
                       lastname VARCHAR(100) NOT NULL,
                       passport_code VARCHAR(150) NOT NULL UNIQUE,
                       phone_number VARCHAR(15),
                       password VARCHAR(255) NOT NULL,
                       driver_license VARCHAR(11) NOT NULL UNIQUE
);