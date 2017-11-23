package com.wfrete.wfrete2.api.service;

import com.wfrete.wfrete2.model.Veiculo;
import com.wfrete.wfrete2.model.RetornoIntegracao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface VeiculoService {

    @GET("/veiculo/")
    Call<List<Veiculo>> getAll();

    @GET("/veiculo/{id}")
    Call<Veiculo> getOne(@Path("id") Integer id);

    @POST("/veiculo/")
    Call<RetornoIntegracao> save(@Body Veiculo veiculo);

    @PUT("/veiculo/")
    Call<RetornoIntegracao> update(@Body Veiculo veiculo);

    @DELETE("/veiculo/{id}")
    Call<Void> delete(@Path("id") Integer id);
}
