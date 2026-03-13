package com.resources.human.application;

import com.resources.human.domain.*;
import com.resources.human.domain.exceptions.DomainValidationException;

import java.time.LocalDate;

public class SistemaERS {
    public void alocarRecurso(int colaboradorId, int recursoId) throws DomainValidationException {
        Colaborador colaborador = Colaborador.create(
                colaboradorId,
                "Diogo",
                "Estagiário",
                600.50,
                LocalDate.of(2022, 2, 2)
        );

        Recurso recurso = Recurso.create(
                recursoId,
                "Notebook HP",
                "Notebook",
                true,
                450.50
        );

        Alocacao alocacao = Alocacao.create(
                colaborador,
                recurso,
                LocalDate.now(),
                "Seila"
        );
    }
}