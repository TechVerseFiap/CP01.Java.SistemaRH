package com.resources.human.application;

import com.resources.human.domain.*;
import com.resources.human.domain.enums.Categoria;
import com.resources.human.domain.exceptions.AlreadyExistsByIdException;
import com.resources.human.domain.exceptions.DomainValidationException;
import com.resources.human.domain.exceptions.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SistemaERS {
    private List<Recurso> recursoList;
    private List<Colaborador> colaboradorList;
    private List<Alocacao> alocacaoList;

    public Colaborador cadastrarColaborador(int id, String nome, String cargo, double salario, LocalDate dataDeAdmissao) throws AlreadyExistsByIdException {
        if (colaboradorList.stream().anyMatch(c -> c.getId() == id))
            throw new AlreadyExistsByIdException("Um Colaborador com o mesmo ID já existe na base de dados!");

        Colaborador colaboradorCriado = Colaborador.create(id, nome, cargo, salario, dataDeAdmissao);
        colaboradorList.add(colaboradorCriado);
        return colaboradorCriado;
    }

    public Recurso cadastrarRecurso(int id, String nomeDoRecurso, Categoria categoria, boolean disponivel, double valorEstimado) throws AlreadyExistsByIdException {
        if (recursoList.stream().anyMatch(c -> c.getId() == id))
            throw new AlreadyExistsByIdException("Um Recurso com o mesmo ID já existe na base de dados!");

        Recurso recursoCriado = Recurso.create(id, nomeDoRecurso, categoria, disponivel, valorEstimado);
        recursoList.add(recursoCriado);
        return recursoCriado;
    }

    public Colaborador buscarColaboradorById(int colaboradorId) throws EntityNotFoundException {
        Optional<Colaborador> colaborador = colaboradorList.stream()
                .filter(c -> c.getId() == colaboradorId)
                .findFirst();
        if (colaborador.isEmpty())
            throw new EntityNotFoundException("Colaborador inexistente!");
        return colaborador.get();
    }

    public Recurso buscarRecursoById(int recursoId) throws EntityNotFoundException {
        Optional<Recurso> recurso = recursoList.stream()
                .filter(r -> r.getId() == recursoId)
                .findFirst();
        if (recurso.isEmpty())
            throw new EntityNotFoundException("Recurso inexistente!");
        return recurso.get();
    }

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