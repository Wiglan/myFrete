package com.wfrete.wfrete2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.wfrete.wfrete2.bd.DbGateway;
import com.wfrete.wfrete2.model.Veiculo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Desenvolvimento 11 on 04/11/2017.
 */

public class VeiculoDAO {

    private final String TABLE_VEICULOS = "Veiculos";
    private DbGateway gw;

    public VeiculoDAO(Context ctx){
        gw = DbGateway.getInstance(ctx);
    }

    public boolean salvar(String nome, String modelo, String placa, int ano){
        return salvar(0, nome, modelo,placa, ano);
    }

    public boolean salvar(int id, String nome, String modelo,String placa, int ano){

        ContentValues cv = new ContentValues();

        cv.put("nome", nome);
        cv.put("modelo",modelo);
        cv.put("placa", placa);
        cv.put("ano",ano);

        if(id > 0)
            return gw.getDatabase().update(TABLE_VEICULOS, cv, "ID=?", new String[]{ id + "" }) > 0;
        else
            return gw.getDatabase().insert(TABLE_VEICULOS, null, cv) > 0;

    }

    public boolean excluir(int id){
        return gw.getDatabase().delete(TABLE_VEICULOS, "ID=?", new String[]{ id + "" }) > 0;
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
            veiculos.add(new Veiculo(id, nome, modelo, placa, ano));
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
            cursor.close();
            return new Veiculo(id, nome, modelo, placa, ano);
        }

        return null;
    }

    public Veiculo veiculoById(int idPesquisa){

        Cursor cursor = gw.getDatabase().query(TABLE_VEICULOS,null,"id=?",new String[] {String.valueOf(idPesquisa)},null,null,null, null);

        if(cursor.moveToNext()){

            Veiculo veiculo = new Veiculo();

            veiculo.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            veiculo.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
            veiculo.setModelo(cursor.getString(cursor.getColumnIndex("MODELO")));
            veiculo.setPlaca(cursor.getString(cursor.getColumnIndex("PLACA")));
            veiculo.setAno(cursor.getInt(cursor.getColumnIndex("ANO")));
            cursor.close();

            return veiculo;
        }
        else {
            return null;
        }

    }
}
