package com.example.composejoyride.data.network.api.models

import com.google.gson.annotations.SerializedName

data class ServersidePoemResponse(
    @SerializedName("is_poetry") val isPoetry: Boolean?,
    @SerializedName("score") val score: Double?,
    @SerializedName("meter") val meter: String?,
    @SerializedName("rhyme_scheme") val rhymeScheme: String?,
    @SerializedName("num_rhymes") val numRhymes: Int?,
    @SerializedName("error_text") val errorText: String?,
    @SerializedName("stressed_lines") val stressedLines: List<String>?,
    @SerializedName("unstressed_lines") val unstressedLines: List<String>?,
    @SerializedName("syllables") val syllables: List<String>?
)
