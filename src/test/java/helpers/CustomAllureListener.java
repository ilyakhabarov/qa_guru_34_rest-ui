package helpers;

import io.qameta.allure.restassured.AllureRestAssured;

public class CustomAllureListener {
    public static AllureRestAssured withCustomTemplates() {
        AllureRestAssured filter = new AllureRestAssured();
        filter.setRequestTemplate("request.ftl");
        filter.setResponseTemplate("response.ftl");
        return filter;
    }
}