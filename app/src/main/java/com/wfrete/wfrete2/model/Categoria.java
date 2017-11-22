package com.wfrete.wfrete2.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Desenvolvimento 11 on 14/11/2017.
 */

public class Categoria implements Serializable {

    private int id;
    private String nome;
    private String tipo;
    private int s_id;
    private Date s_datahora;

    public Categoria(){
    }

    public Categoria(int id, String nome, String tipo, int s_id, Date s_datahora) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        return this.id == ((Categoria)o).id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

}
