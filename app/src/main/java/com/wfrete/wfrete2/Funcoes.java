package com.wfrete.wfrete2;


import android.content.Context;
import android.net.ConnectivityManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Desenvolvimento 11 on 19/11/2017.
 */

public class Funcoes {

    public static SimpleDateFormat dateFormatIntegracao;
    private static SimpleDateFormat timeFormat;


    static {
        dateFormatIntegracao = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        timeFormat = new SimpleDateFormat("dd-MMM/ HH:mm:ss");
    }

    public static boolean internetAtiva(Context context) {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

    public static int getRandomInt() {
        Random random = new Random();
        return random.nextInt((99999999 - 999) + 1) + 999;
    }


    public static String formataMsgIntegracao(Date data){

        if (data != null){
            String str = "Sincronizado em " + timeFormat.format(data);
            str = str.replace("-", " de ");
            str = str.replace("/", " as ");
            return str;
        }
        else {
            return "Não Sincronizado";
        }
    }


}
