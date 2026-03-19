package com.resources.human.domain;

import java.time.LocalDate;

public class Colaborador {
    private int id;
    private String nome;
    private String cargo;
    private double salario;
    private boolean ativo;
    private LocalDate dataDeAdmissao;


    private Colaborador(
            int id,
            String nome,
            String cargo,
            double salario,
            LocalDate dataDeAdmissao
    ) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.salario = salario;
        this.dataDeAdmissao = dataDeAdmissao;
        this.ativo = true;
    }

    public static Colaborador create(
            int id,
            String nome,
            String cargo,
            double salario,
            LocalDate dataDeAdmissao
    ) {
        return new Colaborador(
            id,
            nome,
            cargo,
            salario,
            dataDeAdmissao
      );
    }

    public void promover(String novoCargo, double novoSalario) {
        this.cargo = novoCargo;
        this.salario = novoSalario;
    }

    public int getId() {
        return this.id;
    }
}