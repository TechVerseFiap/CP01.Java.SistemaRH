package com.resources.human.domain;

import com.resources.human.domain.enums.Categoria;
import com.resources.human.domain.exceptions.DomainValidationException;

public class Recurso {
    private int id;
    private String nomeDoRecurso;
    private Categoria categoria;
    private boolean disponivel;
    private double valorEstimado;

    private Recurso(
            int id,
            String nomeDoRecurso,
            Categoria categoria,
            boolean disponivel,
            double valorEstimado
    ) {
        this.id = id;
        this.nomeDoRecurso = nomeDoRecurso;
        this.categoria = categoria;
        this.disponivel = disponivel;
        this.valorEstimado = valorEstimado;
    }

    public static Recurso create(
            int id,
            String nomeDoRecurso,
            Categoria categoria,
            boolean disponivel,
            double valorEstimado
    ) {
        return new Recurso(
            id,
            nomeDoRecurso,
            categoria,
            disponivel,
            valorEstimado
      );
    }

    public boolean podeSerAlocado() { return disponivel; }
    public void mudarDisponibilidade() { this.disponivel = !this.disponivel; }
    public boolean necessarioAutorizacao() { return valorEstimado > 5000; }
}