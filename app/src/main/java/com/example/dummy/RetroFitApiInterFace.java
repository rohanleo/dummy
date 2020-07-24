package com.example.dummy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetroFitApiInterFace {

    @GET("posts")
    Call<List<UserModel>> getUserInfo();

    @FormUrlEncoded
    @POST("post")
    Call<List<UserModel>> setUserInfo(@Field("userId") String userId, @Field("title") String title, @Field("body") String body);
}
