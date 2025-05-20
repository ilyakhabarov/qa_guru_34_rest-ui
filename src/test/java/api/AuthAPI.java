package api;

import models.LoginRequestModel;
import models.LoginResponseModel;
import tests.TestData;
import specs.Specs;

import static io.restassured.RestAssured.given;

public class AuthAPI {
    public static LoginResponseModel login() {
        LoginRequestModel request = new LoginRequestModel(TestData.USERNAME, TestData.PASSWORD);
        return given(Specs.requestSpec)
                .body(request)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(Specs.responseSpec(200))
                .extract().as(LoginResponseModel.class);
    }
}