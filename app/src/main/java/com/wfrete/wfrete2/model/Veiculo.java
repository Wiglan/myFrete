package com.wfrete.wfrete2.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Desenvolvimento 11 on 04/11/2017.
 */

public class Veiculo implements Serializable {

    private int id;
    private String nome;
    private String modelo;
    private String placa;
    private int ano;
    private int s_id;
    private Date s_datahora;


    public Veiculo(){

    }

    public Veiculo(int id, String nome, String modelo, String placa, int ano, int s_id, Date s_datahora) {
        this.id = id;
        this.nome = nome;
        this.modelo = modelo;
        this.placa = placa;
        this.ano = ano;
        this.s_datahora = s_datahora;
        this.s_id = s_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
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

    @Override
    public boolean equals(Object o) {
        return this.id == ((Veiculo)o).id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

}
