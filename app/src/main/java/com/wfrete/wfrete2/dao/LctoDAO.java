package com.wfrete.wfrete2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.wfrete.wfrete2.bd.DbGateway;
import com.wfrete.wfrete2.model.Lcto;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Desenvolvimento 11 on 19/08/2017.
 */

//o dao equivale ao CONTROLLER

//Design Pattern Data Access Object
//Fornece uma interface para que as as camadas de aplicação se comuniquem com o datasource.
public class LctoDAO {

    private final String TABLE_LCTO = "Frete_Lcto";
    private DbGateway gw;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public LctoDAO(Context ctx)
    {
        gw = DbGateway.getInstance(ctx);
    }

    public boolean salvar(int frete_id, double valor, int categoria_id, String obs, Date data, Time hora){
        return salvar(0, frete_id, valor, categoria_id, obs, data, hora);
    }

    public boolean salvar(int id, int frete_id, double valor, int categoria_id, String obs, Date data, Time hora){

        ContentValues cv = new ContentValues();

        cv.put("frete_id", frete_id);
        cv.put("valor", valor);
        cv.put("categoria_id", categoria_id);
        cv.put("observacao", obs);
        cv.put("data", dateFormat.format(data));
        cv.put("hora", hora.getTime());


        if(id > 0)
            return gw.getDatabase().update(TABLE_LCTO, cv, "ID=?", new String[]{ id + "" }) > 0;
        else
            return gw.getDatabase().insert(TABLE_LCTO, null, cv) > 0;

    }

    public boolean excluir(int id){
        return gw.getDatabase().delete(TABLE_LCTO, "ID=?", new String[]{ id + "" }) > 0;
    }

    public List<Lcto> ListarLctosByFrete(int frete_id_pesquisa){
        List<Lcto> lctos = new ArrayList<>();
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Frete_Lcto WHERE FRETE_ID = " + frete_id_pesquisa + " ORDER BY DATA, HORA DESC", null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            int frete_id = cursor.getInt(cursor.getColumnIndex("FRETE_ID"));
            int categoria_id = cursor.getInt(cursor.getColumnIndex("CATEGORIA_ID"));
            String observacao = cursor.getString(cursor.getColumnIndex("OBSERVACAO"));
            double valor = cursor.getDouble(cursor.getColumnIndex("VALOR"));
            Date data = null;

            try {
                data = dateFormat.parse(cursor.getString(cursor.getColumnIndex("DATA")));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Time hora = new Time(cursor.getLong(cursor.getColumnIndex("HORA")));

            lctos.add(new Lcto(id,frete_id, valor, categoria_id, observacao, data, hora));
        }
        cursor.close();
        return lctos;
    }

    public Lcto retornarUltimo(){

        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM FRETE_LCTO ORDER BY ID DESC", null);

        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            int frete_id = cursor.getInt(cursor.getColumnIndex("FRETE_ID"));
            int categoria_id = cursor.getInt(cursor.getColumnIndex("CATEGORIA_ID"));
            String observacao = cursor.getString(cursor.getColumnIndex("OBSERVACAO"));
            double valor = cursor.getDouble(cursor.getColumnIndex("VALOR"));
            Date data = null;

            try {
                data = dateFormat.parse(cursor.getString(cursor.getColumnIndex("DATA")));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Time hora = new Time(cursor.getLong(cursor.getColumnIndex("HORA")));
            cursor.close();
            return new Lcto(id,frete_id, valor, categoria_id, observacao, data, hora);
        }

        return null;
    }

    public Lcto lctoById(int idPesquisa){

        Cursor cursor = gw.getDatabase().query(TABLE_LCTO,null,"id=?",new String[] {String.valueOf(idPesquisa)},null,null,null, null);

        if(cursor.moveToNext()){


            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            int frete_id = cursor.getInt(cursor.getColumnIndex("FRETE_ID"));
            int categoria_id = cursor.getInt(cursor.getColumnIndex("CATEGORIA_ID"));
            String observacao = cursor.getString(cursor.getColumnIndex("OBSERVACAO"));
            double valor = cursor.getDouble(cursor.getColumnIndex("VALOR"));
            Date data = null;

            try {
                data = dateFormat.parse(cursor.getString(cursor.getColumnIndex("DATA")));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Time hora = new Time(cursor.getLong(cursor.getColumnIndex("HORA")));
            cursor.close();
            return new Lcto(id,frete_id, valor, categoria_id, observacao, data, hora);

        }
        else {
            return null;
        }

    }


}
