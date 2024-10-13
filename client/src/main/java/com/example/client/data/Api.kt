package com.example.client.data

import retrofit2.http.GET
import retrofit2.http.Url
const val BASE_URL = ""
interface Api {
    @GET("")
    fun getGesture(@Url url: String): String


}