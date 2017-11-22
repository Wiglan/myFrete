package com.wfrete.wfrete2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.wfrete.wfrete2.Funcoes;
import com.wfrete.wfrete2.bd.DbGateway;
import com.wfrete.wfrete2.model.Motorista;

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
public class MotoristaDAO {

    private final String TABLE_MOTORISTAS = "Motoristas";
    private DbGateway gw;

    public MotoristaDAO(Context ctx){
        gw = DbGateway.getInstance(ctx);
    }

    public boolean salvar(String nome, String cpf, String telefone, int s_id, Date s_datahora){
        return salvar(0, nome, cpf, telefone, s_id, s_datahora);
    }

    public boolean salvar(int id, String nome, String cpf, String telefone,int s_id, Date s_datahora){

        ContentValues cv = new ContentValues();

        cv.put("Nome", nome);
        cv.put("Cpf",cpf);
        cv.put("Telefone", telefone);
        cv.put("s_id", s_id);

        if (s_datahora != null){
            cv.put("s_datahora", Funcoes.dateFormatIntegracao.format(s_datahora));
        }
        else {
            cv.putNull("s_datahora");
        }

        if(id > 0)
            return gw.getDatabase().update(TABLE_MOTORISTAS, cv, "ID=?", new String[]{ id + "" }) > 0;
        else
            return gw.getDatabase().insert(TABLE_MOTORISTAS, null, cv) > 0;

        //Aqui usei as boas práticas recomendadas na documentação oficial do Android, onde diz que para INSERTs devemos usar o método insert informando o nome da tabela e um map de content values com as colunas e valores que queremos inserir.
    }

    public boolean salvar_dados_integracao(int id, int s_id, String s_datahora){
        ContentValues cv = new ContentValues();
        cv.put("s_id", s_id);
        cv.put("s_datahora", s_datahora);
        return gw.getDatabase().update(TABLE_MOTORISTAS, cv, "ID=?", new String[]{ id + "" }) > 0;
    }


    public boolean excluir(int id){
        return gw.getDatabase().delete(TABLE_MOTORISTAS, "ID=?", new String[]{ id + "" }) > 0;
    }


    public List<Motorista> ListarMotoristas(){
        List<Motorista> motoristas = new ArrayList<>();
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Motoristas", null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String nome = cursor.getString(cursor.getColumnIndex("NOME"));
            String cpf = cursor.getString(cursor.getColumnIndex("CPF"));
            String telefone = cursor.getString(cursor.getColumnIndex("TELEFONE"));

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

            motoristas.add(new Motorista(id, nome, cpf, telefone, s_id, s_datahora));
        }
        cursor.close();
        return motoristas;
    }

    public Motorista retornarUltimo(){
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM MOTORISTAS ORDER BY ID DESC", null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String nome = cursor.getString(cursor.getColumnIndex("NOME"));
            String cpf = cursor.getString(cursor.getColumnIndex("CPF"));
            String telefone = cursor.getString(cursor.getColumnIndex("TELEFONE"));
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
            return new Motorista(id, nome, cpf, telefone, s_id, s_datahora);
        }

        return null;
    }

    public Motorista motoristaById(int idPesquisa){

        Cursor cursor = gw.getDatabase().query(TABLE_MOTORISTAS,null,"id=?",new String[] {String.valueOf(idPesquisa)},null,null,null, null);

        if(cursor.moveToNext()){

            Motorista motorista = new Motorista();

            motorista.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            motorista.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
            motorista.setCpf(cursor.getString(cursor.getColumnIndex("CPF")));
            motorista.setTelefone(cursor.getString(cursor.getColumnIndex("TELEFONE")));
            motorista.setS_id(cursor.getInt(cursor.getColumnIndex("S_ID")));
            Date s_datahora = null;
            try {
                String dataStr = cursor.getString(cursor.getColumnIndex("S_DATAHORA"));
                if (dataStr != null){
                    s_datahora = Funcoes.dateFormatIntegracao.parse(dataStr);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            motorista.setS_datahora(s_datahora);
            cursor.close();

            return motorista;
        }
        else {
            return null;
        }

    }


}
