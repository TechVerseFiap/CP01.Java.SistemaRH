package com.resources.human.application;

import com.resources.human.domain.*;
import com.resources.human.domain.enums.Categoria;
import com.resources.human.domain.exceptions.DomainValidationException;
import com.resources.human.domain.exceptions.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SistemaERS {
    public List<Recurso> recursoList;
    public List<Colaborador> colaboradorList;
    public List<Alocacao> alocacaoList;

    public void alocarRecurso(int colaboradorId, int recursoId) throws EntityNotFoundException, DomainValidationException {
        Optional<Colaborador> colaboradorAlocacao = colaboradorList.stream()
                .filter(c -> c.getId() == colaboradorId)
                .findFirst();
        Optional<Recurso> recursoAlocacao = recursoList.stream()
                .filter(r -> r.getId() == recursoId)
                .findFirst();

        if (colaboradorAlocacao.isEmpty())
            throw new EntityNotFoundException("Não é possível realizar a alocação para um Colaborador não cadastrado!");
        if (recursoAlocacao.isEmpty())
            throw new EntityNotFoundException("Não é possível realizar a alocação para um Recurso não cadastrado!");

        Alocacao alocacao = Alocacao.create(
                colaboradorAlocacao.get(),
                recursoAlocacao.get(),
                LocalDate.now(),
                "Devolução será realizada no dia do retorno do Diretor para a Matriz."
        );

        alocacaoList.add(alocacao);
    }
}