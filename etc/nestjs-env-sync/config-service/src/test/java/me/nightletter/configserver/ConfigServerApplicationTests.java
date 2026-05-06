package me.nightletter.configserver;

import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConfigServerApplicationTests {

    WebTestClient webTestClient;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        String baseUrl = "http://localhost:" + port;
        this.webTestClient = WebTestClient.bindToServer()
                .baseUrl(baseUrl)
                .build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/application/dev", "/application/prod"})
    void env_property_is_not_empty(String uri) {
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectBody()
                .jsonPath("$.propertySources")
                .isNotEmpty();
    }

    @Test
    void env_property_is_empty() {
        webTestClient.get()
                .uri("/application/123")
                .exchange()
                .expectBody()
                .jsonPath("$.propertySources")
                .isEmpty();
    }

}
