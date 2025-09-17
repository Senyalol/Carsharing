ALTER TABLE cars ADD COLUMN photo_url VARCHAR(300);
ALTER TABLE cars ADD COLUMN engine_type VARCHAR(40);
ALTER TABLE cars ADD COLUMN number_of_seats INT;
ALTER TABLE cars ADD COLUMN weight INT;
ALTER TABLE cars ADD COLUMN engine_volume INT;
ALTER TABLE cars ADD COLUMN max_speed INT;
ALTER TABLE cars ADD COLUMN gearbox_type VARCHAR(100);
ALTER TABLE cars ADD COLUMN describe VARCHAR(1000);
ALTER TABLE cars ADD COLUMN HorseP INT;
ALTER TABLE cars ADD COLUMN  price_per_hour DOUBLE PRECISION;

INSERT INTO cars (make,model,year,license_plate,availability,location,photo_url,engine_type,number_of_seats,weight,engine_volume,max_speed,gearbox_type,describe,horsep,price_per_hour)
VALUES('Германия','Audi A6',2018,'9182374657',TRUE,'Автостоянка n5','https://www.google.com/url?sa=i&url=https%3A%2F%2Fru.m.wikipedia.org%2Fwiki%2F%25D0%25A4%25D0%25B0%25D0%25B9%25D0%25BB%3AAudi_A6_2018_%252844686504882%2529.jpg&psig=AOvVaw1G7Hh9TSzu_elJY4_8IPuH&ust=1739469518639000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMCp7IzbvosDFQAAAAAdAAAAABAE','Дизель',4,1865,3,250,'Автоматическая коробка передач','Четвертое поколение бизнес-седана Audi A6 было запущено в 2011 году, а в начале сентября 2014 года компания Audi официально представила обновленный A6. Премьера новинки состоялась 2 октября на автосалоне в Париже. Изменения в дизайне оказались незначительными — автомобиль получил модернизированные бамперы и решетку радиатора, новые накладки на пороги и насадки на глушитель. В качестве опции семейство теперь оснащается матричными светодиодными фарами, аналогичными той оптике, которой комплектуется Audi A7. Обновлена информационно-развлекательная система — она получила более производительный процессор, новую 3D-анимированную графику, возможность работы в качестве мобильной точки доступа Wi-Fi 4G LTE для восьми устройств.',333,21);


-- ALTER TABLE car_schema.cars ADD COLUMN photo_url VARCHAR(300);
-- ALTER TABLE car_schema.cars ADD COLUMN engine_type VARCHAR(40);
-- ALTER TABLE car_schema.cars ADD COLUMN number_of_seats INT;
-- ALTER TABLE car_schema.cars ADD COLUMN weight INT;
-- ALTER TABLE car_schema.cars ADD COLUMN engine_volume INT;
-- ALTER TABLE car_schema.cars ADD COLUMN max_speed INT;
-- ALTER TABLE car_schema.cars ADD COLUMN gearbox_type VARCHAR(100);
--
-- INSERT INTO car_schema.cars (make,model,year,license_plate,availability,location,photo_url,engine_type,number_of_seats,weight,engine_volume,max_speed,gearbox_type)
-- VALUES('Германия','Audi A6',2018,'9182374657',TRUE,'Автостоянка n5','https://www.google.com/url?sa=i&url=https%3A%2F%2Fru.m.wikipedia.org%2Fwiki%2F%25D0%25A4%25D0%25B0%25D0%25B9%25D0%25BB%3AAudi_A6_2018_%252844686504882%2529.jpg&psig=AOvVaw1G7Hh9TSzu_elJY4_8IPuH&ust=1739469518639000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMCp7IzbvosDFQAAAAAdAAAAABAE','Дизель',4,1865,3,250,'Автоматическая коробка передач');
--
-- ALTER TABLE car_schema.cars ADD COLUMN describe VARCHAR(1000);
-- ALTER TABLE car_schema.cars ADD COLUMN HorseP INT;
--
-- UPDATE car_schema.cars
-- SET describe = 'Четвертое поколение бизнес-седана Audi A6 было запущено в 2011 году, а в начале сентября 2014 года компания Audi официально представила обновленный A6. Премьера новинки состоялась 2 октября на автосалоне в Париже. Изменения в дизайне оказались незначительными — автомобиль получил модернизированные бамперы и решетку радиатора, новые накладки на пороги и насадки на глушитель. В качестве опции семейство теперь оснащается матричными светодиодными фарами, аналогичными той оптике, которой комплектуется Audi A7. Обновлена информационно-развлекательная система — она получила более производительный процессор, новую 3D-анимированную графику, возможность работы в качестве мобильной точки доступа Wi-Fi 4G LTE для восьми устройств.'
-- WHERE car_id = 1;
--
-- UPDATE car_schema.cars
-- SET horsep = 333
-- WHERE car_id = 1;
--
-- ALTER TABLE car_schema.cars ADD COLUMN  price_per_hour DOUBLE PRECISION;
--
-- UPDATE car_schema.cars
-- SET price_per_hour = 200.5
-- WHERE car_id = 13;
--
-- UPDATE car_schema.cars
-- SET price_per_hour = 99.9
-- WHERE car_id = 1;
