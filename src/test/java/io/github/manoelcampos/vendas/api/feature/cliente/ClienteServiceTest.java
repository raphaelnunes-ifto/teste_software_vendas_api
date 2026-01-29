package io.github.manoelcampos.vendas.api.feature.cliente;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @InjectMocks
    private ClienteService service;

    @Mock
    private ClienteRepository repository;

    @Test
    void findByCpf() {
        final var cpfComSimbolos = "067.913.200-79";
        final var cpfSemSimbolos = "06791320079";

        var optional = Optional.of(new Cliente("Manoel", cpfSemSimbolos, null));
        Mockito.when(repository.findByCpf(cpfSemSimbolos))
                .thenReturn(optional);

        //assertTrue(service.findByCpf(cpfComSimbolos).isPresent());
        //assertTrue(service.findByCpf(cpfSemSimbolos).isPresent());
        var cliente1 = service.findByCpf(cpfComSimbolos).orElseThrow();
        var cliente2 = service.findByCpf(cpfSemSimbolos).orElseThrow();

        assertEquals(cpfSemSimbolos, cliente1.getCpf());
        assertEquals(cpfSemSimbolos, cliente2.getCpf());

        //Mockito.verify(repository).findByCpf(cpfSemSimbolos);
    }
}