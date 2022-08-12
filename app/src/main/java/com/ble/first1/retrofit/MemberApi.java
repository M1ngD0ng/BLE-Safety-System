package com.ble.first1.retrofit;

import com.ble.first1.model.Member;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MemberApi {

    @POST("/member/join")
    Call<Member> join(@Body Member member);
}
