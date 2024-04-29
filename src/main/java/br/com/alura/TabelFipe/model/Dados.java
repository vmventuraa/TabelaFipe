package br.com.alura.TabelFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Dados(@JsonAlias("codigo") String codigo,
                    @JsonAlias("nome") String nome) {
}
