package api;

import io.restassured.response.Response;
import models.LoginRequestModel;
import models.LoginResponseModel;
import models.UserBooksResponseModel;
import tests.TestData;
import specs.Specs;

import static io.restassured.RestAssured.given;
import static specs.Specs.requestSpec;
import static specs.Specs.responseSpec;

public class AccountApiSteps {
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

    public static UserBooksResponseModel getUserBooks(String token, String userId) {
        Response response = given(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/Account/v1/User/" + userId)
                .then()
                .spec(responseSpec(200))
                .extract().response();

        return response.as(UserBooksResponseModel.class);
    }
}