package com.example.composejoyride.data.network

import com.example.composejoyride.data.network.api.ServerApi
import com.example.composejoyride.data.utils.Constants.BASE_SERVER_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val client = Retrofit.Builder()
    .baseUrl(BASE_SERVER_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val serverApi: ServerApi = client.create(ServerApi::class.java)