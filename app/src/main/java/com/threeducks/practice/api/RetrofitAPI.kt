package com.threeducks.practice.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("/api/")
    fun getDataInfo(
        @Query("key") key: String,
        @Query("per_page") per_page: String,
        @Query("q") q: String
    ): Call<ResponseData>

} // interface :: PracticeAPI