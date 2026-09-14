package com.example.composejoyride.data.network.api

import com.example.composejoyride.data.network.api.models.ServersidePoem
import com.example.composejoyride.data.network.api.models.ServersidePoemResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ServerApi {
    @POST("scansion/analyze")
    suspend fun analyzePoem(@Body request: ServersidePoem): ServersidePoemResponse
}