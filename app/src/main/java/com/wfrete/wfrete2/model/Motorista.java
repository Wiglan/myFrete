package com.wfrete.wfrete2.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Desenvolvimento 11 on 19/08/2017.
 */

public class Motorista implements Serializable {

    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private int s_id;
    private Date s_datahora;

    public Motorista(){

    }

    public Motorista(int id, String nome, String cpf, String telefone, int s_id, Date s_datahora) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.s_datahora = s_datahora;
        this.s_id = s_id;
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

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public Date getS_datahora() {
        return s_datahora;
    }

    public void setS_datahora(Date s_datahora) {
        this.s_datahora = s_datahora;
    }
}
