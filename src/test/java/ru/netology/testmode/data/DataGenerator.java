package ru.netology.testmode.data;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static final Faker faker = new Faker();

    private DataGenerator() {}

    public static UserInfo getRegisteredUser(String status) {
        UserInfo user = new UserInfo(
                faker.name().username(),
                faker.internet().password(),
                status
        );

        sendRequest(user);
        return user;
    }

    public static UserInfo getUser(String status) {
        return new UserInfo(
                faker.name().username(),
                faker.internet().password(),
                status
        );
    }

    private static void sendRequest(UserInfo user) {
        given()
                .baseUri("http://localhost")
                .port(9999)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
}

