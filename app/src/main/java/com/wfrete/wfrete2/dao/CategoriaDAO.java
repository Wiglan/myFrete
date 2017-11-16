package com.wfrete.wfrete2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.wfrete.wfrete2.bd.DbGateway;
import com.wfrete.wfrete2.model.Categoria;

import java.util.ArrayList;
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

    public boolean salvar(String nome, String tipo){
        return salvar(0, nome, tipo);
    }

    public boolean salvar(int id, String nome, String tipo){

        ContentValues cv = new ContentValues();

        cv.put("nome", nome);
        cv.put("tipo",tipo);

        if(id > 0)
            return gw.getDatabase().update(TABLE_CATEGORIAS, cv, "ID=?", new String[]{ id + "" }) > 0;
        else
            return gw.getDatabase().insert(TABLE_CATEGORIAS, null, cv) > 0;

    }

    public boolean excluir(int id){
        return gw.getDatabase().delete(TABLE_CATEGORIAS, "ID=?", new String[]{ id + "" }) > 0;
    }


    public List<Categoria> ListarCategorias(){
        List<Categoria> categorias = new ArrayList<>();
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Categorias ORDER BY TIPO, NOME", null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String nome = cursor.getString(cursor.getColumnIndex("NOME"));
            String tipo = cursor.getString(cursor.getColumnIndex("TIPO"));
            categorias.add(new Categoria(id, nome, tipo));
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
            cursor.close();
            return new Categoria(id, nome, tipo);
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
            cursor.close();
            return categoria;
        }
        else {
            return null;
        }

    }


}
