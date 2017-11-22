package com.wfrete.wfrete2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.wfrete.wfrete2.Funcoes;
import com.wfrete.wfrete2.bd.DbGateway;
import com.wfrete.wfrete2.model.Categoria;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Desenvolvimento 11 on 04/11/2017.
 */

public class CategoriaDAO {

    private final String TABLE_CATEGORIAS = "Categorias";
    private DbGateway gw;

    public CategoriaDAO(Context ctx){
        gw = DbGateway.getInstance(ctx);
    }

    public boolean salvar(String nome, String tipo, int s_id, Date s_datahora){
        return salvar(0, nome, tipo, s_id, s_datahora);
    }

    public boolean salvar(int id, String nome, String tipo, int s_id, Date s_datahora){

        ContentValues cv = new ContentValues();

        cv.put("nome", nome);
        cv.put("tipo",tipo);
        cv.put("s_id", s_id);

        if (s_datahora != null){
            cv.put("s_datahora", Funcoes.dateFormatIntegracao.format(s_datahora));
        }
        else {
            cv.putNull("s_datahora");
        }

        if(id > 0)
            return gw.getDatabase().update(TABLE_CATEGORIAS, cv, "ID=?", new String[]{ id + "" }) > 0;
        else
            return gw.getDatabase().insert(TABLE_CATEGORIAS, null, cv) > 0;

    }

    public boolean excluir(int id){
        return gw.getDatabase().delete(TABLE_CATEGORIAS, "ID=?", new String[]{ id + "" }) > 0;
    }


    public boolean salvar_dados_integracao(int id, int s_id, String s_datahora){
        ContentValues cv = new ContentValues();
        cv.put("s_id", s_id);
        cv.put("s_datahora", s_datahora);
        return gw.getDatabase().update(TABLE_CATEGORIAS, cv, "ID=?", new String[]{ id + "" }) > 0;
    }


    public List<Categoria> ListarCategorias(){
        List<Categoria> categorias = new ArrayList<>();
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Categorias ORDER BY TIPO, NOME", null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String nome = cursor.getString(cursor.getColumnIndex("NOME"));
            String tipo = cursor.getString(cursor.getColumnIndex("TIPO"));

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

            categorias.add(new Categoria(id, nome, tipo, s_id, s_datahora));
        }
        cursor.close();
        return categorias;
    }

    public Categoria retornarUltimo(){
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Categorias ORDER BY ID DESC", null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String nome = cursor.getString(cursor.getColumnIndex("NOME"));
            String tipo = cursor.getString(cursor.getColumnIndex("TIPO"));

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
            return new Categoria(id, nome, tipo, s_id, s_datahora);
        }

        return null;
    }

    public Categoria categoriaById(int idPesquisa){

        Cursor cursor = gw.getDatabase().query(TABLE_CATEGORIAS,null,"id=?",new String[] {String.valueOf(idPesquisa)},null,null,null, null);

        if(cursor.moveToNext()){

            Categoria categoria = new Categoria();

            categoria.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            categoria.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
            categoria.setTipo(cursor.getString(cursor.getColumnIndex("TIPO")));

            categoria.setS_id(cursor.getInt(cursor.getColumnIndex("S_ID")));
            Date s_datahora = null;
            try {
                String dataStr = cursor.getString(cursor.getColumnIndex("S_DATAHORA"));
                if (dataStr != null){
                    s_datahora = Funcoes.dateFormatIntegracao.parse(dataStr);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            categoria.setS_datahora(s_datahora);

            cursor.close();

            return categoria;
        }
        else {
            return null;
        }

    }

    public Categoria categoriaByNome(String nome){

        Cursor cursor = gw.getDatabase().query(TABLE_CATEGORIAS,null,"UPPER(NOME)=?", new String[] {nome.toUpperCase()},null,null,null, null);

        if(cursor.moveToNext()){

            Categoria categoria = new Categoria();
            categoria.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            categoria.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
            categoria.setTipo(cursor.getString(cursor.getColumnIndex("TIPO")));
            categoria.setS_id(cursor.getInt(cursor.getColumnIndex("S_ID")));
            Date s_datahora = null;
            try {
                String dataStr = cursor.getString(cursor.getColumnIndex("S_DATAHORA"));
                if (dataStr != null){
                    s_datahora = Funcoes.dateFormatIntegracao.parse(dataStr);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            categoria.setS_datahora(s_datahora);

            cursor.close();
            return categoria;
        }
        else {
            return null;
        }

    }


}
