package com.example.simplemath

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NewtonAPI {
    @GET("simplify/{data}")
    fun simplify(@Path("data") data: String): Call<NewtonResult>

    @GET("factor/{data}")
    fun factor(@Path("data") data: String): Call<NewtonResult>

    @GET("derive/{data}")
    fun derive(@Path("data") data: String): Call<NewtonResult>

    @GET("integrate/{data}")
    fun integrate(@Path("data") data: String): Call<NewtonResult>

    @GET("zeroes/{data}")
    fun zeroes(@Path("data") data: String): Call<NewtonResult>

    @GET("tangent/{data}")
    fun tangent(@Path("data") data: String): Call<NewtonResult>

    @GET("area/{data}")
    fun area(@Path("data") data: String): Call<NewtonResult>

    @GET("cos/{data}")
    fun cos(@Path("data") data: String): Call<NewtonResult>

    @GET("sin/{data}")
    fun sin(@Path("data") data: String): Call<NewtonResult>

    @GET("tan/{data}")
    fun tan(@Path("data") data: String): Call<NewtonResult>

    @GET("arccos/{data}")
    fun arccos(@Path("data") data: String): Call<NewtonResult>

    @GET("arcsin/{data}")
    fun arcsin(@Path("data") data: String): Call<NewtonResult>

    @GET("arctan/{data}")
    fun arctan(@Path("data") data: String): Call<NewtonResult>

    @GET("abs/{data}")
    fun abs(@Path("data") data: String): Call<NewtonResult>

    @GET("log/{data}")
    fun log(@Path("data") data: String): Call<NewtonResult>

//    /simplify/2^2+2(2)
//    /factor/x^2 + 2x
//    /derive/x^2+2x
//    /integrate/x^2+2x
//    /zeroes/x^2+2x
//  ******  /tangent/2|x^3
//  ******  /area/2:4|x^3
//    /cos/pi
//    /sin/0
//    /tan/0
//    /arccos/1
//    /arcsin/0
//    /arctan/0
//    /abs/-1
//  ******  /log/2|8


}