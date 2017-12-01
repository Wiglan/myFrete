package com.wfrete.wfrete2.api.controller;

import android.content.Context;

import com.wfrete.wfrete2.util.Funcoes;
import com.wfrete.wfrete2.api.ServiceGenerator;
import com.wfrete.wfrete2.api.service.CategoriaService;
import com.wfrete.wfrete2.dao.CategoriaDAO;
import com.wfrete.wfrete2.model.Categoria;
import com.wfrete.wfrete2.model.RetornoIntegracao;

import retrofit2.Call;

/**
 * Created by Desenvolvimento 11 on 19/11/2017.
 */

public class CategoriaController {

    public static boolean wsSalvarCategoria(Context context,  Categoria categoria){

        int localizadorCategoriaWS = 0;
        int idLocalCategoria = categoria.getId();

        if (Funcoes.internetAtiva(context)){

            try {
                CategoriaService categoriaService = ServiceGenerator.createService(CategoriaService.class, "TOKEN");

                RetornoIntegracao retornoIntegracao;
                Call<RetornoIntegracao> call;

                //novo categoria
                if (categoria.getS_id() <= 0){
                    localizadorCategoriaWS = Funcoes.getRandomInt() * -1;
                    categoria.setS_id(localizadorCategoriaWS);

                    call = categoriaService.save(categoria);

                }
                //se ja tem um id, ja foi integrado, entao edita o registro.
                else {

                    //muda o id local pelo id do servidor para chamar a edicao la
                    categoria.setId(categoria.getS_id());
                    call = categoriaService.update(categoria);

                }
                //executar no serivodr
                retornoIntegracao = call.execute().body();

                //se salvou no servidor, salvar os dados de integracao.
                CategoriaDAO dao = new CategoriaDAO(context);

                if (retornoIntegracao.getDthr_integracao() != null){
                    dao.salvar_dados_integracao(idLocalCategoria,
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
