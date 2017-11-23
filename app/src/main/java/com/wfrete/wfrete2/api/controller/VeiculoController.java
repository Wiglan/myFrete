package com.wfrete.wfrete2.api.controller;

import android.content.Context;

import com.wfrete.wfrete2.util.Funcoes;
import com.wfrete.wfrete2.api.ServiceGenerator;
import com.wfrete.wfrete2.api.service.VeiculoService;
import com.wfrete.wfrete2.dao.VeiculoDAO;
import com.wfrete.wfrete2.model.Veiculo;
import com.wfrete.wfrete2.model.RetornoIntegracao;

import retrofit2.Call;

/**
 * Created by Desenvolvimento 11 on 19/11/2017.
 */

public class VeiculoController {

    public static void wsSalvarVeiculo(Context context,  Veiculo veiculo){

        int localizadorVeiculoWS = 0;
        int idLocalVeiculo = veiculo.getId();

        if (Funcoes.internetAtiva(context)){

            try {
                VeiculoService veiculoService = ServiceGenerator.createService(VeiculoService.class, "TOKEN");

                RetornoIntegracao retornoIntegracao;
                Call<RetornoIntegracao> call;

                //novo veiculo
                if (veiculo.getS_id() <= 0){
                    localizadorVeiculoWS = Funcoes.getRandomInt() * -1;
                    veiculo.setS_id(localizadorVeiculoWS);

                    call = veiculoService.save(veiculo);

                }
                //se ja tem um id, ja foi integrado, entao edita o registro.
                else {

                    //muda o id local pelo id do servidor para chamar a edicao la
                    veiculo.setId(veiculo.getS_id());
                    call = veiculoService.update(veiculo);

                }
                //executar no serivodr
                retornoIntegracao = call.execute().body();

                //se salvou no servidor, salvar os dados de integracao.
                VeiculoDAO dao = new VeiculoDAO(context);

                if (retornoIntegracao.getDthr_integracao() != null){
                    dao.salvar_dados_integracao(idLocalVeiculo,
                            retornoIntegracao.getId_gerado(), retornoIntegracao.getDthr_integracao());
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }

        }

    }

}
