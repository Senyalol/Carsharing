package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShortUserInfoDTO {

    @JsonProperty("user_id")
    private Integer id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("passport_code")
    private String passportCode;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("password")
    private String password;

    @JsonProperty("driver_license")
    private String driverLicense;

    @JsonProperty("imguser")
    private String imguser;

    private String adminkey;

}

