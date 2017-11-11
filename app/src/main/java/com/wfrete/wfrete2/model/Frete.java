package com.wfrete.wfrete2.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Desenvolvimento 11 on 08/11/2017.
 */

public class Frete implements Serializable {

    private int id;
    private int nro_cte;
    private String origem;
    private String destino;
    private double vlr_ton;
    private double peso;
    private double vlr_total;
    private Date data_abertura;
    private Date data_encerramento;
    private int motorista_id;
    private int veiculo_id;

    public Frete() {
    }

    public Frete(int id, int nro_cte, String origem, String destino, double vlr_ton, double peso, double vlr_total, Date data_abertura, Date data_encerramento, int motorista_id, int veiculo_id) {
        this.id = id;
        this.nro_cte = nro_cte;
        this.origem = origem;
        this.destino = destino;
        this.vlr_ton = vlr_ton;
        this.peso = peso;
        this.vlr_total = vlr_total;
        this.data_abertura = data_abertura;
        this.data_encerramento = data_encerramento;
        this.motorista_id = motorista_id;
        this.veiculo_id = veiculo_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNro_cte() {
        return nro_cte;
    }

    public void setNro_cte(int nro_cte) {
        this.nro_cte = nro_cte;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getVlr_ton() {
        return vlr_ton;
    }

    public void setVlr_ton(double vlr_ton) {
        this.vlr_ton = vlr_ton;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getVlr_total() {
        return vlr_total;
    }

    public void setVlr_total(double vlr_total) {
        this.vlr_total = vlr_total;
    }

    public Date getData_abertura() {
        return data_abertura;
    }

    public void setData_abertura(Date data_abertura) {
        this.data_abertura = data_abertura;
    }

    public Date getData_encerramento() {
        return data_encerramento;
    }

    public void setData_encerramento(Date data_encerramento) {
        this.data_encerramento = data_encerramento;
    }

    public int getMotorista_id() {
        return motorista_id;
    }

    public void setMotorista_id(int motorista_id) {
        this.motorista_id = motorista_id;
    }

    public int getVeiculo_id() {
        return veiculo_id;
    }

    public void setVeiculo_id(int veiculo_id) {
        this.veiculo_id = veiculo_id;
    }

    @Override
    public boolean equals(Object o) {
        return this.id == ((Frete)o).id;
    }

    @Override
    public int hashCode()
    {
        return this.id;
    }

}
