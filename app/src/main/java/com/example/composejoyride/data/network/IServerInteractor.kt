package com.example.composejoyride.data.network

import com.example.composejoyride.data.network.api.models.ClientsidePoem


interface IServerInteractor {
    suspend fun analyzePoem(lines: List<String>): ClientsidePoem
}