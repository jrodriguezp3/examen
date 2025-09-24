package com.example.examen;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface GetCountries {
    @GET("v3.1/name/{country}")
    Call<CountryResponse[]> getCountry(@Path("country") String country);

    class CountryResponse {
        public Flags flags;

        public static class Flags {
            public String png;
            public String svg;
        }
    }
}
