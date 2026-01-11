package ru.netology.testmode.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.data.DataGenerator;
import ru.netology.testmode.data.UserInfo;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        UserInfo user = DataGenerator.getRegisteredUser("active");

        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $$("button").find(text("Продолжить")).click();

        $("h2")
                .shouldBe(visible)
                .shouldHave(text("Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        UserInfo user = DataGenerator.getUser("active");

        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $$("button").find(text("Продолжить")).click();

        $("[data-test-id=error-notification]")
                .shouldBe(visible)
                .shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error if login with blocked user")
    void shouldGetErrorIfBlockedUser() {
        UserInfo user = DataGenerator.getRegisteredUser("blocked");

        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $$("button").find(text("Продолжить")).click();

        $("[data-test-id=error-notification]")
                .shouldBe(visible)
                .shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        UserInfo user = DataGenerator.getRegisteredUser("active");
        String wrongLogin = DataGenerator.getUser("active").getLogin();

        $("[data-test-id=login] input").setValue(wrongLogin);
        $("[data-test-id=password] input").setValue(user.getPassword());
        $$("button").find(text("Продолжить")).click();

        $("[data-test-id=error-notification]")
                .shouldBe(visible)
                .shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        UserInfo user = DataGenerator.getRegisteredUser("active");
        String wrongPassword = DataGenerator.getUser("active").getPassword();

        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(wrongPassword);
        $$("button").find(text("Продолжить")).click();

        $("[data-test-id=error-notification]")
                .shouldBe(visible)
                .shouldHave(text("Неверно указан логин или пароль"));
    }
}


