package ru.netology.testmode.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.data.DataGenerator;
import ru.netology.testmode.data.UserInfo;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.text;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        UserInfo user = DataGenerator.getRegisteredUser("active");

        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $$("button").find(text("Продолжить")).click();

        $("h2").shouldHave(text("Личный кабинет"));
    }
}

