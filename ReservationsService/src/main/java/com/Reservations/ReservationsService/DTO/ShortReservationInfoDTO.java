package com.Reservations.ReservationsService.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class ShortReservationInfoDTO {

    @JsonProperty("reservation_id")
    private Integer id;

    @JsonProperty("user_id")
    private Integer user_id;

    @JsonProperty("car_id")
    private Integer car_id;

    @JsonProperty("start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Moscow")
    private Instant startTime;

    @JsonProperty("end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Moscow")
    private Instant endTime;

    @JsonProperty("status")
    private Boolean status;

}
