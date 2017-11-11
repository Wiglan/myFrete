package com.wfrete.wfrete2.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Desenvolvimento 11 on 19/08/2017.
 */


//Cliente do banco de dados.
public class DbGateway {

    private static DbGateway gw;
    private SQLiteDatabase db;

    private DbGateway(Context ctx){
        DatabaseHandler handler = new DatabaseHandler(ctx);
        db = handler.getWritableDatabase();
    }

    public static DbGateway getInstance(Context ctx){

        //Design Pattern Singleton
        //aqui sempre utiliza a mesm conexao com o banco da dados, para evitar que o app trablhe com multiplas conexoes simultaneas.
        if(gw == null)
            gw = new DbGateway(ctx);
        return gw;
    }

    public SQLiteDatabase getDatabase(){
        return this.db;
    }

}
