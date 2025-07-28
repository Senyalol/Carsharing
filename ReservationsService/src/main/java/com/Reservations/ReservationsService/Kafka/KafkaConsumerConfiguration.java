package com.Reservations.ReservationsService.Kafka;

import dto.ShortUserInfoDTO;
import dto.ShortCarInfoDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;


import java.util.HashMap;
import java.util.Map;


@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

//    @Bean
//    public ConsumerFactory<String, ShortCarInfoDTO> consumerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "Carsharing");
//        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//
//        // Используем JsonDeserializer напрямую
//        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//
//        // Указываем конкретный класс для десериализации
//        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, ShortCarInfoDTO.class);
//
//        // Разрешаем все пакеты (для теста)
//        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//
//        return new DefaultKafkaConsumerFactory<>(configProps);
//    }

    private Map<String, Object> basicConfig(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }

    //Для ShortCarInfoDTO
    @Bean
    public ConsumerFactory<String, ShortCarInfoDTO> carInfoConsumerFactory(){
        Map<String, Object> ConfigProps = basicConfig();
        ConfigProps.put(ConsumerConfig.GROUP_ID_CONFIG,"CarsharingC");
        ConfigProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        ConfigProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE,ShortCarInfoDTO.class);
        return new DefaultKafkaConsumerFactory<>(ConfigProps);
    }

    //Контейнер слушаетеля для CarDTO
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ShortCarInfoDTO> carKafkaListnerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,ShortCarInfoDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(carInfoConsumerFactory());
        return factory;
    }

    //Для ShortUserInfoDTO
    @Bean
    public ConsumerFactory<String, ShortUserInfoDTO> userInfoConsumerFactory(){
        Map<String, Object> ConfigProps = basicConfig();
        ConfigProps.put(ConsumerConfig.GROUP_ID_CONFIG,"CarsharingU");
        ConfigProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        ConfigProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE,ShortUserInfoDTO.class);
        return new DefaultKafkaConsumerFactory<>(ConfigProps);
    }

    //Контейнер слушателя для UserDTO
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ShortUserInfoDTO> userKafkaListnerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, ShortUserInfoDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userInfoConsumerFactory());
        return factory;
    }

}
