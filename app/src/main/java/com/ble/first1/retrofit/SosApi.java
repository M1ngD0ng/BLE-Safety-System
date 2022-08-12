package com.ble.first1.retrofit;

import com.ble.first1.model.Member;
import com.ble.first1.model.Sos;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SosApi {
    @POST("/sos/sign")
    Call<Sos> sign(@Body Sos sos);
}
