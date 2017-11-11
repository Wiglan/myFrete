package com.wfrete.wfrete2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.wfrete.wfrete2.bd.DbGateway;
import com.wfrete.wfrete2.model.Frete;

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
public class FreteDAO {

    private final String TABLE_FRETES = "Fretes";
    private DbGateway gw;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public FreteDAO(Context ctx)
    {
        gw = DbGateway.getInstance(ctx);
    }

    public boolean salvar(int nro_cte, String origem, String destino, double vlr_ton, double peso, double vlr_total, Date data_abertura, Date data_encerramento, int motorista_id, int veiculo_id){
        return salvar(0, nro_cte, origem, destino, vlr_ton, peso, vlr_total, data_abertura, data_encerramento, motorista_id, veiculo_id);
    }

    public boolean salvar(int id, int nro_cte, String origem, String destino, double vlr_ton, double peso, double vlr_total, Date data_abertura, Date data_encerramento, int motorista_id, int veiculo_id){

        ContentValues cv = new ContentValues();

        cv.put("nro_cte", nro_cte);
        cv.put("origem", origem);
        cv.put("destino", destino);
        cv.put("vlr_ton", vlr_ton);
        cv.put("peso", peso);
        cv.put("vlr_total", vlr_total);
        cv.put("data_abertura", dateFormat.format(data_abertura));
        cv.put("data_encerramento", dateFormat.format(data_encerramento));
        cv.put("motorista_id", motorista_id);
        cv.put("veiculo_id", veiculo_id);

        if(id > 0)
            return gw.getDatabase().update(TABLE_FRETES, cv, "ID=?", new String[]{ id + "" }) > 0;
        else
            return gw.getDatabase().insert(TABLE_FRETES, null, cv) > 0;

    }

    public boolean excluir(int id){
        return gw.getDatabase().delete(TABLE_FRETES, "ID=?", new String[]{ id + "" }) > 0;
    }

    public List<Frete> ListarFretes(){
        List<Frete> fretes = new ArrayList<>();
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Fretes", null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            int nro_cte = cursor.getInt(cursor.getColumnIndex("NRO_CTE"));
            String origem = cursor.getString(cursor.getColumnIndex("ORIGEM"));
            String destino = cursor.getString(cursor.getColumnIndex("DESTINO"));
            double vlr_ton = cursor.getDouble(cursor.getColumnIndex("VLR_TON"));
            double peso = cursor.getDouble(cursor.getColumnIndex("PESO"));
            double vlr_total = cursor.getDouble(cursor.getColumnIndex("VLR_TOTAL"));

            Date data_abertura = null;
            Date data_encerramento = null;
            try {
                data_abertura = dateFormat.parse(cursor.getString(cursor.getColumnIndex("DATA_ABERTURA")));
                data_encerramento = dateFormat.parse(cursor.getString(cursor.getColumnIndex("DATA_ENCERRAMENTO")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int motorista_id = cursor.getInt(cursor.getColumnIndex("MOTORISTA_ID"));
            int veiculo_id = cursor.getInt(cursor.getColumnIndex("VEICULO_ID"));

            fretes.add(new Frete(id,nro_cte,origem,destino,vlr_ton,peso,vlr_total,data_abertura, data_encerramento, motorista_id, veiculo_id));
        }
        cursor.close();
        return fretes;
    }

    public Frete retornarUltimo(){

        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Fretes ORDER BY ID DESC", null);

        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            int nro_cte = cursor.getInt(cursor.getColumnIndex("NRO_CTE"));
            String origem = cursor.getString(cursor.getColumnIndex("ORIGEM"));
            String destino = cursor.getString(cursor.getColumnIndex("DESTINO"));
            double vlr_ton = cursor.getDouble(cursor.getColumnIndex("VLR_TON"));
            double peso = cursor.getDouble(cursor.getColumnIndex("PESO"));
            double vlr_total = cursor.getDouble(cursor.getColumnIndex("VLR_TOTAL"));

            Date data_abertura = null;
            Date data_encerramento = null;
            try {
                data_abertura = dateFormat.parse(cursor.getString(cursor.getColumnIndex("DATA_ABERTURA")));
                data_encerramento = dateFormat.parse(cursor.getString(cursor.getColumnIndex("DATA_ENCERRAMENTO")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int motorista_id = cursor.getInt(cursor.getColumnIndex("MOTORISTA_ID"));
            int veiculo_id = cursor.getInt(cursor.getColumnIndex("VEICULO_ID"));


            cursor.close();
            return new Frete(id,nro_cte,origem,destino,vlr_ton,peso,vlr_total,data_abertura, data_encerramento, motorista_id, veiculo_id);
        }

        return null;
    }

    public Frete freteById(int idPesquisa){

        Cursor cursor = gw.getDatabase().query(TABLE_FRETES,null,"id=?",new String[] {String.valueOf(idPesquisa)},null,null,null, null);

        if(cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            int nro_cte = cursor.getInt(cursor.getColumnIndex("NRO_CTE"));
            String origem = cursor.getString(cursor.getColumnIndex("ORIGEM"));
            String destino = cursor.getString(cursor.getColumnIndex("DESTINO"));
            double vlr_ton = cursor.getDouble(cursor.getColumnIndex("VLR_TON"));
            double peso = cursor.getDouble(cursor.getColumnIndex("PESO"));
            double vlr_total = cursor.getDouble(cursor.getColumnIndex("VLR_TOTAL"));

            Date data_abertura = null;
            Date data_encerramento = null;
            try {
                data_abertura = dateFormat.parse(cursor.getString(cursor.getColumnIndex("DATA_ABERTURA")));
                data_encerramento = dateFormat.parse(cursor.getString(cursor.getColumnIndex("DATA_ENCERRAMENTO")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int motorista_id = cursor.getInt(cursor.getColumnIndex("MOTORISTA_ID"));
            int veiculo_id = cursor.getInt(cursor.getColumnIndex("VEICULO_ID"));


            cursor.close();
            return new Frete(id,nro_cte,origem,destino,vlr_ton,peso,vlr_total,data_abertura, data_encerramento, motorista_id, veiculo_id);

        }
        else {
            return null;
        }

    }


}
