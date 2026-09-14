package com.example.composejoyride.data.network.api.models

import com.google.gson.annotations.SerializedName

data class ServersidePoem(
    @SerializedName("lines") val lines : List<String>,
    @SerializedName("check_rhymes") val checkRhymes: Boolean,
    @SerializedName("check_poor_poetry") val checkPoorPoetry: Boolean,
    @SerializedName("show_secondary_accentuation") val showSecondaryAccentuation : Boolean
    )
