package com.resources.human;

import com.resources.human.application.SistemaERS;
import com.resources.human.domain.enums.Categoria;
import com.resources.human.domain.exceptions.*;

import java.time.LocalDate;

public class Program {

    public static void main(String[] args) {

        SistemaERS sistema = new SistemaERS();

        try {
            // =========================
            // 1. CADASTRO
            // =========================
            System.out.println("\n=== CADASTRANDO COLABORADOR ===");
            sistema.cadastrarColaborador(
                    1,
                    "Davi",
                    "Desenvolvedor",
                    5000.0,
                    LocalDate.of(2023, 1, 10)
            );

            System.out.println("\n=== CADASTRANDO RECURSO ===");
            sistema.cadastrarRecurso(
                    1,
                    "Notebook Dell",
                    Categoria.NOTEBOOK,
                    true,
                    4500.0
            );

            // =========================
            // 2. LISTAGEM INICIAL
            // =========================
            System.out.println("\n=== LISTANDO DADOS INICIAIS ===");
            sistema.listarColaboradores();
            sistema.listarRecursos();

            // =========================
            // 3. ALOCAÇÃO
            // =========================
            System.out.println("\n=== REALIZANDO ALOCAÇÃO ===");
            sistema.alocarRecurso(1, 1, "A devolução será realizada quando eu quiser.");

            sistema.listarAlocacoes();

            // =========================
            // 4. BUSCA
            // =========================
            System.out.println("\n=== BUSCANDO COLABORADOR ===");
            System.out.println(sistema.buscarColaboradorById(1).toString());

            System.out.println("\n=== BUSCANDO RECURSO ===");
            System.out.println(sistema.buscarRecursoById(1).toString());

            // =========================
            // 5. DEVOLUÇÃO
            // =========================
            System.out.println("\n=== DEVOLVENDO RECURSO ===");
            sistema.devolverRecurso(1, 1);

            sistema.listarAlocacoes();

            // =========================
            // 6. REMOÇÃO
            // =========================
            System.out.println("\n=== REMOVENDO COLABORADOR E RECURSO ===");
            sistema.removerColaborador(1);
            sistema.removerRecurso(1);

            sistema.listarColaboradores();
            sistema.listarRecursos();

        } catch (AlreadyExistsByIdException |
                 EntityNotFoundException |
                 DomainValidationException |
                 AllocationMismatchException |
                 EntityInUseException e) {

            System.out.println("Erro: " + e.getMessage());
        }
    }
}