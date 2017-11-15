package com.wfrete.wfrete2.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Desenvolvimento 11 on 19/08/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "wfrete2";
    private final String CREATE_TABLE_MOTORISTAS = "CREATE TABLE MOTORISTAS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME TEXT NOT NULL, CPF TEXT, TELEFONE TEXT NOT NULL)";
    private final String CREATE_TABLE_VEICULOS = "CREATE TABLE VEICULOS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME TEXT NOT NULL, MODELO TEXT, PLACA TEXT, ANO INTEGER)";
    private final String CREATE_TABLE_FRETES = "CREATE TABLE FRETES (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                              "NRO_CTE INTEGER,ORIGEM TEXT NOT NULL,DESTINO TEXT NOT NULL, " +
                                              "VLR_TON DOUBLE PRECISION, PESO DOUBLE PRECISION, VLR_TOTAL DOUBLE PRECISION, "+
                                              "DATA_ABERTURA DATE, DATA_ENCERRAMENTO DATE, MOTORISTA_ID INTEGER, VEICULO_ID INTEGER, CLIENTE TEXT," +
                                              "FOREIGN KEY(MOTORISTA_ID) REFERENCES MOTORISTAS(ID),FOREIGN KEY(VEICULO_ID) REFERENCES VEICULOS(ID) )";
    private final String CREATE_TABLE_CATEGORIAS = "CREATE TABLE CATEGORIAS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME TEXT NOT NULL, TIPO TEXT NOT NULL)";
    private final String CREATE_TABLE_FRETE_LCTO = "CREATE TABLE FRETE_LCTO (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "FRETE_ID INTEGER NOT NULL,VALOR DOUBLE PRECISION NOT NULL, CATEGORIA_ID INTEGER NOT NULL, OBSERVACAO TEXT, " +
            "DATA DATE NOT NULL, HORA LONG, " +
            "FOREIGN KEY(CATEGORIA_ID) REFERENCES CATEGORIAS(ID), FOREIGN KEY(FRETE_ID) REFERENCES FRETES(ID))";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOTORISTAS);
        db.execSQL(CREATE_TABLE_VEICULOS);
        db.execSQL(CREATE_TABLE_FRETES);
        db.execSQL(CREATE_TABLE_CATEGORIAS);
       db.execSQL(CREATE_TABLE_FRETE_LCTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS FRETE_LCTO");
        db.execSQL("DROP TABLE IF EXISTS FRETES");
        db.execSQL("DROP TABLE IF EXISTS MOTORISTAS");
        db.execSQL("DROP TABLE IF EXISTS VEICULOS");
        db.execSQL("DROP TABLE IF EXISTS CATEGORIAS");

    }
}
