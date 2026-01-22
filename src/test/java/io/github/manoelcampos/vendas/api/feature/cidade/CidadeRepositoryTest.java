package io.github.manoelcampos.vendas.api.feature.cidade;

import org.assertj.core.util.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class CidadeRepositoryTest {

    @Autowired
    CidadeRepository repository;

    @Test
    void findByDescricaoLike() {
        final var listaObtida = repository.findByDescricaoLike("São %");

        final var listaEsperada = List.of(
                new Cidade(13L, "São Luiz"),
                new Cidade(1L, "São Paulo"),
                new Cidade(28L, "São José dos Campos"));
        System.out.println("Obtida: " + listaObtida);
        System.out.println("Esperada: " + listaEsperada);

        assertThat(listaObtida).size().isEqualTo(listaEsperada.size());
        assertThat(listaObtida).containsAll(listaEsperada);
    }

    @Test
    void deleteByIdExcluiCidade() {

        final long id = 28;
        assertThat(repository.findById(id)).isPresent();
        repository.deleteById(id);
        assertThat(repository.findById(id)).isEmpty();
    }

    @Test
    void updateCidade() {
        final long id = 28;
        final var nomeCidadeEsperado = "SÃO JOSÉ DOS CAMPOS";

        Cidade cidade = getCidade(id);
        cidade.setDescricao(nomeCidadeEsperado);

        repository.saveAndFlush(cidade);

        var cidadeObtida = getCidade(id);
        assertThat(cidadeObtida.getDescricao()).isEqualTo(nomeCidadeEsperado);
    }

    private @NotNull Cidade getCidade(long id) {
        Cidade cidade = repository.findById(id).orElseThrow();
        return cidade;
    }

    @Test
    void deleteByIdLancaExcessaoViolacaoFK() {
        final long id = 4;

        for(long i = 1; i <= 10; i++){
            repository.deleteById(i);
        }

        assertThatThrownBy(() -> {
                repository.deleteById(id);
                repository.flush();
        })
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}