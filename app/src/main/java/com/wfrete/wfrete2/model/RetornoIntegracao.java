package com.wfrete.wfrete2.model;

import java.util.Date;

/**
 * Created by Desenvolvimento 11 on 20/11/2017.
 */

public class RetornoIntegracao {

    private int id_gerado;
    private String msg;
    private String dthr_integracao;

    public RetornoIntegracao() {

    }


    public RetornoIntegracao(int id_gerado, String msg, String dthr_integracao) {
        this.id_gerado = id_gerado;
        this.msg = msg;
        this.dthr_integracao = dthr_integracao;
    }

    public int getId_gerado() {
        return id_gerado;
    }

    public void setId_gerado(int id_gerado) {
        this.id_gerado = id_gerado;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDthr_integracao() {
        return dthr_integracao;
    }

    public void setDthr_integracao(String dthr_integracao) {
        this.dthr_integracao = dthr_integracao;
    }


    @Override
    public boolean equals(Object o) {
        return this.id_gerado == ((RetornoIntegracao)o).id_gerado;
    }

    @Override
    public int hashCode() {

        return this.id_gerado;
    }
}
