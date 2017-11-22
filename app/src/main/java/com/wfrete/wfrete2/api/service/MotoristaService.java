package com.wfrete.wfrete2.api.service;

import com.wfrete.wfrete2.model.Motorista;
import com.wfrete.wfrete2.model.RetornoIntegracao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MotoristaService {

    @GET("/motorista/")
    Call<List<Motorista>> getAll();

    @GET("/motorista/{id}")
    Call<Motorista> getOne(@Path("id") Long id);

    @POST("/motorista/")
    Call<RetornoIntegracao> save(@Body Motorista motorista);

    @PUT("/motorista/")
    Call<RetornoIntegracao> update(@Body Motorista motorista);

    @DELETE("/motorista/{id}")
    Call<Void> delete(@Path("id") Long id);
}
