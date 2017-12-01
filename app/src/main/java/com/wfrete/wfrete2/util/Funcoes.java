package com.wfrete.wfrete2.util;


import android.content.Context;
import android.net.ConnectivityManager;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static Date gerarData(Date dataLcto, Time horaLcto){

        int ano;
        int mes;
        int dia;
        int minuto;
        int hora;
        int segundo;

        ano = mes = dia = minuto = hora = segundo = 0;

        if (dataLcto != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dataLcto);
            ano = calendar.get(Calendar.YEAR);
            mes = calendar.get(Calendar.MONTH);
            dia = calendar.get(Calendar.DAY_OF_MONTH);
        }
        if (horaLcto != null){
            Calendar calendar = Calendar.getInstance();
            hora = calendar.get(Calendar.HOUR_OF_DAY);
            minuto = calendar.get(Calendar.MINUTE);
            segundo = calendar.get(Calendar.SECOND);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(ano, mes, dia, hora, minuto, segundo);

        return calendar.getTime();
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
