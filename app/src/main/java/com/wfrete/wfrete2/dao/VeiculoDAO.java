package com.wfrete.wfrete2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.wfrete.wfrete2.util.Constantes;
import com.wfrete.wfrete2.util.Funcoes;
import com.wfrete.wfrete2.bd.DbGateway;
import com.wfrete.wfrete2.model.Veiculo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Desenvolvimento 11 on 04/11/2017.
 */

public class VeiculoDAO {

    private DbGateway gw;

    public VeiculoDAO(Context ctx){
        gw = DbGateway.getInstance(ctx);
    }

    public boolean salvar(String nome, String modelo, String placa, int ano, int s_id, Date s_datahora){
        return salvar(0, nome, modelo,placa, ano, s_id, s_datahora);
    }

    public boolean salvar(int id, String nome, String modelo,String placa, int ano, int s_id, Date s_datahora){

        ContentValues cv = new ContentValues();

        cv.put("nome", nome);
        cv.put("modelo",modelo);
        cv.put("placa", placa);
        cv.put("ano",ano);

        cv.put("s_id", s_id);

        if (s_datahora != null){
            cv.put("s_datahora", Funcoes.dateFormatIntegracao.format(s_datahora));
        }
        else {
            cv.putNull("s_datahora");
        }

        if(id > 0)
            return gw.getDatabase().update(Constantes.TABLE_VEICULOS, cv, "ID=?", new String[]{ id + "" }) > 0;
        else
            return gw.getDatabase().insert(Constantes.TABLE_VEICULOS, null, cv) > 0;

    }

    public boolean salvar_dados_integracao(int id, int s_id, String s_datahora){
        ContentValues cv = new ContentValues();
        cv.put("s_id", s_id);
        cv.put("s_datahora", s_datahora);
        return gw.getDatabase().update(Constantes.TABLE_VEICULOS, cv, "ID=?", new String[]{ id + "" }) > 0;
    }

    public boolean excluir(int id){
        return gw.getDatabase().delete(Constantes.TABLE_VEICULOS, "ID=?", new String[]{ id + "" }) > 0;
    }


    public List<Veiculo> ListarVeiculos(){
        List<Veiculo> veiculos = new ArrayList<>();
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Veiculos", null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String nome = cursor.getString(cursor.getColumnIndex("NOME"));
            String modelo = cursor.getString(cursor.getColumnIndex("MODELO"));
            String placa = cursor.getString(cursor.getColumnIndex("PLACA"));
            int ano = cursor.getInt(cursor.getColumnIndex("ANO"));

            int s_id = cursor.getInt(cursor.getColumnIndex("S_ID"));

            Date s_datahora = null;
            try {
                String dataStr = cursor.getString(cursor.getColumnIndex("S_DATAHORA"));
                if (dataStr != null){
                    s_datahora = Funcoes.dateFormatIntegracao.parse(dataStr);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


            veiculos.add(new Veiculo(id, nome, modelo, placa, ano, s_id, s_datahora));
        }
        cursor.close();
        return veiculos;
    }

    public Veiculo retornarUltimo(){
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Veiculos ORDER BY ID DESC", null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String nome = cursor.getString(cursor.getColumnIndex("NOME"));
            String modelo = cursor.getString(cursor.getColumnIndex("MODELO"));
            String placa = cursor.getString(cursor.getColumnIndex("PLACA"));
            int ano = cursor.getInt(cursor.getColumnIndex("ANO"));

            int s_id = cursor.getInt(cursor.getColumnIndex("S_ID"));

            Date s_datahora = null;
            try {
                String dataStr = cursor.getString(cursor.getColumnIndex("S_DATAHORA"));
                if (dataStr != null){
                    s_datahora = Funcoes.dateFormatIntegracao.parse(dataStr);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cursor.close();
            return new Veiculo(id, nome, modelo, placa, ano, s_id, s_datahora);
        }

        return null;
    }

    public Veiculo veiculoById(int idPesquisa){

        Cursor cursor = gw.getDatabase().query(Constantes.TABLE_VEICULOS,null,"id=?",new String[] {String.valueOf(idPesquisa)},null,null,null, null);

        if(cursor.moveToNext()){

            Veiculo veiculo = new Veiculo();

            veiculo.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            veiculo.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
            veiculo.setModelo(cursor.getString(cursor.getColumnIndex("MODELO")));
            veiculo.setPlaca(cursor.getString(cursor.getColumnIndex("PLACA")));
            veiculo.setAno(cursor.getInt(cursor.getColumnIndex("ANO")));

            veiculo.setS_id(cursor.getInt(cursor.getColumnIndex("S_ID")));
            Date s_datahora = null;
            try {
                String dataStr = cursor.getString(cursor.getColumnIndex("S_DATAHORA"));
                if (dataStr != null){
                    s_datahora = Funcoes.dateFormatIntegracao.parse(dataStr);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            veiculo.setS_datahora(s_datahora);

            cursor.close();

            return veiculo;
        }
        else {
            return null;
        }

    }

    public boolean existeVinculos(int veiculosId) {

        Cursor cursor = gw.getDatabase().query(Constantes.TABLE_FRETES,null,"VEICULO_ID=?", new String[] {String.valueOf(veiculosId)},null,null,null, null);

        if(cursor.moveToNext()){
            return true;
        }

        return false;
    }
}
