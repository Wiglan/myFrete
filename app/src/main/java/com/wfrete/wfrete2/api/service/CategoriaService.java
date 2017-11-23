package com.wfrete.wfrete2.api.service;

import com.wfrete.wfrete2.model.Categoria;
import com.wfrete.wfrete2.model.RetornoIntegracao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoriaService {

    @GET("/categoria/")
    Call<List<Categoria>> getAll();

    @GET("/categoria/{id}")
    Call<Categoria> getOne(@Path("id") Integer id);

    @POST("/categoria/")
    Call<RetornoIntegracao> save(@Body Categoria categoria);

    @PUT("/categoria/")
    Call<RetornoIntegracao> update(@Body Categoria categoria);

    @DELETE("/categoria/{id}")
    Call<Void> delete(@Path("id") Integer id);
}
