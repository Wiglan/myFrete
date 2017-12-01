package com.wfrete.wfrete2.api.service;

import com.wfrete.wfrete2.model.Frete;
import com.wfrete.wfrete2.model.RetornoIntegracao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FreteService {

    @GET("/frete/")
    Call<List<Frete>> getAll();

    @GET("/frete/{id}")
    Call<Frete> getOne(@Path("id") Integer id);

    @POST("/frete/")
    Call<RetornoIntegracao> save(@Body Frete frete);

    @PUT("/frete/")
    Call<RetornoIntegracao> update(@Body Frete frete);

    @DELETE("/frete/{id}")
    Call<Void> delete(@Path("id") Integer id);
}
