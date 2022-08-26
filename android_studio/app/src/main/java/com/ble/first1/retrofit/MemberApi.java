package com.ble.first1.retrofit;

import com.ble.first1.model.Member;
import com.ble.first1.model.MemberRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MemberApi {

    @POST("/member/join")
    Call<Void> join(@Body Member member); // 받는 값이 없어서 VOIDㅇ

    @POST("/member/login")
    Call<MemberRes> login(@Body Member member);
}
