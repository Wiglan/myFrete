package com.wfrete.wfrete2.model;

import java.io.Serializable;

/**
 * Created by Desenvolvimento 11 on 19/08/2017.
 */

public class Motorista implements Serializable {

    private int id;
    private String nome;
    private String cpf;
    private String telefone;

    public Motorista(){

    }

    public Motorista(int id, String nome, String cpf, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        return this.id == ((Motorista)o).id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
