package models;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LoginResponseModel {
    private String token;
    private String userId;
    private String expires;
    private String username;
}
