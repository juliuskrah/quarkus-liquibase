package com.juliuskrah.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

/**
 * @author Julius Krah
 */
@QuarkusTest
class ClientResourceTest {
    
    @Test
    @DisplayName("Test mapping for /clients/{id}")
    void testClient() {
        Response response = given()
                .when()
                .get("/clients/{id}", UUID.fromString("15759e2d-21ab-4ec2-bb9a-2aee55c409fe"))
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        assertThat(response.jsonPath().getString("name")).isEqualTo("freedom limited");
    }

    @Test
    @DisplayName("Test mapping for /clients/")
    void testClients() {
        Response response = given()
                .when()
                .get("/clients")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        assertThat(response.jsonPath().getList("name"))
            .containsExactlyInAnyOrder("hey foods", "freedom limited", "evil corp", "acme corporation");
    }

    @Test
    @DisplayName("Test mapping for /clients/{code}/code")
    void testClientByCode() {
        Response response = given()
                .when()
                .get("/clients/{code}/code", "acme")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        assertThat(response.jsonPath().getString("name")).isEqualTo("acme corporation");
    }

    @Test
    @DisplayName("Test mapping for /clients/{clientCode}/services")
    void testServicesByClient() {
        Response response = given()
                .when()
                .get("/clients/{clientCode}/services", "hey")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        assertThat(response.jsonPath().getList("name"))
            .containsExactlyInAnyOrder("coffee", "pizza");
    }
}
