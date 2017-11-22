package com.wfrete.wfrete2.api;

import android.content.Context;
import android.net.ConnectivityManager;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Created by Desenvolvimento 11 on 19/11/2017.
 */

public class Validador {

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



}
