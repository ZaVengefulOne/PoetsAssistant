package com.example.composejoyride.data.network.api.models

data class ClientsidePoem(
    var stressedLines: List<String>,
    var score: Double?,
    var meter: String?,
    var rhymeScheme: String?
)
