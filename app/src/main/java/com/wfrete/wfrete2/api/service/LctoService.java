package com.wfrete.wfrete2.api.service;

import com.wfrete.wfrete2.model.Lcto;
import com.wfrete.wfrete2.model.RetornoIntegracao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LctoService {

    @GET("/lcto/")
    Call<List<Lcto>> getAll();

    @GET("/lcto/{id}")
    Call<Lcto> getOne(@Path("id") Integer id);

    @POST("/lcto/")
    Call<RetornoIntegracao> save(@Body Lcto lcto);

    @PUT("/lcto/")
    Call<RetornoIntegracao> update(@Body Lcto lcto);

    @DELETE("/lcto/{id}")
    Call<Void> delete(@Path("id") Integer id);
}
