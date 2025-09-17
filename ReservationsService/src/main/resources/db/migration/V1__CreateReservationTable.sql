CREATE TABLE Reservations (
                              reservation_id SERIAL PRIMARY KEY,
                              user_id INT,
                              car_id INT,
                              start_time TIMESTAMP NOT NULL,
                              end_time TIMESTAMP NOT NULL,
                              status BOOLEAN,
                              FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ,
                              FOREIGN KEY (car_id) REFERENCES Cars(car_id) ON DELETE CASCADE
);
