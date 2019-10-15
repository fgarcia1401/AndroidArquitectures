package com.example.androidarquitectures.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface  CountriesAPI {

    @GET("all")
    Single<List<Country>> getCountries();

}
