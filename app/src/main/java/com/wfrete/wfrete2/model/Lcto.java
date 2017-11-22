package com.wfrete.wfrete2.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * Created by Desenvolvimento 11 on 08/11/2017.
 */

public class Lcto implements Serializable {

    private int id;
    private int frete_id;
    private double valor;
    private int categoria_id;
    private String observacao;
    private Date data;
    private Time hora;
    public Lcto() {
    }

    public Lcto(int id, int frete_id, double valor, int categoria_id, String observacao, Date data, Time hora) {
        this.id = id;
        this.frete_id = frete_id;
        this.valor = valor;
        this.categoria_id = categoria_id;
        this.observacao = observacao;
        this.data = data;
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrete_id() {
        return frete_id;
    }

    public void setFrete_id(int frete_id) {
        this.frete_id = frete_id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    @Override
    public boolean equals(Object o) {
        return this.id == ((Lcto)o).id;
    }

    @Override
    public int hashCode()
    {
        return this.id;
    }

}
