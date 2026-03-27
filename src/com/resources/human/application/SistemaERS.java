package com.resources.human.application;

import com.resources.human.domain.*;
import com.resources.human.domain.enums.Categoria;
import com.resources.human.domain.exceptions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SistemaERS {
    private List<Recurso> recursoList = new ArrayList<>();
    private List<Colaborador> colaboradorList = new ArrayList<>();
    private List<Alocacao> alocacaoList = new ArrayList<>();

    public Colaborador cadastrarColaborador(int id, String nome, String cargo, double salario, LocalDate dataDeAdmissao)
            throws AlreadyExistsByIdException {
        if (colaboradorList.stream().anyMatch(c -> c.getId() == id))
            throw new AlreadyExistsByIdException("Um Colaborador com o mesmo ID já existe na base de dados!");

        Colaborador colaboradorCriado = Colaborador.create(id, nome, cargo, salario, dataDeAdmissao);
        colaboradorList.add(colaboradorCriado);
        return colaboradorCriado;
    }

    public Recurso cadastrarRecurso(int id, String nomeDoRecurso, Categoria categoria, boolean disponivel, double valorEstimado)
            throws AlreadyExistsByIdException, DomainValidationException {
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

    public void listarColaboradores() {
        System.out.println("=== LISTAGEM DE COLABORADORES CADASTRADOS ===\n\n");
        if (!colaboradorList.isEmpty()) {
            for (Colaborador c : colaboradorList) {
                System.out.println(c.exibir());
            }
            return;
        }
        System.out.println("- Nenhum colaborador Cadastrado");
    }

    public void listarRecursos() {
        System.out.println("=== LISTAGEM DE RECURSOS CADASTRADOS ===\n\n");
        if (!recursoList.isEmpty()) {
            for (Recurso r : recursoList) {
                System.out.println(r.exibir());
            }
            return;
        }
        System.out.println("- Nenhum recurso Cadastrado");
    }

    public void listarAlocacoes() {
        System.out.println("=== LISTAGEM DE ALOCAÇÕES CADASTRADOS ===\n\n");
        if (!alocacaoList.isEmpty()) {
            for (Alocacao a : alocacaoList) {
                System.out.println(a.exibir());
            }
            return;
        }
        System.out.println("- Nenhuma alocação Cadastrado");
    }

    public void removerColaborador(int colaboradorId) throws EntityInUseException, EntityNotFoundException {
        if (alocacaoList.stream().anyMatch(a -> a.getColaborador().getId() == colaboradorId))
            throw new EntityInUseException("Impossível remover um colaborador que ainda possui alguma alocação ativa!");
        Colaborador colaborador = buscarColaboradorById(colaboradorId);
        colaboradorList.remove(colaborador);
    }

    public void removerRecurso(int recursoId) throws EntityInUseException, EntityNotFoundException {
        if (alocacaoList.stream().anyMatch(a -> a.getRecurso().getId() == recursoId))
            throw new EntityInUseException("Impossível remover um recurso que ainda possui alocação ativa!");
        Recurso recurso = buscarRecursoById(recursoId);
        recursoList.remove(recurso);
    }

    public void alocarRecurso(int colaboradorId, int recursoId, String observation) throws EntityNotFoundException, DomainValidationException {
        Optional<Colaborador> colaboradorAlocacao = colaboradorList.stream()
                .filter(c -> c.getId() == colaboradorId)
                .findFirst();
        Optional<Recurso> recursoAlocacao = recursoList.stream()
                .filter(r -> r.getId() == recursoId)
                .findFirst();

        if (colaboradorAlocacao.isEmpty())
            throw new EntityNotFoundException("Não é possível realizar a alocação para um Colaborador não cadastrado!");
        if (!colaboradorAlocacao.get().isAtivo())
            throw new DomainValidationException("Não é possível realizar a alocação para um Colaborador desaivado!");
        if (recursoAlocacao.isEmpty())
            throw new EntityNotFoundException("Não é possível realizar a alocação para um Recurso não cadastrado!");

        Alocacao alocacao = Alocacao.create(
                colaboradorAlocacao.get(),
                recursoAlocacao.get(),
                LocalDate.now(),
                observation
        );

        alocacaoList.add(alocacao);
    }

    public void devolverRecurso(int colaboradorId, int recursoId) throws EntityNotFoundException, AllocationMismatchException {
        Optional<Alocacao> alocacao = alocacaoList.stream().filter(a -> a.getRecurso().getId() == recursoId).findFirst();

        if (alocacao.isEmpty())
            throw new EntityNotFoundException("Não existe uma alocação para o seguinte ID de recurso: "+ recursoId);
        if (alocacao.get().getColaborador().getId() != colaboradorId)
            throw new AllocationMismatchException("O colaborador informado não possue alocação com esse recurso!");

        alocacao.get().getRecurso().mudarDisponibilidade();
        alocacaoList.remove(alocacao.get());
    }
}