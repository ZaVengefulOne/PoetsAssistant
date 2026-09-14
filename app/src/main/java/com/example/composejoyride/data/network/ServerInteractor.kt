package com.example.composejoyride.data.network

import android.util.Log
import com.example.composejoyride.data.network.api.models.ClientsidePoem
import com.example.composejoyride.data.network.api.models.ServersidePoem
import com.example.composejoyride.data.network.api.models.ServersidePoemResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServerInteractor : IServerInteractor {
    override suspend fun analyzePoem(lines: List<String>): ClientsidePoem {
        val response = serverApi.analyzePoem(
            ServersidePoem(
                lines = lines,
                checkRhymes = true,
                checkPoorPoetry = true,
                showSecondaryAccentuation = false,
            )
        )
        return ClientsidePoem(
            stressedLines = response.stressedLines ?: listOf(),
            score = response.score,
            meter = response.meter,
            rhymeScheme = response.rhymeScheme
        )
    }
}