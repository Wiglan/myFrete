package com.wfrete.wfrete2.api.controller;

import android.content.Context;

import com.wfrete.wfrete2.api.ServiceGenerator;
import com.wfrete.wfrete2.api.service.LctoService;
import com.wfrete.wfrete2.dao.LctoDAO;
import com.wfrete.wfrete2.model.Lcto;
import com.wfrete.wfrete2.model.RetornoIntegracao;
import com.wfrete.wfrete2.util.Funcoes;

import retrofit2.Call;

/**
 * Created by Desenvolvimento 11 on 19/11/2017.
 */

public class LctoController {

    public static void wsSalvarLcto(Context context,  Lcto lcto){

        int localizadorLctoWS = 0;
        int idLocalLcto = lcto.getId();

        if (Funcoes.internetAtiva(context)){

            try {
                LctoService lctoService = ServiceGenerator.createService(LctoService.class, "TOKEN");

                RetornoIntegracao retornoIntegracao;
                Call<RetornoIntegracao> call;

                //necessario por que da erro de UNserializable JSON nos campos de data e hora.
                lcto.setDatahoraStringLcto(Funcoes.dateFormatIntegracao.format(Funcoes.gerarData(lcto.getData(), lcto.getHora())));
                lcto.setData(null);
                lcto.setHora(null);

                //novo lcto
                if (lcto.getS_id() <= 0){
                    localizadorLctoWS = Funcoes.getRandomInt() * -1;
                    lcto.setS_id(localizadorLctoWS);

                    call = lctoService.save(lcto);

                }
                //se ja tem um id, ja foi integrado, entao edita o registro.
                else {

                    //muda o id local pelo id do servidor para chamar a edicao la
                    lcto.setId(lcto.getS_id());
                    call = lctoService.update(lcto);

                }
                //executar no serivodr
                retornoIntegracao = call.execute().body();

                //se salvou no servidor, salvar os dados de integracao.
                LctoDAO dao = new LctoDAO(context);

                if (retornoIntegracao.getDthr_integracao() != null){
                    dao.salvar_dados_integracao(idLocalLcto,
                            retornoIntegracao.getId_gerado(), retornoIntegracao.getDthr_integracao());
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }

        }

    }

}
