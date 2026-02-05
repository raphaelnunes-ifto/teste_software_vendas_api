package io.github.manoelcampos.vendas.api.feature.cliente;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClienteControllerTest {
    @Autowired
    private WebTestClient client;

    @Test
    void findByCpfInvalido() {
        var cpf = "000";
        client.get()
                .uri("/clientes/cpf/{cpf}", cpf)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void findByCpfInexistente() {
        var cpf = "386.125.710-65";
        client.get()
                .uri("/clientes/cpf/{cpf}", cpf)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}