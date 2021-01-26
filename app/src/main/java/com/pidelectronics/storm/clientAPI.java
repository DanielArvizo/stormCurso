package com.pidelectronics.storm;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface clientAPI {

    @GET
    Call<JsonObject> obtenerClimaApi(@Url String url);

}
