package com.wfrete.wfrete2.model;

import java.io.Serializable;

/**
 * Created by Desenvolvimento 11 on 14/11/2017.
 */

public class Categoria implements Serializable {

    private int id;
    private String nome;
    private String tipo;


    public Categoria(){
    }

    public Categoria(int id, String nome, String tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        return this.id == ((Categoria)o).id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

}
