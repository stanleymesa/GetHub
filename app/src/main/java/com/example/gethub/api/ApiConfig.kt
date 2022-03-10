package com.example.gethub.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {

        private const val BASE_URL = "https://api.github.com/"

        fun getApiServices() : ApiServices {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiServices::class.java)
        }
    }
}