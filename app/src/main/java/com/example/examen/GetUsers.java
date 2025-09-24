package com.example.examen;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetUsers {
    @GET("api/")
    Call<RandomUserResponse> getUsers(@Query("results") int results);

    class RandomUserResponse {
        public UserInfo[] results;
    }
}