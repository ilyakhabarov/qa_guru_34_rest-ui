package tests;

import api.AccountApiSteps;
import api.BookStoreApiSteps;
import com.codeborne.selenide.Selenide;
import helpers.WithLogin;
import models.LoginResponseModel;
import models.UserBooksRequestModel;
import models.UserBooksResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static tests.TestData.BOOK_ISBN;
import static tests.TestData.USERNAME;

public class BookStoreOperationsTests extends TestBase {

    @Test
    @WithLogin
    @DisplayName("Удаление книги из профиля: проверка через API и UI")
    void deleteBookTest() {
        LoginResponseModel auth = step("Авторизация пользователя через API", AccountApiSteps::login);
        String token = auth.getToken();
        String userId = auth.getUserId();

        step("Очистка коллекции пользователя", () ->
                BookStoreApiSteps.deleteAllBooks(token, userId)
        );

        step("Добавление книги через API", () ->
                BookStoreApiSteps.addBook(token, userId, BOOK_ISBN)
        );

        step("Проверка через API, что книга добавлена пользователю", () -> {
            UserBooksResponseModel booksResp = AccountApiSteps.getUserBooks(token, userId);
            List<UserBooksRequestModel> userBooks = booksResp.getBooks();
            assertThat(userBooks).extracting(UserBooksRequestModel::getIsbn).contains(BOOK_ISBN);
        });

        step("Переход на страницу профиля", () -> {
            open("/profile");
            $("#userName-value").shouldHave(text(USERNAME));
        });

        step("Удаление книги у пользователя через API", () ->
                BookStoreApiSteps.deleteBook(token, userId, BOOK_ISBN)
        );

        step("Проверка через API, что книга удалена у пользователя", () -> {
            UserBooksResponseModel booksResp = AccountApiSteps.getUserBooks(token, userId);
            assertThat(booksResp.getBooks()).noneMatch(b -> b.getIsbn().equals(BOOK_ISBN));
        });

        step("Обновление страницы и проверка удаления через UI", () -> {
            Selenide.refresh();
            $(".rt-noData").shouldBe(visible).shouldHave(text("No rows found"));
        });
    }
}
