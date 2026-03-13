package com.resources.human.domain;

import com.resources.human.domain.exceptions.DomainValidationException;

import java.time.LocalDate;

public class Alocacao {
    private Colaborador colaborador;
    private Recurso recurso;
    private LocalDate data;
    private String observacao;

    private Alocacao(
            Colaborador colaborador,
            Recurso recurso,
            LocalDate data,
            String observacao
    ) throws DomainValidationException {
        if (!recurso.podeSerAlocado())
            throw new DomainValidationException("Não é possível alocar um recurso que não está disponível");

        this.colaborador = colaborador;
        this.recurso = recurso;
        this.data = data;
        this.observacao = observacao;
    }

    public static Alocacao create(
            Colaborador colaborador,
            Recurso recurso,
            LocalDate data,
            String observacao
    ) throws DomainValidationException {
        return new Alocacao(
            colaborador,
            recurso,
            data,
            observacao
        );
    }
}