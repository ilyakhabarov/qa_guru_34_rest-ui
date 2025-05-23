package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import helpers.CustomAllureListener;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static io.restassured.config.SSLConfig.sslConfig;

public class TestBase {
    @BeforeEach
    void setUp() {
        RestAssured.filters(CustomAllureListener.withCustomTemplates());
    }

    @BeforeAll
    static void configuration() {
        Configuration.browserSize = System.getProperty("browserSize", "1280x720");
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("browserVersion", "128.0");
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        Configuration.remote = System.getProperty("browserRemote", "https://user1:1234@selenoid.autotests.cloud/wd/hub");

        RestAssured.baseURI = "https://demoqa.com";
        RestAssured.config = RestAssured.config()
                .sslConfig(sslConfig().with().relaxedHTTPSValidation());

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        Selenide.closeWebDriver();
    }
}