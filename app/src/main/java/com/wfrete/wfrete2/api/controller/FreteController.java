package com.wfrete.wfrete2.api.controller;

import android.content.Context;

import com.wfrete.wfrete2.api.ServiceGenerator;
import com.wfrete.wfrete2.api.service.FreteService;
import com.wfrete.wfrete2.dao.FreteDAO;
import com.wfrete.wfrete2.model.Frete;
import com.wfrete.wfrete2.model.RetornoIntegracao;
import com.wfrete.wfrete2.util.Funcoes;

import retrofit2.Call;

/**
 * Created by Desenvolvimento 11 on 19/11/2017.
 */

public class FreteController {

    public static boolean wsSalvarFrete(Context context,  Frete frete){

        int localizadorFreteWS = 0;
        int idLocalFrete = frete.getId();

        if (Funcoes.internetAtiva(context)){

            try {
                FreteService freteService = ServiceGenerator.createService(FreteService.class, "TOKEN");

                RetornoIntegracao retornoIntegracao;
                Call<RetornoIntegracao> call;

                //necessario por que da erro de UNserializable JSON nos campos de data e hora.

                if (frete.getData_encerramento() != null){
                    frete.setDataEncerramentoStringFrete(Funcoes.dateFormatIntegracao.format(frete.getData_encerramento()));
                }

                if (frete.getData_abertura() != null){
                    frete.setDataAberturaStringFrete(Funcoes.dateFormatIntegracao.format(frete.getData_abertura()));
                }

                frete.setData_encerramento(null);
                frete.setData_abertura(null);

                //novo frete
                if (frete.getS_id() <= 0){
                    localizadorFreteWS = Funcoes.getRandomInt() * -1;
                    frete.setS_id(localizadorFreteWS);

                    call = freteService.save(frete);

                }
                //se ja tem um id, ja foi integrado, entao edita o registro.
                else {

                    //muda o id local pelo id do servidor para chamar a edicao la
                    frete.setId(frete.getS_id());
                    call = freteService.update(frete);

                }
                //executar no serivodr
                retornoIntegracao = call.execute().body();

                //se salvou no servidor, salvar os dados de integracao.
                FreteDAO dao = new FreteDAO(context);

                if (retornoIntegracao.getDthr_integracao() != null){
                    dao.salvar_dados_integracao(idLocalFrete,
                            retornoIntegracao.getId_gerado(), retornoIntegracao.getDthr_integracao());
                    return true;
                }

                return false;

            }catch (Exception ex){
                ex.printStackTrace();
                return false;
            }

        }
        else {
            return false;
        }

    }

}
