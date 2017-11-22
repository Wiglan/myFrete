package com.wfrete.wfrete2.api.controller;

import android.content.Context;
import android.widget.Toast;

import com.wfrete.wfrete2.api.ServiceGenerator;
import com.wfrete.wfrete2.api.Validador;
import com.wfrete.wfrete2.api.service.MotoristaService;
import com.wfrete.wfrete2.dao.MotoristaDAO;
import com.wfrete.wfrete2.model.Motorista;
import com.wfrete.wfrete2.model.RetornoIntegracao;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Desenvolvimento 11 on 19/11/2017.
 */

public class MotoristaController {

    public static void wsSalvarMotorista(Context context,  Motorista motorista){

        int localizadorMotoristaWS = 0;

        if (Validador.internetAtiva(context)){

            try {
                MotoristaService motoristaService = ServiceGenerator.createService(MotoristaService.class, "TOKEN");

                RetornoIntegracao retornoIntegracao;
                Call<RetornoIntegracao> call;

                //novo motorista
                if (motorista.getS_id() <= 0){
                    localizadorMotoristaWS = Validador.getRandomInt() * -1;
                    motorista.setS_id(localizadorMotoristaWS);

                    call = motoristaService.save(motorista);

                }
                //se ja tem um id, ja foi integrado, entao edita o registro.
                else {

                    motorista.setId(motorista.getS_id());
                    call = motoristaService.update(motorista);

                }
                //executar no serivodr
                retornoIntegracao = call.execute().body();

                //se salvou no servidor, salvar os dados de integracao.
                MotoristaDAO dao = new MotoristaDAO(context);

                if (retornoIntegracao.getDthr_integracao() != null){
                    dao.salvar_dados_integracao(motorista.getId(),
                            retornoIntegracao.getId_gerado(), retornoIntegracao.getDthr_integracao());
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }

        }

    }

}
