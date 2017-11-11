package com.wfrete.wfrete2.model;

import java.io.Serializable;

/**
 * Created by Desenvolvimento 11 on 04/11/2017.
 */

public class Veiculo implements Serializable {

    private int id;
    private String nome;
    private String modelo;
    private String placa;
    private int ano;


    public Veiculo(){

    }

    public Veiculo(int id, String nome, String modelo, String placa, int ano) {
        this.id = id;
        this.nome = nome;
        this.modelo = modelo;
        this.placa = placa;
        this.ano = ano;
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


    @Override
    public boolean equals(Object o) {
        return this.id == ((Veiculo)o).id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

}
